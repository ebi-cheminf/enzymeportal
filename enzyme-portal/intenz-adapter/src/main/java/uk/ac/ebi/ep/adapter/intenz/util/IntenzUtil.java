package uk.ac.ebi.ep.adapter.intenz.util;

/**
 *
 * @since   1.0
 * @version $LastChangedRevision$ <br/>
 *          $LastChangedDate$ <br/>
 *          $Author$
 * @author  $Author$
 */
public class IntenzUtil {

    public static final String INTENZ_FTP_ADD =
            "ftp://ftp.ebi.ac.uk/pub/databases/intenz/xml/ASCII";
            //"ftp://ftp.ebi.ac.uk/pub/databases/intenz/xml/ASCII/EC_1/EC_1.1/EC_1.1.1/EC_1.1.1.1.xml";
    public static final String EC_PREFIX = "EC_";

    public static String createIntenzEntryUrl(String ecNumber) {
        String[] ecNumberFragments = ecNumber.split("\\.");
        StringBuffer sb = new StringBuffer();
        sb.append(INTENZ_FTP_ADD);
        String ecSuperClassNr = "";
        for (String ecNumberFragment: ecNumberFragments) {
            sb.append("/");
            sb.append(EC_PREFIX);
            String currentNr = "";
            if (ecSuperClassNr.equals("")) {
                currentNr = ecNumberFragment;
            }
            else {
                currentNr = ecSuperClassNr + "." + ecNumberFragment;
            }
            sb.append(currentNr);
             ecSuperClassNr = currentNr;
        }
        sb.append(".xml");
        return sb.toString();
    }

}