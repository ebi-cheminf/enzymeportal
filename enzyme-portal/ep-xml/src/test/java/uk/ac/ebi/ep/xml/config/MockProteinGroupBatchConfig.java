package uk.ac.ebi.ep.xml.config;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.xml.StaxWriterCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import uk.ac.ebi.ep.data.domain.ProteinGroups;
import uk.ac.ebi.ep.xml.generator.protein.ProteinXmlFooterCallback;
import uk.ac.ebi.ep.xml.generator.protein.ProteinXmlHeaderCallback;
import uk.ac.ebi.ep.xml.generator.proteinGroup.JobCompletionNotificationListener;
import uk.ac.ebi.ep.xml.generator.proteinGroup.ProteinGroupsProcessor;
import uk.ac.ebi.ep.xml.model.Entry;
import uk.ac.ebi.ep.xml.util.LogChunkListener;
import uk.ac.ebi.ep.xml.util.PrettyPrintStaxEventItemWriter;

/**
 *
 * @author Joseph <joseph@ebi.ac.uk>
 */
@Configuration
@EnableBatchProcessing
@Import({MockXmlConfig.class})
public class MockProteinGroupBatchConfig extends DefaultBatchConfigurer {

//    public static final String PROTEIN_CENTRIC_JOB = "PROTEIN_CENTRIC_JOB";
//    public static final String PROTEIN_CENTRIC_DB_TO_XML_STEP = "readFromDbWriteToXMLStep";
//    
    private static final String READ_DATA_JOB = "READ_DATA_FROM_DB_JOB";
    private static final String READ_PROCESS_WRITE_XML_STEP = "readProcessAndWriteXMLstep";

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private XmlConfigParams mockXmlConfigParams;

    @Bean
    public Resource proteinCentricXmlDir() {
        return new FileSystemResource(mockXmlConfigParams.getProteinCentricXmlDir());
    }

    @Bean
    public JobLauncher jobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(this.getJobRepository());

        return jobLauncher;
    }

    @Bean
    public Job proteinCentricJob() {
        return jobBuilders.get(READ_DATA_JOB)
                .start(readFromDbWriteToXMLStep())
                .listener(jobExecutionListener())
                .build();
    }

    @Bean
    public Step readFromDbWriteToXMLStep() {
        return stepBuilders.get(READ_PROCESS_WRITE_XML_STEP)
                .<ProteinGroups, Entry>chunk(mockXmlConfigParams.getChunkSize())
                .<ProteinGroups>reader(proteinGroupsReader())
                .processor(proteinGroupsToEntryProcessor())
                .writer(xmlWriter())
                .listener(logChunkListener())
                .build();
    }

    @Bean
    public ItemReader<ProteinGroups> proteinGroupsReader() {
        JpaPagingItemReader<ProteinGroups> databaseReader = new JpaPagingItemReader<>();
        databaseReader.setEntityManagerFactory(entityManagerFactory);
        databaseReader.setQueryString("select p from ProteinGroups p");
        databaseReader.setPageSize(mockXmlConfigParams.getChunkSize());
        databaseReader.setSaveState(false);
        return databaseReader;
    }

    @Bean
    public ItemProcessor<ProteinGroups, Entry> proteinGroupsToEntryProcessor() {
        return new ProteinGroupsProcessor(mockXmlConfigParams);

    }

//    @Bean
//    public ItemWriter<Entry> entryToXmlWriter() {
//        PrettyPrintStaxEventItemWriter<Entry> xmlWriter = new PrettyPrintStaxEventItemWriter<>();
//        xmlWriter.setResource(proteinCentricXmlDir());
//        xmlWriter.setRootTagName("database");
//        xmlWriter.setMarshaller(entryMarshaller());
//        xmlWriter.setHeaderCallback(xmlHeaderCallback());
//        xmlWriter.setFooterCallback(new ProteinXmlFooterCallback());
//        return xmlWriter;
//    }
    @Bean
    public ItemWriter<Entry> xmlWriter() {
        PrettyPrintStaxEventItemWriter<Entry> xmlWriter = new PrettyPrintStaxEventItemWriter<>();
        // XmlFileUtils.createDirectory(mockXmlConfigParams.getXmlDir());
        xmlWriter.setResource(proteinCentricXmlDir());
        xmlWriter.setRootTagName("database");
        xmlWriter.setMarshaller(entryMarshaller());
        xmlWriter.setHeaderCallback(xmlHeaderCallback());
        xmlWriter.setFooterCallback(new ProteinXmlFooterCallback());
        return xmlWriter;
    }

    private StaxWriterCallback xmlHeaderCallback() {
        return new ProteinXmlHeaderCallback(mockXmlConfigParams.getReleaseNumber(), countProteinGroupEntries());
    }

    private String countProteinGroupEntries() {
        Query query = entityManagerFactory.createEntityManager().createQuery("select count(p.proteinGroupId) from ProteinGroups p");
        return String.valueOf(query.getSingleResult());
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new JobCompletionNotificationListener(READ_DATA_JOB);
    }

    private ChunkListener logChunkListener() {
        return new LogChunkListener(mockXmlConfigParams.getChunkSize());
    }

    /**
     * Creates a job repository that uses an in memory map to register the job's
     * progress. This should be changed to use a real data source in the
     * following cases: - If you want to be able to resume a failed job - If you
     * want more than one of the same job (with the same parameters) to be
     * launched simultaneously - If you want to multi thread the job - If you
     * have a locally partitioned step.
     *
     * @return a JobRepository
     * @throws Exception if an error is encountered whilst creating the job repo
     */
    @Override
    protected JobRepository createJobRepository() throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    private Marshaller entryMarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Entry.class);

        Map<String, Object> jaxbProps = new HashMap<>();
        jaxbProps.put(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        marshaller.setMarshallerProperties(jaxbProps);

        return marshaller;
    }

}
