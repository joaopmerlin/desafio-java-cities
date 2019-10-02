package br.com.joaomerlin.cities.config;

import feign.Client;
import feign.httpclient.ApacheHttpClient;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.ribbon.CachingSpringLoadBalancerFactory;
import org.springframework.cloud.openfeign.ribbon.LoadBalancerFeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@EnableCircuitBreaker
@EnableFeignClients(basePackages = "br.com.joaomerlin.cities.client")
public class FeignConfig {

    @Bean
    public Client client(CachingSpringLoadBalancerFactory lbClientFactory, SpringClientFactory clientFactory) {
        return new LoadBalancerFeignClient(new ApacheHttpClient(), lbClientFactory, clientFactory);
    }

    @Bean
    public LoadBalancedRetryFactory retryFactory(SpringClientFactory clientFactory) {
        return new RibbonLoadBalancedRetryFactory(clientFactory);
    }

}
