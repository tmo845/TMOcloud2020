package com.tmo.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {
    ServiceInstance INSTANCE(List<ServiceInstance> serviceInstances);
}
