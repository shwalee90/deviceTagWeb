package com.auxil.tagweb.service;

import com.auxil.tagweb.domain.TbEquipInfo;
import com.auxil.tagweb.dto.RealTimeModbusConnDTO;
import com.digitalpetri.modbus.master.ModbusTcpMaster;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;
import com.digitalpetri.modbus.requests.*;
import com.digitalpetri.modbus.responses.ReadCoilsResponse;
import com.digitalpetri.modbus.responses.ReadDiscreteInputsResponse;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import com.digitalpetri.modbus.responses.ReadInputRegistersResponse;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class RealTimeService {

    Map<String,Integer> realTimeValueMap = new HashMap<>();

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public void quickStart(){

        System.out.println("start");

        ModbusTcpMasterConfig config = new ModbusTcpMasterConfig.Builder("localhost").setPort(502).build();
        ModbusTcpMaster master = new ModbusTcpMaster(config);

        master.connect();

        CompletableFuture<ReadHoldingRegistersResponse> futureHR =
                master.sendRequest(new ReadHoldingRegistersRequest(0, 1), 0);

        futureHR.thenAccept(response -> {
            System.out.println("Response holding register: " + ByteBufUtil.hexDump(response.getRegisters()));

            System.out.println(ReferenceCountUtil.release(response));
        });


        CompletableFuture<ReadCoilsResponse> futureCoils =
                master.sendRequest(new ReadCoilsRequest(0, 1), 0);

        futureCoils.thenAccept(response -> {
            System.out.println("Response coils: " + ByteBufUtil.hexDump(response.getCoilStatus()));

            System.out.println(ReferenceCountUtil.release(response));
        });
        CompletableFuture<ReadDiscreteInputsResponse> futureDi =
                master.sendRequest(new ReadDiscreteInputsRequest(0, 1), 0);

        futureDi.thenAccept(response -> {
            System.out.println("Response DISCRETE : " + ByteBufUtil.hexDump(response.getInputStatus()));

            System.out.println(ReferenceCountUtil.release(response));
        });
        CompletableFuture<ReadInputRegistersResponse> futureIR =
                master.sendRequest(new ReadInputRegistersRequest(0, 1), 0);

        futureIR.thenAccept(response -> {
            System.out.println("Response INPUT REGISTER: " + ByteBufUtil.hexDump(response.getRegisters()));

            System.out.println(ReferenceCountUtil.release(response));
        });

    }

    public Map<String,Integer> readModValue(TbEquipInfo equipInfo , List<RealTimeModbusConnDTO> connList){


        ModbusTcpMasterConfig config = new ModbusTcpMasterConfig.Builder(equipInfo.getIp()).setPort(equipInfo.getPort()).build();
        ModbusTcpMaster master = new ModbusTcpMaster(config);

        master.connect();

        System.out.println("#######"+connList);

        try{

        for ( RealTimeModbusConnDTO tagList : connList) {
            String memoryName = tagList.getMemoryName();


            if ("C".equalsIgnoreCase(memoryName)) {

                CompletableFuture<ReadCoilsResponse> futureCoils =
                        master.sendRequest(new ReadCoilsRequest(tagList.getAddress(), 1), 0);

                futureCoils.thenAccept(response -> {
                    int decVal = Integer.parseInt(ByteBufUtil.hexDump(response.getCoilStatus()) ,16);
                    realTimeValueMap.put(memoryName+tagList.getAddress() , decVal);
                });

            } else if ("DI".equalsIgnoreCase(memoryName)) {
                CompletableFuture<ReadDiscreteInputsResponse> futureDi =
                        master.sendRequest(new ReadDiscreteInputsRequest(tagList.getAddress(), 1), 0);
                futureDi.thenAccept(response -> {

                    int decVal = Integer.parseInt(ByteBufUtil.hexDump(response.getInputStatus()) , 16);
                    realTimeValueMap.put(memoryName+tagList.getAddress() , decVal);
                });

            } else if ("R".equalsIgnoreCase(memoryName)) {
                CompletableFuture<ReadHoldingRegistersResponse> futureHR =
                        master.sendRequest(new ReadHoldingRegistersRequest(tagList.getAddress(), 1), 0);
                futureHR.thenAccept(response -> {
                    int decVal = Integer.parseInt(ByteBufUtil.hexDump(response.getRegisters()) , 16);
                    realTimeValueMap.put(memoryName+tagList.getAddress() , decVal);
                });

            } else if ("IR".equalsIgnoreCase(memoryName)) {
                CompletableFuture<ReadInputRegistersResponse> futureIR =
                        master.sendRequest(new ReadInputRegistersRequest(tagList.getAddress(), 1), 0);

                futureIR.thenAccept(response -> {
                    int decVal = Integer.parseInt(ByteBufUtil.hexDump(response.getRegisters()) , 16);
                    realTimeValueMap.put(memoryName+tagList.getAddress() , decVal);
                });
            }
        }

        }catch (Exception e){
            master.disconnect();
        }

        return realTimeValueMap;
    }
    public void writeTag(HashMap<String, String> map, TbEquipInfo tbEquipInfo) {

        String address = map.get("address");

        ModbusTcpMasterConfig config = new ModbusTcpMasterConfig.Builder(tbEquipInfo.getIp()).setPort(tbEquipInfo.getPort()).build();
        ModbusTcpMaster master = new ModbusTcpMaster(config);

        master.connect();

        if(address.startsWith("C")){

            int addr = Integer.parseInt(address.replace("C", ""));

            master.sendRequest(new WriteSingleCoilRequest(addr ,Boolean.parseBoolean(map.get("writeValue"))), 0);

        }

        else if(address.startsWith("R")){
            int addr = Integer.parseInt(address.replace("R", ""));

            master.sendRequest(new WriteSingleRegisterRequest(addr , Integer.parseInt(map.get("writeValue"))), 0);

        }

    }

    public Map<String, Integer> getRedis(List<RealTimeModbusConnDTO> tagList, long equipid) {

        Map<String,Integer> redisMap = new HashMap<>();

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        try{

        tagList.stream().forEach( fe -> redisMap.put(equipid+":"+fe.getMemoryName()+fe.getAddress()
                , Integer.parseInt(valueOperations.get(equipid+":"+fe.getMemoryName()+fe.getAddress()))));
        }  catch (NumberFormatException ne){
            ne.getStackTrace();
        }


        return redisMap;
    }
}
