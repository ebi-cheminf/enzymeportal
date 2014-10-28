/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.ep.base.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.xml_cml.schema.cml2.react.Reaction;
import uk.ac.ebi.das.jdas.adapters.features.DasGFFAdapter.SegmentAdapter;
import uk.ac.ebi.das.jdas.adapters.features.FeatureAdapter;
import uk.ac.ebi.ep.adapter.bioportal.BioPortalService;
import uk.ac.ebi.ep.adapter.bioportal.IBioportalAdapter;
import uk.ac.ebi.ep.adapter.das.IDASFeaturesAdapter;
import uk.ac.ebi.ep.adapter.das.SimpleDASFeaturesAdapter;
import uk.ac.ebi.ep.adapter.literature.ILiteratureAdapter;
import uk.ac.ebi.ep.adapter.literature.LabelledCitation;
import uk.ac.ebi.ep.adapter.literature.SimpleLiteratureAdapter;
import uk.ac.ebi.ep.biomart.adapter.BiomartAdapter;
import uk.ac.ebi.ep.data.common.CommonSpecies;
import uk.ac.ebi.ep.data.domain.EnzymePortalCompound;
import uk.ac.ebi.ep.data.domain.EnzymePortalDisease;
import uk.ac.ebi.ep.data.domain.EnzymePortalPathways;
import uk.ac.ebi.ep.data.domain.EnzymePortalSummary;
import uk.ac.ebi.ep.data.domain.RelatedProteins;
import uk.ac.ebi.ep.data.domain.UniprotEntry;
import uk.ac.ebi.ep.data.domain.UniprotXref;
import uk.ac.ebi.ep.data.enzyme.model.ChemicalEntity;
import uk.ac.ebi.ep.data.enzyme.model.CountableMolecules;
import uk.ac.ebi.ep.data.enzyme.model.EcClass;
import uk.ac.ebi.ep.data.enzyme.model.Entity;
import uk.ac.ebi.ep.data.enzyme.model.Enzyme;
import uk.ac.ebi.ep.data.enzyme.model.EnzymeHierarchy;
import uk.ac.ebi.ep.data.enzyme.model.EnzymeModel;
import uk.ac.ebi.ep.data.enzyme.model.Molecule;
import uk.ac.ebi.ep.data.enzyme.model.Pathway;
import uk.ac.ebi.ep.data.enzyme.model.ProteinStructure;
import uk.ac.ebi.ep.data.enzyme.model.ReactionPathway;
import uk.ac.ebi.ep.data.enzyme.model.Sequence;
import uk.ac.ebi.ep.data.exceptions.EnzymeRetrieverException;
import uk.ac.ebi.ep.data.exceptions.MultiThreadingException;
import uk.ac.ebi.ep.data.search.model.Compound;
import uk.ac.ebi.ep.data.search.model.Disease;
import uk.ac.ebi.ep.data.search.model.EnzymeAccession;
import uk.ac.ebi.ep.data.service.EnzymePortalService;
import uk.ac.ebi.ep.ebeye.EbeyeService;
import uk.ac.ebi.ep.enzymeservices.chebi.ChebiAdapter;
import uk.ac.ebi.ep.enzymeservices.chebi.ChebiFetchDataException;
import uk.ac.ebi.ep.enzymeservices.chebi.IChebiAdapter;
import uk.ac.ebi.ep.enzymeservices.intenz.IntenzAdapter;
import uk.ac.ebi.ep.enzymeservices.reactome.IReactomeAdapter;
import uk.ac.ebi.ep.enzymeservices.reactome.ReactomeAdapter;
import uk.ac.ebi.ep.enzymeservices.rhea.IRheaAdapter;
import uk.ac.ebi.ep.enzymeservices.rhea.RheaWsAdapter;
import uk.ac.ebi.rhea.ws.client.RheaFetchDataException;

/**
 *
 * @author joseph
 */
public class EnzymeRetriever extends EnzymeFinder {
    
    private static final Logger LOGGER = Logger.getLogger(EnzymeRetriever.class);
    protected IRheaAdapter rheaAdapter;
    private IReactomeAdapter reactomeAdapter;
    protected IChebiAdapter chebiAdapter;
    protected IDASFeaturesAdapter pdbeAdapter;
    protected ILiteratureAdapter litAdapter;
    protected BiomartAdapter biomartAdapter;
    private static IBioportalAdapter bioportalAdapter;

    //private final EnzymePortalService service;
    public EnzymeRetriever(EnzymePortalService service, EbeyeService eService) {
        super(service, eService);
        
        rheaAdapter = new RheaWsAdapter();
        biomartAdapter = new BiomartAdapter();
        
        bioportalAdapter = new BioPortalService();
        try {
            pdbeAdapter = new SimpleDASFeaturesAdapter(IDASFeaturesAdapter.PDBE_DAS_URL);
        } catch (IOException e) {
            LOGGER.error("Unable to create a PDBe adapter", e);
        }
    }
    
    public IBioportalAdapter getBioportalAdapter() {
        return bioportalAdapter;
    }

    /**
     * Lazily constructs a new adapter if needed.
     *
     * @return a Reactome adapter.
     */
    public IReactomeAdapter getReactomeAdapter() {
        if (reactomeAdapter == null) {
            reactomeAdapter = new ReactomeAdapter();
        }
        return reactomeAdapter;
    }

    /**
     * Lazily constructs a new adapter if needed.
     *
     * @return a ChEBI adapter.
     */
    public IChebiAdapter getChebiAdapter() {
        if (chebiAdapter == null) {
            chebiAdapter = new ChebiAdapter();
        }
        return chebiAdapter;
    }
    
    public ILiteratureAdapter getLiteratureAdapter() {
        if (litAdapter == null) {
            litAdapter = new SimpleLiteratureAdapter();
        }
        return litAdapter;
    }
    
    public IntenzAdapter getIntenzAdapter() {
        if (intenzAdapter == null) {
            intenzAdapter = new IntenzAdapter();
        }
        
        return intenzAdapter;
    }
    
    List<String> computeSynonymNames(UniprotEntry uniprotEntry) {
        List<String> synonyms = new LinkedList<>();
        
        String namesColumn = uniprotEntry.getSynonymNames();
        
        if (namesColumn != null && namesColumn.contains(";")) {
            String[] syn = namesColumn.split(";");
            for (String x : syn) {
                
                synonyms.addAll(parseNameSynonyms(x));
            }
        }
        return synonyms.stream().sorted().distinct().collect(Collectors.toList());
        
    }
    
    private List<EnzymeAccession> getRelatedSPeciesOLD(EnzymePortalSummary enzymePortalSummary) {
        String defaultSpecies = CommonSpecies.Human.getScientificName();
        
        List<EnzymeAccession> relatedPecies = new LinkedList<>();
        
        String nameprefix = enzymePortalSummary.getUniprotAccession().getName().substring(0, enzymePortalSummary.getUniprotAccession().getName().indexOf("_"));
        
        List<UniprotEntry> enzyme = super.getService().findEnzymeByNamePrefixAndProteinName(nameprefix, enzymePortalSummary.getUniprotAccession().getProteinName());
        
        enzyme.stream().forEach((entry) -> {
            EnzymeAccession ea = new EnzymeAccession();
            ea.getUniprotaccessions().add(entry.getAccession());
            ea.setSpecies(entry);
            if (entry.getScientificname() != null && entry.getScientificname().equalsIgnoreCase(defaultSpecies)) {
                relatedPecies.add(0, ea);
            } else if (entry.getScientificname() != null && !entry.getScientificname().equalsIgnoreCase(defaultSpecies)) {
                relatedPecies.add(ea);
            }
        });
        
        return relatedPecies.stream().distinct().collect(Collectors.toList());
        
    }
    
    private List<EnzymeAccession> getRelatedSPecies(UniprotEntry uniprotEntry) {
        String defaultSpecies = CommonSpecies.Human.getScientificName();
        
        List<EnzymeAccession> relatedPecies = new LinkedList<>();
        
        String nameprefix = uniprotEntry.getName().substring(0, uniprotEntry.getName().indexOf("_"));
        
        List<UniprotEntry> enzyme = super.getService().findEnzymeByNamePrefixAndProteinName(nameprefix, uniprotEntry.getProteinName());
        
        enzyme.stream().forEach((entry) -> {
            EnzymeAccession ea = new EnzymeAccession();
            ea.getUniprotaccessions().add(entry.getAccession());
            ea.setSpecies(entry);
            if (entry.getScientificname() != null && entry.getScientificname().equalsIgnoreCase(defaultSpecies)) {
                relatedPecies.add(0, ea);
            } else if (entry.getScientificname() != null && !entry.getScientificname().equalsIgnoreCase(defaultSpecies)) {
                relatedPecies.add(ea);
            }
        });
        
        return relatedPecies.stream().distinct().collect(Collectors.toList());
        
    }
    
    private List<RelatedProteins> getRelatedProteins(UniprotEntry uniprotEntry) {
        String defaultSpecies = CommonSpecies.Human.getScientificName();
        
        String nameprefix = uniprotEntry.getName().substring(0, uniprotEntry.getName().indexOf("_"));
        
        List<RelatedProteins> relatedPecies = new LinkedList<>();
        
        List<RelatedProteins> relatedProteins = super.getService().findRelatedProteinsByNamePrefix(nameprefix);
        
        relatedProteins.stream().map((entry) -> {
            entry.setSpecies(uniprotEntry);
            return entry;
        }).map((entry) -> {
            entry.getUniprotaccessions().add(uniprotEntry.getAccession());
            return entry;
        }).forEach((entry) -> {
            if (entry.getUniprotAccession().getScientificname() != null && entry.getUniprotAccession().getScientificname().equalsIgnoreCase(defaultSpecies)) {
                relatedPecies.add(0, entry);
            } else if (entry.getUniprotAccession().getScientificname() != null && !entry.getUniprotAccession().getScientificname().equalsIgnoreCase(defaultSpecies)) {
                relatedPecies.add(entry);
            }
        });
        
        return relatedPecies.stream().distinct().collect(Collectors.toList());
        
    }
    
    private EnzymeModel getEnzymeModel(String uniprotAccession) {
        UniprotEntry uniprotEntry = super.getService().findByAccession(uniprotAccession);

        List<EnzymeAccession> relatedPecies = getRelatedSPecies(uniprotEntry);
        EnzymeModel model = new EnzymeModel();
        model.setName(uniprotEntry.getProteinName());
        model.setRelatedspecies(relatedPecies);
       // model.setRelatedspecies(getRelatedProteins(uniprotEntry));
        
        model.setAccession(uniprotEntry.getAccession());
        model.getUniprotaccessions().add(uniprotEntry.getAccession());
        model.setSpecies(uniprotEntry);
        uniprotEntry.getEnzymePortalEcNumbersSet().stream().forEach((ec) -> {
            Enzyme enzyme = new Enzyme();
            EnzymeHierarchy enzymeHierarchy = new EnzymeHierarchy();
            EcClass ecClass = new EcClass();
            ecClass.setEc(ec.getEcNumber());
            enzymeHierarchy.getEcclass().add(ecClass);
            enzyme.getEchierarchies().add(enzymeHierarchy);
            
            model.getEc().add(ec.getEcNumber());
            model.setEnzyme(enzyme);
        });
        
        return model;
    }
    
    public EnzymeModel getEnzyme(String uniprotAccession) {
        
        UniprotEntry uniprotEntry = super.getService().findByAccession(uniprotAccession);

        //String nameprefix = uniprotEntry.getName().substring(0, uniprotEntry.getName().indexOf("_"));
        //List<RelatedProteins> relatedProteins = super.getService().findRelatedProteinsByNamePrefix(nameprefix);
        List<EnzymePortalSummary> summaries = uniprotEntry.getEnzymePortalSummarySet();//.stream().distinct().collect(Collectors.toList());

        EnzymeModel model = new EnzymeModel();
        
        Enzyme enzyme = new Enzyme();
        
        Sequence sequence = new Sequence();
        sequence.setLength(uniprotEntry.getSequenceLength());
        enzyme.setSequence(sequence);
        
        model.setName(uniprotEntry.getProteinName());
        model.setRelatedspecies(getRelatedSPecies(uniprotEntry));
        //model.setRelatedspecies(getRelatedProteins(uniprotEntry));

//        relatedProteins.stream().forEach((relatedProtein) -> {
//            model.getRelatedspecies().add(relatedProtein);
//        });
        model.setSynonym(computeSynonymNames(uniprotEntry));
        
        model.setAccession(uniprotEntry.getAccession());
        model.getUniprotaccessions().add(uniprotEntry.getAccession());
        model.setSpecies(uniprotEntry);
        
        uniprotEntry.getEnzymePortalEcNumbersSet().stream().forEach((ec) -> {
            EnzymeHierarchy enzymeHierarchy = new EnzymeHierarchy();
            EcClass ecClass = new EcClass();
            ecClass.setEc(ec.getEcNumber());
            enzymeHierarchy.getEcclass().add(ecClass);
            enzyme.getEchierarchies().add(enzymeHierarchy);
            
            model.getEc().add(ec.getEcNumber());
        });
        
        summaries.stream().filter((summary) -> (summary.getCommentType().equalsIgnoreCase("FUNCTION"))).forEach((summary) -> {
            model.setFunction(summary.getCommentText());
        });
        
        model.setEnzyme(enzyme);
        try {
            System.out.println("calling intenz ..." + model.getEc());
            intenzAdapter.getEnzymeDetails(model);
        } catch (MultiThreadingException ex) {
            LOGGER.fatal("Error getting enzyme details from Intenz webservice", ex);
        }
        List<String> prov = new LinkedList<>();
        prov.add("IntEnz");
        prov.add("UniProt");
        prov.add("IntEnz - (Integrated relational Enzyme database) is a freely available resource focused on enzyme nomenclature.\n");
        prov.add("UniProt - The mission of UniProt is to provide the scientific community with a comprehensive, high-quality and freely accessible resource of protein sequence and functional information");
        
        model.getEnzyme().setProvenance(prov);
        
        return model;
    }
    
    public EnzymeModel getProteinStructure(String uniprotAccession)
            throws EnzymeRetrieverException {
        LOGGER.debug(" -STR- before getEnzymeSummary");
        
        EnzymeModel model = getEnzymeModel(uniprotAccession);
        
        addProteinStructures(model);
        
        return model;
    }
    
    private void addProteinStructures(EnzymeModel model) {
        
        List<UniprotXref> pdbcodes = super.getService().findPDBcodesByAccession(model.getAccession());
        
        for (UniprotXref pdb : pdbcodes) {
            
            String pdbId = pdb.getSourceId().toLowerCase();
            model.getPdbeaccession().add(pdbId);
            
            ProteinStructure structure = new ProteinStructure();
            structure.setId(pdbId);
            structure.setName(pdbId);

            //ProteinStructure structure = getPdbInfo(pdbId);
            model.getProteinstructure().add(structure);
            
        }
        
    }
    
    public EnzymeModel getDiseases(String uniprotAccession)
            throws EnzymeRetrieverException {
        
        EnzymeModel model = getEnzymeModel(uniprotAccession);
        addDiseases(model);
        return model;
        
    }

    /**
     * Adds any related diseases to the enzyme model.
     *
     * @param enzymeModel the model without disease info.
     * @since 1.1.0
     */
    protected void addDiseases(EnzymeModel enzymeModel) {
        
        List<EnzymePortalDisease> diseases = super.getService().findDiseasesByAccession(enzymeModel.getAccession());
        
        for (EnzymePortalDisease dis : diseases) {
            Disease disease = new Disease();
            disease.setDescription(dis.getDescription());
            disease.setId(dis.getMeshId());
            disease.setName(dis.getName());
            disease.setSelected(false);
            disease.setUrl(dis.getUrl());
            disease.getEvidences().add(dis.getEvidence());
            
            enzymeModel.getDisease().add(disease);
            
        }
        
    }
    
    public EnzymeModel getLiterature(String uniprotAccession) throws EnzymeRetrieverException {
        // EnzymeModel enzymeModel = this.getEnzyme(uniprotAccession);

        UniprotEntry uniprotEntry = super.getService().findByAccession(uniprotAccession);

        // List<EnzymeAccession> relatedPecies = getRelatedSPecies(uniprotEntry);
        EnzymeModel model = getEnzymeModel(uniprotAccession);
        //model.setName(uniprotEntry.getProteinName());
        //model.setRelatedspecies(relatedPecies);

        uniprotEntry.getUniprotXrefSet().stream().filter((pdb) -> (pdb.getSource().equalsIgnoreCase("PDB"))).map((pdb) -> pdb.getSourceId().toLowerCase()).forEach((pdbId) -> {
            model.getPdbeaccession().add(pdbId);
        });
        
        List<LabelledCitation> citations = getLiteratureAdapter().getCitations(
                uniprotAccession, model.getPdbeaccession());
        if (citations != null) {
            model.setLiterature(new ArrayList<>(citations));
        }
        
        return model;
    }

    /**
     * Retrieves the whole enzyme model for comparisons.
     *
     * @param acc the UniProt accession of the enzyme.
     * @return a complete model.
     * @throws EnzymeRetrieverException in case of problem retrieving the model
     * from UniProt, or small molecules from ChEBI.
     * @since 1.1.0
     */
    public EnzymeModel getWholeModel(String acc)
            throws EnzymeRetrieverException {
        // This model includes summary, reactions and pathways:
        EnzymeModel model = getReactionsPathways(acc);
        // Add the missing bits:
        addProteinStructures(model);
        addMolecules(model);
        addDiseases(model);
        return model;
    }
    
    public EnzymeModel getMolecules(String uniprotAccession)
            throws EnzymeRetrieverException {
        
        EnzymeModel model = getEnzymeModel(uniprotAccession);
        
        addMolecules(model);
        return model;
    }

    /**
     * Adds any available data about small molecules to the model.
     *
     * @param model the model
     * @throws EnzymeRetrieverException in case of problem retrieving detailed
     * info about small molecules from ChEBI.
     * @since 1.1.0
     */
    protected void addMolecules(EnzymeModel model)
            throws EnzymeRetrieverException {
        
        try {
            
            List<EnzymePortalCompound> compounds = super.getService().findCompoundsByUniprotAccession(model.getAccession());
            
            CountableMolecules activators = null, inhibitors = null,
                    cofactors = null, drugs = null, bioactive = null;
            if (compounds != null) {
                for (Compound compound : compounds) {
                    // Classify compounds from the DB:
                    switch (compound.getRole()) {
                        case ACTIVATOR:
                            activators = addMoleculeToGroup(activators, compound);
                            break;
                        case INHIBITOR:
                            inhibitors = addMoleculeToGroup(inhibitors, compound);
                            break;
                        case COFACTOR:
                            cofactors = addMoleculeToGroup(cofactors, compound);
                            break;
                        case DRUG:
                            drugs = addMoleculeToGroup(drugs, compound);
                            break;
                        case BIOACTIVE:
                            bioactive = addMoleculeToGroup(bioactive, compound);
                            break;
                    }
                }
            }
            model.setMolecule(new ChemicalEntity()
                    .withActivators(activators)
                    .withInhibitors(inhibitors)
                    .withCofactors(cofactors)
                    .withDrugs(drugs)
                    .withBioactiveLigands(bioactive));
            
            LOGGER.debug("MOLECULES before getting complete entries from ChEBI");
            getChebiAdapter().getMoleculeCompleteEntries(model);
            LOGGER.debug("MOLECULES before provenance");
            List<String> prov = new LinkedList<>();
            prov.add("ChEBI");
            prov.add("ChEMBL");
            // prov.add("RELEASED DATE = " + new Date());
            prov.add("ChEBI - (Chemical Entities of Biological Interest) is a freely available dictionary of molecular entities focused on ‘small’ chemical compounds.");
            prov.add("ChEMBL is a database of bioactive drug-like small"
                    + " molecules, it contains 2-D structures, calculated"
                    + " properties (e.g. logP, Molecular Weight, Lipinski"
                    + " Parameters, etc.) and abstracted bioactivities (e.g."
                    + " binding constants, pharmacology and ADMET data).");
            if (model.getMolecule() != null) {
                model.getMolecule().setProvenance(prov);
            }
        } catch (ChebiFetchDataException ex) {
            throw new EnzymeRetrieverException(
                    "Failed to get small molecule details from Chebi", ex);
        }
        
    }

    /**
     * Adds a compound to the group (inhibitors, cofactors, etc).<br/>
     * If the group is <code>null</code> it will be created and initialized.
     * <br>
     * It will be modified by adding the passed compound and increasing the
     * total count.
     *
     * @param group a molecules group.
     * @param comp a compound.
     * @return the modified (possibly newly created) group of molecules.
     */
    private CountableMolecules addMoleculeToGroup(CountableMolecules group,
            Compound comp) {
        if (group == null) {
            group = new CountableMolecules();
            group.setMolecule(new ArrayList<>());
            group.setTotalFound(0);
        }
        Molecule molecule = new Molecule();
        molecule.setName(comp.getName());
        molecule.setId(comp.getId());
        group.getMolecule().add(molecule);
        group.setTotalFound(group.getTotalFound() + 1);
        return group;
    }

    /**
     * Searches Rhea for the primary UniProt accession in the model and adds the
     * corresponding reactions if found. <br><b>WARNING:</b> the added reactions
     * have links only to Reactome and MACiE. The links are strings containing a
     * complete URL.
     *
     * @param enzymeModel
     * @return the same model updated with ReactionPathway objects, one per
     * reaction found.
     * @throws EnzymeRetrieverException
     */
    private EnzymeModel queryRheaWsForReactions(EnzymeModel enzymeModel)
            throws EnzymeRetrieverException {
        List<ReactionPathway> reactionPathways = new ArrayList<>();
        List<Reaction> reactions;
        try {
            reactions = rheaAdapter.getRheasInCmlreact(enzymeModel
                    .getUniprotaccessions().get(0));
            
        } catch (RheaFetchDataException ex) {
            throw new EnzymeRetrieverException("Query data from Rhea failed! ", ex);
        }
        for (Reaction reaction : reactions) {

// XXX This adapted reaction will have links only to Reactome and MACiE!:
            ReactionPathway reactionPathway = rheaAdapter.getReactionPathway(reaction);
            reactionPathways.add(reactionPathway);
            
        }
        enzymeModel.getReactionpathway().addAll(reactionPathways);
        return enzymeModel;
        
    }
    
    private Map<String, String> getReactomeAccQueriedFromUniprot(List<Pathway> reactomeUniprotLinks) {
        Map<String, String> idNameMap = new HashMap<>();
        for (Entity pathway : reactomeUniprotLinks) {
            idNameMap.put(pathway.getId(), pathway.getName());
            
        }
        return idNameMap;
    }
    
    public EnzymeModel getReactionsPathways(String uniprotAccession)
            throws EnzymeRetrieverException {
        //Get pathways from uniprot --> maybe not for now
        //Get pathways from Biomart (from Reactome reaction retrieved from Rhea)
        //Choose 2 top pathways to extract from Reactome Website
        // View pathway in reactome should be associated with the reaction.        
        //EnzymeModel enzymeModel = (EnzymeModel)this.uniprotAdapter.getReactionPathwaySummary(uniprotAccession);
        LOGGER.debug(" -RP- before uniprotAdapter.getEnzymeSummary");
        
        EnzymeModel model = getEnzyme(uniprotAccession);

//
//        UniprotEntry uniprotEntry = super.getService().findByAccession(uniprotAccession);
//
//        List<EnzymeAccession> relatedPecies = getRelatedSPecies(uniprotEntry);
//
////        EnzymeModel model = new EnzymeModel();
////        model.setName(uniprotEntry.getProteinName());
////        model.setRelatedspecies(relatedPecies);
////
////        model.setAccession(uniprotEntry.getAccession());
////        model.getUniprotaccessions().add(uniprotEntry.getAccession());
////        model.setSpecies(uniprotEntry);
//        EnzymeModel model = new EnzymeModel();
//
//        Enzyme enzyme = new Enzyme();
//
//        Sequence sequence = new Sequence();
//        sequence.setLength(uniprotEntry.getSequenceLength());
//        enzyme.setSequence(sequence);
//
//        model.setName(uniprotEntry.getProteinName());
//        model.setRelatedspecies(relatedPecies);
//        model.setSynonym(computeSynonymNames(uniprotEntry));
//
//        model.setAccession(uniprotEntry.getAccession());
//        model.getUniprotaccessions().add(uniprotEntry.getAccession());
//        model.setSpecies(uniprotEntry);
//
//        System.out.println("enyzme summary for getEnzyme method " + uniprotEntry.getEnzymePortalSummarySet());
//
//        for (EnzymePortalSummary summary : uniprotEntry.getEnzymePortalSummarySet()) {
//
//            if (summary.getCommentType().equalsIgnoreCase("FUNCTION")) {
//                model.setFunction(summary.getCommentText());
//            }
//
//            if (summary.getCommentType().equalsIgnoreCase("EC_NUMBER")) {
//
//                EnzymeHierarchy enzymeHierarchy = new EnzymeHierarchy();
//                EcClass ecClass = new EcClass();
//                ecClass.setEc(summary.getCommentText());
//                enzymeHierarchy.getEcclass().add(ecClass);
//                enzyme.getEchierarchies().add(enzymeHierarchy);
//
//                model.getEc().add(summary.getCommentText());
//                System.out.println("ec found " + summary.getCommentText());
//
//            }
//        }
//        model.setEnzyme(enzyme);
//
//        
//        
        ReactionPathway reactionPathway = new ReactionPathway();

        //List<EnzymePortalPathways> pathways = uniprotEntry.getEnzymePortalPathwaysSet().stream().distinct().collect(Collectors.toList()); 
        List<EnzymePortalPathways> pathways = super.getService().findPathwaysByAccession(uniprotAccession);
        reactionPathway.setPathways(pathways);
        
        model.getReactionpathway().add(reactionPathway);

        // The model comes with any available Reactome pathway IDs
        // in one ReactionPathway object, no more.
        // Now we get more ReactionPathways (one per Rhea reaction):
        LOGGER.debug(" -RP- before queryRheaWsForReactions");
        model = queryRheaWsForReactions(model);
        
        return model;
    }

    //TODO
    private ProteinStructure getPdbInfo(String pdbId) {
        ProteinStructure structure = null;
        try {
            SimpleDASFeaturesAdapter pdbeAdapter
                    = new SimpleDASFeaturesAdapter(IDASFeaturesAdapter.PDBE_DAS_URL);
            SegmentAdapter segment = pdbeAdapter.getSegment(pdbId);
            structure = new ProteinStructure();
            structure.setId(segment.getId());
            
            for (FeatureAdapter feature : segment.getFeature()) {
                if (feature.getType().getId().equals("description")) {
                    structure.setDescription(feature.getNotes().get(0)); // FIXME?
                    structure.setName(feature.getNotes().get(0));
                    
                }
                //else if (feature.getType().getId().equals("image")) {
//                    Image image = new Image();
//                    image.setSource(feature.getLinks().get(0).getHref());
//                    image.setCaption(feature.getLinks().get(0).getContent());
//                    image.setHref(feature.getLinks().get(1).getHref());
//                    structure.setImage(image);
//                } else if (feature.getType().getId().equals("provenance")) {
//                    structure.setProvenance(feature.getNotes());
//                } else if (feature.getType().getId().equals("summary")) {
//                    DASSummary summary = new DASSummary();
//                    summary.setLabel(feature.getLabel());
//                    summary.setNote(feature.getNotes());
//                    structure.getSummary().add(summary);
//                }
            }
            
        } catch (Exception e) {
            LOGGER.error("Unable to retrieve structure " + pdbId, e);
            
        }
        
        return structure;
    }
    
}