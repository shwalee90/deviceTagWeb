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

import java.io.File;

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

                String path = System.getProperty("user.dir");

                log.info(path);

                Runtime rt = Runtime.getRuntime();

                Process pro;

                String addPath = "\\module-middle\\src\\main\\resources\\ModRSsim2.exe";

                File modSim = new File(path+addPath);
                if(modSim.exists()){
                    try{
                        pro = rt.exec( path+addPath);
                        pro.waitFor();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    log.info("simulator 가 " + path+addPath + "위치에 존재하지 않습니다.");
                }

                return RepeatStatus.FINISHED;
            }

        };
    }



}
