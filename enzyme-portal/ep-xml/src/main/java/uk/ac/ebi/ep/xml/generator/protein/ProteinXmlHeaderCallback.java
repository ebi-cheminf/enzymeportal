package uk.ac.ebi.ep.xml.generator.protein;

import java.io.IOException;
import java.time.LocalDate;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.springframework.batch.item.xml.StaxWriterCallback;
import uk.ac.ebi.ep.xml.generator.XmlTransformer;
import uk.ac.ebi.ep.xml.util.DateTimeUtil;
import uk.ac.ebi.ep.xml.util.EpXmlRuntimeException;
import uk.ac.ebi.ep.xml.util.Preconditions;

/**
 * Adds some meta data to the XML file: - Database name -
 * Database description - Generation date - Total number of entries
 *
 * It also adds an entries tag so that the XML conforms to the Ebeye search
 * format.
 *
 * @author joseph<joseph@ebi.ac.uk>
 */
public class ProteinXmlHeaderCallback implements StaxWriterCallback {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ProteinXmlHeaderCallback.class);

    static final String DB_NAME_ELEMENT = "name";
    static final String DB_DESCRIPTION_ELEMENT = "description";
    static final String RELEASE_VERSION_ELEMENT = "release";
    static final String RELEASE_DATE_ELEMENT = "release_date";
    static final String ENTRY_COUNT_ELEMENT = "entry_count";

    static final String ENTRIES_ELEMENT = "entries";

    protected final String release;
    protected final String numEntries;

    public ProteinXmlHeaderCallback(String release, String numEntries) {
        Preconditions.checkArgument(numEntries == null, "Numberof entries can not be null");
        Preconditions.checkArgument(release == null, "Release version can not be null");

        this.release = release;
        this.numEntries = numEntries;
    }

    @Override
    public void write(XMLEventWriter writer) throws IOException {
        try {
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();

            appendTagWithValue(DB_NAME_ELEMENT, XmlTransformer.ENZYME_PORTAL, eventFactory, writer);
            appendTagWithValue(DB_DESCRIPTION_ELEMENT, XmlTransformer.ENZYME_PORTAL_DESCRIPTION, eventFactory, writer);
            appendTagWithValue(RELEASE_VERSION_ELEMENT, release, eventFactory, writer);
            appendTagWithValue(RELEASE_DATE_ELEMENT, createTodayDate(), eventFactory, writer);
            appendTagWithValue(ENTRY_COUNT_ELEMENT, queryNumberOfUniProtEntries(), eventFactory, writer);

            XMLEvent event = eventFactory.createStartElement("", "", ENTRIES_ELEMENT);
            writer.add(event);
        } catch (XMLStreamException ex) {
            logger.error("Unable to write the header on the XML file" + ex);
            throw new EpXmlRuntimeException("Unable to write the header on the XML file");
        }
    }

    protected void appendTagWithValue(String tag, String value, XMLEventFactory eventFactory, XMLEventWriter writer)
            throws XMLStreamException {
        writer.add(eventFactory.createStartElement("", "", tag));
        writer.add(eventFactory.createCharacters(value));
        writer.add(eventFactory.createEndElement("", "", tag));
    }

    protected String createTodayDate() {
        return DateTimeUtil.convertDateToString(LocalDate.now());
    }

    private String queryNumberOfUniProtEntries() {
        return numEntries;
    }
}
