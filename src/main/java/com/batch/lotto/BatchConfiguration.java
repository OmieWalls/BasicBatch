package com.batch.lotto;

import com.batch.lotto.step.processor.Processor;
import com.batch.lotto.step.reader.Reader;
import com.batch.lotto.step.writer.Writer;
import com.batch.lotto.to.Lottery;
import com.google.appengine.api.datastore.Entity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableBatchProcessing
@ComponentScan
public class BatchConfiguration extends DefaultBatchConfigurer {

    @Autowired
    public JobBuilderFactory job;

    @Autowired
    public StepBuilderFactory steps;

    @Bean
    public DataSource dataSource() {

        // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
                .addScript("db/sql/create-db.sql")
                .addScript("db/sql/insert-data.sql")
                .build();
        return db;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        //This BatchConfigurer ignores any DataSource
    }

    @Bean
    public Job lotteryJob(JobCompletionNotificationListener listener) {
        return this.job.get("eventJob")
                .start(load(listener))
                .build();
    }

    @Bean
    public Step load(JobCompletionNotificationListener listener) {
        return this.steps.get("eventLoad")
                .<List<Lottery>, List<Entity>>chunk(100)
                .reader(new Reader())
                .processor(new Processor())
                .writer(new Writer())
                .listener(listener)
                .transactionManager(transactionManager())
                .build();
    }
}