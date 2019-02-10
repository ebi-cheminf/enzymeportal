package uk.ac.ebi.ep.xml;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.ep.xml.config.XmlFileProperties;

/**
 * @author Joseph
 */
@Ignore //remove ignore when uzpdev is refreshed
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"management.port=0"})
@ActiveProfiles("uzprel")
public class XmlGeneratorTest {

    @Autowired
    private Job enzymeXmlJobTest;

    @Autowired
    private Job proteinXmlJobTest;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private XmlFileProperties xmlFileProperties;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();

    }

    @After
    public void closeResources() throws SQLException {
        entityManager.close();
    }

    @Test
    public void successfulProteinCentricJobRun() throws Exception {

        String query = "SELECT COUNT(*) FROM(select * from PROTEIN_GROUPS where ENTRY_TYPE=0 AND ROWNUM <=1 UNION (select * from PROTEIN_GROUPS where ENTRY_TYPE=1 AND ROWNUM <=2))";

        int expectedEntries = countEntries(query);

        JobExecution jobExecution = jobLauncher.run(proteinXmlJobTest, new JobParameters());
        BatchStatus status = jobExecution.getStatus();
        assertThat(status, is(BatchStatus.COMPLETED));

        if (status == BatchStatus.COMPLETED && jobExecution.getExitStatus() != ExitStatus.NOOP) {
            StepExecution step = getStepByName(MockProteinCentricConfig.PROTEIN_READ_PROCESS_WRITE_XML_STEP, jobExecution);

            assertThat(step.getReadCount(), is(expectedEntries));
            assertThat(step.getWriteCount(), is(expectedEntries));
            assertThat(step.getSkipCount(), is(0));

            printXml(xmlFileProperties.getProteinCentric());
        }

    }

    @Test
    public void successfulEnzymeCentricJobRun() throws Exception {

        String query = "SELECT COUNT(*) FROM(select * from ENZYME_PORTAL_UNIQUE_EC where ROWNUM <=1)";

        int expectedEntries = countEntries(query);

        JobExecution jobExecution = jobLauncher.run(enzymeXmlJobTest, new JobParameters());
        BatchStatus status = jobExecution.getStatus();
        assertThat(status, is(BatchStatus.COMPLETED));

        StepExecution step = getStepByName(MockEnzymeCentricConfig.ENZYME_READ_PROCESS_WRITE_XML_STEP, jobExecution);

        assertThat(step.getReadCount(), is(expectedEntries));
        assertThat(step.getWriteCount(), is(expectedEntries));
        assertThat(step.getSkipCount(), is(0));

        printXml(xmlFileProperties.getEnzymeCentric());

    }

    private StepExecution getStepByName(String stepName, JobExecution jobExecution) {
//        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
//            if (stepExecution.getStepName().equals(stepName)) {
//                return stepExecution;
//            }

//        }
        //throw new IllegalArgumentException("Step name not recognized: " + stepName);
        return jobExecution.getStepExecutions()
                .stream()
                .peek(s -> System.out.println("Job Status : " + s.getStatus().name()))
                .filter(stepExecution -> stepExecution.getStepName().equalsIgnoreCase(stepName))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);

    }

    private int countEntries(String queryString) {
        Query query = entityManager.createNativeQuery(queryString);
        String count = query.getSingleResult().toString();
        return Integer.parseInt(count);
    }

    private void printXml(String fileLocation) throws IOException {
        try (FileReader fileReader = new FileReader(fileLocation);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}