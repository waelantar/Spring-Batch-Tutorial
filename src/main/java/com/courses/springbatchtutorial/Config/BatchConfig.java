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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired private ItemReader<BankTransaction> bankTransactionItemReader;
    @Autowired private ItemWriter<BankTransaction> bankTransactionItemWriter;
    @Autowired private ItemProcessor<BankTransaction,BankTransaction> bankTransactionItemProcessor;
    @Bean
    public ItemReader<BankTransaction> reader() {
        return new FlatFileItemReaderBuilder<BankTransaction>()
                .name("BankTransactionItemReader")
                .resource(new ClassPathResource("/home/wael/Documents/WAEL WORK/Spring-Batch-Tutorial/src/main/resources/bank.csv"))
                .delimited()
                .delimiter(",")
                .strict(false)
                .names("Account No", "DATE", "TRANSACTION DETAILS","CHQ.NO","VALUE DATE","WITHDRAWAL AMT","DEPOSIT AMT","BALANCE AMT")
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(BankTransaction.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<BankTransaction, BankTransaction> processor() {

        return item -> item; // Perform any necessary processing here

    }

    @Bean
    public ItemWriter<BankTransaction> writer() {
        return items -> {
            for (BankTransaction item : items) {
                // Write item to the destination
            }
        };
    }
    @Bean
    public Step step(ItemReader<BankTransaction> reader, ItemProcessor<BankTransaction, BankTransaction> processor,
                     ItemWriter<BankTransaction> writer, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("myStep",jobRepository)
                .<BankTransaction, BankTransaction>chunk(10,transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }


    @Bean
    public Job job(Step step,JobRepository jobRepository) {
        return new JobBuilder("myJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

}
