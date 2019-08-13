package uk.ac.ebi.ep.xml.transformer;

import java.util.Set;
import uk.ac.ebi.ep.xml.dao.EcNumber;
import uk.ac.ebi.ep.xml.entities.ProteinXml;
import uk.ac.ebi.ep.xml.schema.Field;
import uk.ac.ebi.ep.xml.util.FieldName;

/**
 *
 * @author joseph
 */
public class Transformer {

    protected void addField(String fieldId, String value, Set<Field> fields) {
        Field field = new Field(fieldId, value);
        fields.add(field);
    }

    protected String computeEcToFamilyName(int ec) {

        if (ec == 1) {

            return EcNumber.EnzymeFamily.OXIDOREDUCTASES.getName();
        }
        if (ec == 2) {
            return EcNumber.EnzymeFamily.TRANSFERASES.getName();
        }
        if (ec == 3) {
            return EcNumber.EnzymeFamily.HYDROLASES.getName();
        }
        if (ec == 4) {
            return EcNumber.EnzymeFamily.LYASES.getName();
        }
        if (ec == 5) {
            return EcNumber.EnzymeFamily.ISOMERASES.getName();
        }
        if (ec == 6) {
            return EcNumber.EnzymeFamily.LIGASES.getName();
        }
        if (ec == 7) {
            return EcNumber.EnzymeFamily.TRANSLOCASES.getName();
        }
        return "Invalid Ec Number";
    }

    /**
     *
     * @param ec
     * @return enzyme family
     */
    protected String computeFamily(String ec) {

        if (ec.startsWith("1")) {

            return EcNumber.EnzymeFamily.OXIDOREDUCTASES.getName();
        }
        if (ec.startsWith("2")) {
            return EcNumber.EnzymeFamily.TRANSFERASES.getName();
        }
        if (ec.startsWith("3")) {
            return EcNumber.EnzymeFamily.HYDROLASES.getName();
        }
        if (ec.startsWith("4")) {
            return EcNumber.EnzymeFamily.LYASES.getName();
        }
        if (ec.startsWith("5")) {
            return EcNumber.EnzymeFamily.ISOMERASES.getName();
        }
        if (ec.startsWith("6")) {
            return EcNumber.EnzymeFamily.LIGASES.getName();
        }

        if (ec.startsWith("7")) {
            return EcNumber.EnzymeFamily.TRANSLOCASES.getName();
        }

        return "Invalid Ec Number";
    }

    protected void addGeneNameFields(ProteinXml entry, Set<Field> fields) {
        addField(FieldName.GENE_NAME.getName(), entry.getGeneName(), fields);

    }
}
