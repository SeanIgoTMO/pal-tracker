package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class EnvController {

    private HashMap<String,String> env;

    @GetMapping("/env")
    public Map<String,String> getEnv()
    {
        return env;
    }

    public EnvController(
        @Value("${PORT:NOT SET}") String port,
        @Value("${MEMORY_LIMIT:NOT SET}") String memory_limit,
        @Value("${CF_INSTANCE_INDEX:NOT SET}") String cf_instance_index,
        @Value("${CF_INSTANCE_ADDR:NOT SET}") String cf_instance_addr

    ) {
        env = new HashMap<String,String>();
        env.put("PORT",port);
        env.put("MEMORY_LIMIT",memory_limit);
        env.put("CF_INSTANCE_INDEX",cf_instance_index);
        env.put("CF_INSTANCE_ADDR",cf_instance_addr);
    }

    //@GetMapping("/")
    //public String sayHello() {
    //    return welcome_message;
    //}
}
