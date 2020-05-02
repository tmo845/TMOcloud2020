package com.tmo.springcloud.alibaba.service.impl;

import com.tmo.springcloud.alibaba.dao.OrderDao;
import com.tmo.springcloud.alibaba.domain.Order;
import com.tmo.springcloud.alibaba.service.AccountService;
import com.tmo.springcloud.alibaba.service.OrderService;
import com.tmo.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private StorageService storageService;


    @Override
    @GlobalTransactional(name = "fsp_create_order",rollbackFor = Exception.class)
    public void create(Order order) {
        //1.开始创建订单
        log.info("---------->开始创建订单");
        orderDao.create(order);

        log.info("订单微服务开始调库存微服务，做扣减count");
        //2.扣减库存
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("订单微服务开始调库存微服务，做扣减end");

        log.info("订单微服务开始调账户微服务，做扣减money");
        //3.扣减账户
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("订单微服务开始调账户微服务，做扣减end");

        //4.修改订单状态 从0------>1
        log.info("---------->开始修改订单");
        orderDao.update(order.getUserId(),0);
        log.info("---------->修改订单结束");


        log.info("---------->下订单结束，O(∩_∩)O");
    }
}
