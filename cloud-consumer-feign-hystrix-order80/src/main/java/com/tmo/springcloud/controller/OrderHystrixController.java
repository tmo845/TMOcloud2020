package com.tmo.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.tmo.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Autowired
    private PaymentHystrixService paymentHystrixService;

    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
    //@HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
     //       @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "1500")})
    @HystrixCommand
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        int i=1/0;
        String result = paymentHystrixService.paymentInfo_TimeOut(id);
        return result;
    }
    public String paymentInfo_TimeoutHandler(Integer id){
        return "线程池："+Thread.currentThread().getName()+"80:8001系统繁忙或者运行报错，请稍后再试"+id+"\t"+"/(ㄒoㄒ)/~~";
    }

    //下面是全局处理fallback的方法
    public String payment_Global_FallbackMethod(){
        return "Global异常处理信息，请稍后重试。/(ㄒoㄒ)/~~";
    }
}