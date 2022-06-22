package com.auxil.pump.Batch.Scheduler;


//import com.auxil.pump.Batch.job.TagReadJob;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ReadScheduler {

    @Autowired
    private Job tagReadJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(cron = "0 */1 * * * *")
    public void tagReadJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime",new JobParameter(System.currentTimeMillis()))
        );

        try {

            jobLauncher.run( tagReadJob, jobParameters);

        } catch (JobRestartException e) {
            e.printStackTrace();
        }
    }



}
