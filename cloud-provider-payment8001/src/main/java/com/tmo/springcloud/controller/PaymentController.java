package com.tmo.springcloud.controller;

import com.tmo.springcloud.entities.CommonResult;
import com.tmo.springcloud.entities.Payment;
import com.tmo.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;


    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult creat(@RequestBody Payment payment){

        int result = paymentService.create( payment);
        log.info("插入结果："+result);
        if (result>0){
            return new CommonResult(200,"插入数据库成功,serverPort:"+serverPort,result);
        }else {
            return new CommonResult(444,"插入数据库失败",null);
        }
    }
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPAymentById(@PathVariable("id") Long id){

        Payment result = paymentService.getPaymentById(id);
        log.info("查询结果："+result+"aaaa");
        if (result != null){
            return new CommonResult(200,"查询成功,serverPort:"+serverPort,result);
        }else {
            return new CommonResult(444,"没有对应记录，查询id"+id,null);
        }
    }


    @GetMapping("/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String e:services
                ) {
            log.info("*******e******:   "+e);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance is:instances
                ) {
            log.info(is.getServiceId()+
                    "\t"+is.getHost()+
                    "\t"+is.getPort()+
                    "\t"+is.getUri());
        }

        return this.discoveryClient;
    }


    @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }


    @GetMapping(value = "/payment/openFeign")
    public String timeOut(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @GetMapping("/payment/zipkin")
    public String paymentZipKin(){
        return "welcome to home (●'◡'●) ";
    }

}
