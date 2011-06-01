package uk.ac.ebi.ep.ebeye.adapter;

import java.util.List;
import java.util.Map;
import uk.ac.ebi.ebeye.param.ParamOfGetAllResults;
import uk.ac.ebi.ebeye.param.ParamOfGetResults;
import uk.ac.ebi.ep.ebeye.result.jaxb.Result;
import uk.ac.ebi.ep.search.exception.MultiThreadingException;

/**
 *
 * @since   1.0
 * @version $LastChangedRevision$ <br/>
 *          $LastChangedDate$ <br/>
 *          $Author$
 * @author  $Author$
 */
public interface IEbeyeAdapter {

//********************************* VARIABLES ********************************//
    public static final int EBEYE_RESULT_LIMIT = 100;
    public static final int EP_RESULTS_PER_DOIMAIN_LIMIT = 20;
    public static final int EP_UNIPROT_XREF_RESULT_LIMIT = 8000;
    //MILLISECOND
    public static final int EBEYE_ONE_RECORD_TIMEOUT = 10;
    //SECOND
    public static final int EBEYE_ONLINE_REQUEST_TIMEOUT = 60;
    //HOUR
    public static final int EBEYE_CACHE_TIMEOUT = 2;
    public static enum Domains {intenz,uniprot,rhea,reactome,chebi
                                                ,pdbe,chembl_compound, chembl_target,omim,mesh
        };
    //Supported fields
        //TODO change the schema to add UNIPROT array
    public static enum FieldsOfGetResults {
       id, acc,UNIPROT
    };

    public static enum UniprotResultFields  {
        descSubName,descRecName,organism_scientific_name,status
    };

//******************************** CONSTRUCTORS ******************************//


//****************************** GETTER & SETTER *****************************//


//********************************** METHODS *********************************//

    public List<Result> getAllResults(ParamOfGetAllResults paramOfGetResults);

    public Map<String, List<Result>> getAllDomainsResults(
            List<ParamOfGetAllResults>  ParamOfGetResultsList)
            throws MultiThreadingException;

    public List<Result> getResults(List<ParamOfGetAllResults>
            paramOfGetAllResultsList) throws MultiThreadingException;
}
