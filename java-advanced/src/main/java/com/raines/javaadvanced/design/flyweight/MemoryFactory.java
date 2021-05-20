package com.raines.javaadvanced.design.flyweight;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class MemoryFactory {

    //内存对象列表
    private static List<Memory> memoryList = new ArrayList<>();

    public static Memory getMemory(int size){
        Memory memory = null;
        for (int i = 0;i<memoryList.size();i++){
            memory = memoryList.get(i);
            //如果存在和需求size一样大小并且未使用的内存块，则直接返回
            if (memory.getSize() == size && !memory.isIsused()) {
                memory.setIsused(true);
                memoryList.set(i,memory);
                log.info("从集合中获取内存对象"+ JSON.toJSONString(memory));
                break;
            }
        }
        //如果内存不存在，则从系统中申请新的内存返回，并将该内存加入到内存对象列表中
        if (memory == null){
            memory = new Memory(32,false, UUID.randomUUID().toString());
            log.info("添加一个内存对象到集合");
            memoryList.add(memory);
        }
        return memory;
    }

    //释放内存
    public static void releaseMemory(String id){
        for (int i = 0; i < memoryList.size(); i++) {
            Memory memory = memoryList.get(i);
            //如果存在和需求size一样大小并且未使用的内存块，则直接返回
            if (memory.getId().equals(id)){
                memory.setIsused(false);
                memoryList.set(i,memory);
                log.info("释放内存对象"+id);
                break;
            }
        }
    }

}
