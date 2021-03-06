package uk.ac.ebi.ep.enzymeservice.reactome.service;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import uk.ac.ebi.ep.enzymeservice.reactome.config.EnzymeServiceProperties;
import uk.ac.ebi.ep.enzymeservice.reactome.model.Figure;
import uk.ac.ebi.ep.enzymeservice.reactome.model.ReactomeResult;
import uk.ac.ebi.ep.enzymeservice.reactome.model.Summation;
import uk.ac.ebi.ep.enzymeservice.reactome.view.PathWay;
import uk.ac.ebi.ep.restclient.service.RestConfigService;

/**
 *
 * @author joseph
 */
@Slf4j
@Service
class ReactomeServiceImpl implements ReactomeService {

    private static final String PATHWAY = "Pathway";

    private final RestConfigService restConfigService;
    private final EnzymeServiceProperties enzymeServiceProperties;
    private static final String ENDPOINT = "/ContentService/data/query/";
    private static final String REACTOME_CONTENT_SERVICE_PATH = ENDPOINT;

    @Autowired
    public ReactomeServiceImpl(RestConfigService restConfigService, EnzymeServiceProperties enzymeServiceProperties) {
        this.restConfigService = restConfigService;
        this.enzymeServiceProperties = enzymeServiceProperties;
    }

    private URI buildURI(String reactomeId) {

        String baseUrl = String.format("%s%s%s", enzymeServiceProperties.getReactomeUrl(), REACTOME_CONTENT_SERVICE_PATH, reactomeId);

        return UriComponentsBuilder.fromUriString(baseUrl).build().toUri();
    }

    private Mono<ReactomeResult> reactomeContentService(String id) {
        String baseUrl = String.format("%s", enzymeServiceProperties.getReactomeUrl());

        WebClient client = restConfigService.getWebClient()
                .mutate()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection()
                        .compress(true)))
                .baseUrl(baseUrl)
                .build();

        URI uri = buildURI(id);

        return client
                .get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(response -> response.bodyToMono(ReactomeResult.class))
                .onErrorResume(ex -> Mono.error(ex))
                .switchIfEmpty(Mono.empty());

    }

    @Override
    public Mono<ReactomeResult> findReactomeResultById(String reactomeId) {
        return reactomeContentService(reactomeId);
    }

    @Override
    public Mono<PathWay> findPathwayById(String pathwayId) {
        return reactomeContentService(pathwayId)
                .filter(data -> Objects.nonNull(data))
                .map(this::buildPathWay);
    }

    @Override
    public Mono<List<PathWay>> findPathwaysByIds(List<String> pathwayIds) {
        return Flux.fromIterable(pathwayIds)
                .flatMap(this::reactomeContentService)
                .filter(data -> Objects.nonNull(data))
                .map(this::buildPathWay)
                .collectList();
    }

    @Override
    public Flux<PathWay> pathwaysByPathwayIds(List<String> pathwayIds) {

        List<Mono<ReactomeResult>> data = pathwayIds.stream()
                .map(this::reactomeContentService)
                .collect(Collectors.toList());
        if (Objects.isNull(data)) {
            return Flux.empty();
        }

        return Flux.merge(data)
                .map(this::buildPathWay);

    }

    private PathWay buildPathWay(@NonNull ReactomeResult result) {
        PathWay pathway = null;
        if (result.getSchemaClass() != null && result.getSpeciesName() != null && (result.getSchemaClass().equalsIgnoreCase(PATHWAY) || result.getSpeciesName().equalsIgnoreCase(PATHWAY))) {
            pathway = new PathWay();
            pathway.setId(result.getStId());
            pathway.setName(result.getDisplayName());
            String url = String.format("%s%s", "https://www.reactome.org/content/detail/", result.getStId());
            pathway.setUrl(url);
            String text = result.getSummation().stream().findFirst().orElse(new Summation()).getText();
            pathway.setDescription(text);
            Figure figure = result.getFigure().stream().findFirst().orElse(new Figure());
            String image = String.format("%s%s", "https://www.reactome.org", figure.getUrl()); //"https://www.reactome.org/figures/SAM_formation.jpg"
            if (figure.getUrl() != null) {
                pathway.setImage(image);
            }

        }
        return pathway;
    }

}
