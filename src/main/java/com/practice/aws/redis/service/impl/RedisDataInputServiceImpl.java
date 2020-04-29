package com.practice.aws.redis.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.aws.redis.model.Product;
import com.practice.aws.redis.service.RedisDataInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author imran ahmad
 */
@Service
public class RedisDataInputServiceImpl implements RedisDataInputService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, Object> redisUserTemplate;

    @Override
    public void putData(String key) {
        Product product=new Product();
        product.setId(1);product.setName("test");product.setPrice(1000);
        saveDataInRedis(key,product);
    }
    public Map<String, Object> saveDataInRedis(String id, Object obj) {
        Map<String, Object> result = new HashMap<>();
        String jsonObj = "";
        try {
            jsonObj = objectMapper.writeValueAsString(obj);
            System.out.println(jsonObj);
        } catch (JsonProcessingException jpe) {

        }
        redisUserTemplate.opsForValue().set(id, obj);
        result.put("isSuccess", true);
        result.put("massage", "Data saved succesfully in redis");
        return result;
    }

    public Object getValueByKey(String key,Class<?> cls) throws IOException {
        Object value=redisUserTemplate.opsForValue().get(key);
       // redisUserTemplate.opsForHash().
        //Object obj=objectMapper.readValue(value.toString(),cls);
        System.out.println("Received value from cache "+ value);
        return value;
    }

    public void putInCacheHash(String hashKeyword, Object field, Object value) {
        System.out.println("inserting data in cache for hash and  key value" +
                "details:  key " + field +" hash "+hashKeyword);
        try
        {
            redisUserTemplate.opsForHash().put(hashKeyword,field,value);
        } catch (Exception ex) {
            System.out.println("exception putting data in cache for key. details:  key " + field +" hash "+hashKeyword);

        }
    }

    public void insertHashData(String key) {
        Product product=new Product();
        product.setId(1);product.setName("test");product.setPrice(1000);

        Product product2=new Product();
        product.setId(2);product.setName("test2");product.setPrice(1002);
        putInCacheHash(key,"one",product);
        putInCacheHash(key,"two",product2);
    }

    public Map<Object,Object> getHashData(String hashKey) {
        return redisUserTemplate.opsForHash().entries(hashKey);
    }
}
