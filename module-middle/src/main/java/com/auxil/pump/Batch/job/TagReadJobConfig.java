package com.auxil.pump.Batch.job;


//import com.auxil.pump.domain.TbEquipInfo;
//import com.auxil.pump.domain.TbTagBase;
//import com.auxil.pump.dto.RealTimeModbusConnDTO;
//import com.auxil.pump.service.RealTimeService;
//import com.auxil.pump.service.TbService;
import com.auxil.pump.Service.ApiService;
import com.auxil.pump.domain.TbEquipInfo;
import com.auxil.pump.domain.TbTagBase;
import com.auxil.pump.dto.RealTimeModbusConnDTO;
import com.auxil.pump.service.RealTimeService;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class TagReadJobConfig  {

    @Lazy
    private final JobBuilderFactory jobBuilderFactory;
    @Lazy
    private final StepBuilderFactory stepBuilderFactory;
    @Lazy
    private final RealTimeService realTimeService;

//    @Lazy
//    private final TbService tbService;
//
    @Lazy
    private final ApiService apiService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;



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

                List<TbTagBase> allTag =   apiService.getAllTag();
                Set<Long> equipSet = new HashSet<>();

                allTag.forEach( es ->  equipSet.add(es.getEquipid().getEquipid()));


                for( Long equipId : equipSet){
                    List<RealTimeModbusConnDTO> dtoList = new ArrayList<>();
                    List<TbTagBase> modtagList = allTag.stream().filter(at -> at.getEquipid().getEquipid() == equipId).collect(Collectors.toList());
                    TbEquipInfo equipInfo = modtagList.get(0).getEquipid();
                    String equipType = equipInfo.getEquip_type().getEquip_type();
                    if(equipType.equalsIgnoreCase("MODBUS")) {

                        List<TbTagBase> tagGroup = allTag.stream().filter(at -> at.getEquipid().getEquipid() == equipId).collect(Collectors.toList());

                        tagGroup.stream().forEach(tag -> dtoList.add(new RealTimeModbusConnDTO(tag.getMemorydevicename(), tag.getAddress())));

                        Map<String,Integer> addrValMap =  realTimeService.readModValue(equipInfo , dtoList);
                        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
                        addrValMap.keySet().stream().filter(key -> addrValMap.get(key) != null)
                                .forEach(key ->  valueOperations.set(key, String.valueOf(addrValMap.get(key)), Duration.ofHours(1)));
                    }
                }
                return RepeatStatus.FINISHED;
            }
        };
    }
}
