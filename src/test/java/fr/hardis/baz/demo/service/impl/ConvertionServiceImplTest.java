package fr.hardis.baz.demo.service.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import fr.hardis.baz.demo.test.config.BatchConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest
@Import(value = { BatchConfiguration.class })
@ActiveProfiles("test")
public class ConvertionServiceImplTest {
	
	@Autowired
	@Qualifier(value = "jobLauncherTestUtils")
    private JobLauncherTestUtils jobLauncherTestUtils;
	
	
	@Test
	public void convertToXml() throws Exception {
		final Date currentDate = new Date(); 
		JobParameters param = new JobParametersBuilder()
                .addDate("date",currentDate)
                .addString("filePath", "src/main/resources/data/import.txt")
                .addString("outFilePath", "src/main/resources/data/import-" + currentDate.getTime() + ".xml")
                .addString("format", "xml")
                .toJobParameters();
		BatchStatus batchStatus = jobLauncherTestUtils.launchJob(param).getStatus();
		Assert.assertEquals(batchStatus.name(), BatchStatus.COMPLETED.name());
	}
	
	@Test
	public void convertToJson() throws Exception {
		final Date currentDate = new Date(); 
		JobParameters param = new JobParametersBuilder()
                .addDate("date", currentDate)
                .addString("filePath", "src/main/resources/data/import.txt")
                .addString("outFilePath", "src/main/resources/data/import" + currentDate.getTime() + ".json")
                .addString("format", "json")
                .toJobParameters();
		BatchStatus batchStatus = jobLauncherTestUtils.launchJob(param).getStatus();
		Assert.assertEquals(batchStatus.name(), BatchStatus.COMPLETED.name());
	}
	
}