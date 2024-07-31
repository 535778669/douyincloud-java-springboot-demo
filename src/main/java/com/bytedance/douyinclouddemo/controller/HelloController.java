package com.bytedance.douyinclouddemo.controller;

import com.bytedance.douyinclouddemo.model.JsonResponse;
import com.bytedance.douyinclouddemo.model.SetNameRequest;
import com.bytedance.douyinclouddemo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @Value("${cloud.env}")
    private String envMark;

    @GetMapping("/api/hello")
    public JsonResponse hello(@RequestParam(value = "target", defaultValue = "mongodb") String target) {
        JsonResponse response = new JsonResponse();
        try {
            //HelloService helloService = factory.getHelloService(target);
            //response.success("env:" + envMark + " hello " +  helloService.hello(target));
        }catch (Exception e){
            response.failure("unknown error");
        }
        return response;
    }

    @PostMapping("/api/set_name")
    public JsonResponse setName(@RequestBody SetNameRequest setNameRequest) {
        JsonResponse response = new JsonResponse();
        try {
            //HelloService helloService = factory.getHelloService(setNameRequest.getTarget());
           // helloService.setName(setNameRequest.getTarget(),setNameRequest.getName());
            response.success("");
        }catch (Exception e){
            response.failure("unknown error");
        }
        return response;
    }
    @PostMapping("/api/aast")
    public JsonResponse aast(@RequestBody SetNameRequest setNameRequest) {
        JsonResponse response = new JsonResponse();
        try {
            //HelloService helloService = factory.getHelloService(setNameRequest.getTarget());
            //helloService.setName(setNameRequest.getTarget(),setNameRequest.getName());
            response.success("");
        }catch (Exception e){
            response.failure("unknown error");
        }
        return response;
    }
}
