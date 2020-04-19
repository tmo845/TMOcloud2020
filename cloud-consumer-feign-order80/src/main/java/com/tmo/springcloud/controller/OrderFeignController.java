package com.tmo.springcloud.controller;

import com.tmo.springcloud.entities.CommonResult;
import com.tmo.springcloud.entities.Payment;
import com.tmo.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
@RestController
@Slf4j
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    /**
     * 测试OpenFeign的超时控制
     * @return
     */
    @GetMapping(value = "/consumer/payment/openFeign")
    public String timeOut(){
        return paymentFeignService.timeOut();
    }
}
