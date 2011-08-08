package uk.ac.ebi.ep.uniprot.adapter;

import java.util.List;
import uk.ac.ebi.ep.search.exception.MultiThreadingException;
import uk.ac.ebi.ep.search.model.EnzymeSummary;

/**
 *
 * @since   1.0
 * @version $LastChangedRevision$ <br/>
 *          $LastChangedDate$ <br/>
 *          $Author$
 * @author  $Author$
 */
public interface IUniprotAdapter {
    //second
    public static final int ENTRY_TIMEOUT = 60;

    public static final String ACCESSION_FIELD = "accession";
    public static final String SEQUENCE_URL_BASE = "http://www.uniprot.org/uniprot/";
    public static final String SEQUENCE_URL_SUFFIX = ".html#section_seq";



//********************************* VARIABLES ********************************//


//******************************** CONSTRUCTORS ******************************//


//****************************** GETTER & SETTER *****************************//


//********************************** METHODS *********************************//
    public EnzymeSummary getEnzymeEntry(String accession);
    
    public List<EnzymeSummary> queryEnzymeByIdPrefixes(List<String> queries, String defaultSpecies) throws
            MultiThreadingException;

}


