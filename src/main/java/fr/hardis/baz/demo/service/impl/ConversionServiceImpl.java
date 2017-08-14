package fr.hardis.baz.demo.service.impl;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import fr.hardis.baz.demo.service.ConversionService;

@Service
public class ConversionServiceImpl implements ConversionService {

    @Autowired
    @Qualifier("convertJob")
    private Job convertJob;

    @Autowired
    private JobLauncher jobLauncher;

    public void convert(String intFilePath, String format, String outFilePath) throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addDate("date", new Date())
                .addString("filePath", intFilePath)
                .addString("outFilePath", outFilePath)
                .addString("format", format)
                .toJobParameters();

        jobLauncher.run(convertJob, jobParameters);
    }
}
