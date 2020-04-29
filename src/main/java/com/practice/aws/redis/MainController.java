package com.practice.aws.redis;

import com.practice.aws.redis.model.Product;
import com.practice.aws.redis.service.RedisDataInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author imran ahmad
 */
@RestController
@RequestMapping("ms1")
public class MainController {

    @Autowired
    private RedisDataInputService redisDataInputService;


    @GetMapping("/message")
    public String home(@RequestParam("key") String key) {


        redisDataInputService.putData(key);

        return "success";
    }

    @GetMapping("/value")
    public Product getValue(@RequestParam("key") String key) throws Exception{


        Product prod=(Product)redisDataInputService.getValueByKey(key, Product.class);
        return prod;
    }
    @GetMapping("/value/new")
    public Product getValueOne(@RequestParam("key") String key) throws Exception{


        Product prod=(Product)redisDataInputService.getValueByKey(key, Product.class);
        prod.setPrice(2000);
        return prod;
    }

    @GetMapping("/put")
    public String put(@RequestParam("key") String key) {


        redisDataInputService.insertHashData(key);

        return "success";
    }

    @GetMapping("/get")
    public Map<Object,Object> get(@RequestParam("key") String key) throws Exception{
        Map<Object,Object> prod=redisDataInputService.getHashData(key);
        return prod;
    }
}
