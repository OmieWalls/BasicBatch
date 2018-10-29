package com.batch.lotto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("JOB CREATED!");
    }


    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("JOB COMPLETED! Verify results: ");

        } else if (jobExecution.getStatus() == BatchStatus.FAILED){
            log.info("JOB FAILED! Results: " + "Start Time - " +
                    jobExecution.getStartTime() + "End Time - " +
                    jobExecution.getEndTime() + "Configuration Name - " +
                    jobExecution.getJobConfigurationName() + "Exit Status - " +
                    jobExecution.getExitStatus());
        }
    }
}