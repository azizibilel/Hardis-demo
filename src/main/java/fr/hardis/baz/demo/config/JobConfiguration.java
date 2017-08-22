package fr.hardis.baz.demo.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import fr.hardis.baz.demo.batch.listener.JobListener;
import fr.hardis.baz.demo.batch.mapper.ReferenceFieldSetMapper;
import fr.hardis.baz.demo.batch.writer.Writer;
import fr.hardis.baz.demo.dto.Reference;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {
	
	private static final Logger log = LoggerFactory.getLogger(JobConfiguration.class);
	
	private static final String READER_SEPERATOR = ";";

    @Autowired
    private JobBuilderFactory jobs;
    
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobListener listener;
    

    @Bean
    public Job convertJob(){
        return jobs.get("convertJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step())
                .end()
                .build();
    }

    @Bean
    public Step step(){
        return stepBuilderFactory.get("step")
                .<Reference,Reference>chunk(1000)
                .reader(reader(null))
                .writer(writer(null, null, null))
                .build();
    }


    /**
     *
     * @param filePath
     * @param outFilePath
     * @param format
     * @return itemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<Reference> writer(@Value("#{jobParameters[filePath]}") String filePath,
    		@Value("#{jobParameters[outFilePath]}") String outFilePath,
    		@Value("#{jobParameters[format]}") String format){
        Writer fileWriter = new Writer();
        fileWriter.setFilePath(filePath);
        fileWriter.setOutFilePath(outFilePath);
        fileWriter.setFormat(format);
        return fileWriter;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Reference> reader(@Value("#{jobParameters[filePath]}") String filePath){
        log.debug("filePath = {}", filePath);
        FlatFileItemReader<Reference> reader = new FlatFileItemReader<>();
        //reader.setLinesToSkip(1);//first line is title definition
        reader.setResource(getFileFromDirectory(filePath));
        reader.setLineMapper(lineMapper());
        return reader;
    }
    private Resource getFileFromDirectory(String filePath) {
        File fl = new File(filePath);
        return new FileSystemResource(fl);
    }


    @Bean
    public LineMapper<Reference> lineMapper() {
        DefaultLineMapper<Reference> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(READER_SEPERATOR);
        lineTokenizer.setStrict(false);

        BeanWrapperFieldSetMapper<Reference> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Reference.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(referenceFieldSetMapper());

        return lineMapper;
    }

    @Bean
    public ReferenceFieldSetMapper referenceFieldSetMapper() {
        return new ReferenceFieldSetMapper();
    }

}
