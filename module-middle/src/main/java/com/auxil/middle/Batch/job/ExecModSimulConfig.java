package com.auxil.middle.Batch.job;


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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ExecModSimulConfig  {

    @Lazy
    private final JobBuilderFactory jobBuilderFactory;
    @Lazy
    private final StepBuilderFactory stepBuilderFactory;



    @Bean
    public Job execModsimulJob(){
        return jobBuilderFactory.get("execModsimulJob")
                .incrementer(new RunIdIncrementer())
                .start(execModsimulStep())
                .build();
    }

    @JobScope
    @Bean
    public Step execModsimulStep() {
        return stepBuilderFactory.get("execModsimulStep")
                .tasklet(execModsimulTasklet())
                .build();
    }

    @StepScope
    @Bean
    public Tasklet execModsimulTasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

//                Runtime rt = Runtime.getRuntime();

//                System.out.println(file);
//                log.info("file : " + file);
//
//
//                Process pro;
//
//                try{
//                    pro = rt.exec(file);
//                    pro.waitFor();
//                    System.out.println(file);
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }



                return RepeatStatus.FINISHED;
            }

        };
    }



}
