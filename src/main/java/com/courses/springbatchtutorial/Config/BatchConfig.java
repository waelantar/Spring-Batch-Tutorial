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
        return new FlatFileItemReaderBuilder<BankTransaction>()
                .name("TransactionItemReader")
                .resource(new PathResource(path))
                .delimited()
                .delimiter(",")
                .strict(false)
                .names("accountNo", "date", "transactionDetails", "chequeNo", "valueDate", "withdrawalAmt", "depositAmt", "balanceAmt")
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(BankTransaction.class);
                }})
                .build();
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
