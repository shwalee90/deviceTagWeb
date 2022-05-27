package com.auxil.pump.Batch.job;


//import com.auxil.pump.domain.TbEquipInfo;
//import com.auxil.pump.domain.TbTagBase;
//import com.auxil.pump.dto.RealTimeModbusConnDTO;
//import com.auxil.pump.service.RealTimeService;
//import com.auxil.pump.service.TbService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TagReadJobConfig  {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//    @Autowired
//    private TbService tbService;

//    @Autowired
//    private RealTimeService realTimeService;


    @Bean
    public Job tagReadJob(){
        return jobBuilderFactory.get("tagReadJob")
                .incrementer(new RunIdIncrementer())
                .start(tagReadStep())
                .build();
    }

    @JobScope
    @Bean
    public Step tagReadStep() {
        return stepBuilderFactory.get("tagReadStep")
                .tasklet(tagReadTasklet())
                .build();
    }

    @StepScope
    @Bean
    public Tasklet tagReadTasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                log.info("schedule ok");

                return RepeatStatus.FINISHED;
            }



        };
    }


}
