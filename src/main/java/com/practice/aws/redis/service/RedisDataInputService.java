package com.practice.aws.redis.service;

import java.io.IOException;
import java.util.Map;

public interface RedisDataInputService {

     void putData(String key);

    Object getValueByKey(String key,Class<?> cls) throws IOException;

    void insertHashData(String key);

     Map<Object,Object> getHashData(String hashKey);
}
