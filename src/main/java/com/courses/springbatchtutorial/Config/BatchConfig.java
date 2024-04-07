package com.courses.springbatchtutorial.Config;

import com.courses.springbatchtutorial.entity.BankTransaction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration

public class BatchConfig {
    @Value("${file.path}")
String path;

    @Bean
    public ItemReader<BankTransaction> reader() {
        return new FlatFileItemReaderBuilder<BankTransaction>() // Builder pattern used to construct FlatFileItemReader
                .name("TransactionItemReader") // Name of the reader
                .resource(new PathResource(path)) // Resource from which data will be read (file path)
                .delimited() // Indicates that the file is delimited (CSV format)
                .delimiter(",") // Delimiter used in the CSV file
                .strict(false) // Ignore parsing errors
                .names("accountNo", "date", "transactionDetails", "chequeNo", "valueDate", "withdrawalAmt", "depositAmt", "balanceAmt") // Names of the fields in the CSV file
                .linesToSkip(1) // Skip the header line
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{ // Mapper to map fields from CSV to BankTransaction object
                    setTargetType(BankTransaction.class); // Target type for mapping
                }})
                .build(); // Build the FlatFileItemReader
    }



    @Bean
    public ItemProcessor<BankTransaction, BankTransaction> processor() {

        return new BankTransactionProcessor();// Perform any necessary processing here

    }

    @Bean
    public ItemWriter<BankTransaction> writer() {
        return new BankTransactionWriter();

    }
    @Bean
    public Step step(ItemReader<BankTransaction> reader, ItemProcessor<BankTransaction, BankTransaction> processor,
                     ItemWriter<BankTransaction> writer, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        // Configure and build a Step for processing bank transactions
        return new StepBuilder("myStep", jobRepository)
                .<BankTransaction, BankTransaction>chunk(10, transactionManager) // Chunk-oriented step, committing every 10 items
                .reader(reader) // Set the reader
                .processor(processor) // Set the processor
                .writer(writer) // Set the writer
                .build(); // Build the Step
    }

    @Bean
    public Job job(Step step, JobRepository jobRepository) {
        // Configure and build a Job for processing bank transactions
        return new JobBuilder("myJob", jobRepository)
                .incrementer(new RunIdIncrementer()) // Incrementer to generate unique job instances
                .flow(step) // Add the step to the job flow
                .end() // End the job flow
                .build(); // Build the Job
    }


}
