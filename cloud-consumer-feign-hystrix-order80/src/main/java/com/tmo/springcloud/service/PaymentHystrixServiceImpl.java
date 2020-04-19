package com.tmo.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentHystrixServiceImpl implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "-----paymentFallBackService fall back-paymentInfo_ok";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "-----paymentFallBackService fall back-paymentInfo_TimeOut";
    }
}
