package com.carService.api.gateway.gatewayservice.filter;

import com.carService.api.gateway.gatewayservice.Exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private RestTemplate restTemplate;


    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            //header contains token or not
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                byte[] bytes = "builder".getBytes(StandardCharsets.UTF_8);
                throw new ApplicationException("missing authorization header", "Pass required header", HttpStatus.BAD_REQUEST);
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }
            try {
                String response = restTemplate.getForObject("http://localhost:8080/auth/validateToken?token=" + authHeader, String.class);
                System.out.println(exchange.getRequest().getPath());
                if (exchange.getRequest().getPath().toString().contains("buyer") && !response.equalsIgnoreCase("BUYER")) {
                    throw new Exception("NOT Authorized");
                }

                if (exchange.getRequest().getPath().toString().contains("seller") && !response.equalsIgnoreCase("SELLER")) {
                    throw new Exception("NOT Authorized");
                }

            } catch (Exception e) {
                System.out.println("invalid access...!");
                throw new ApplicationException("unauthorized access to application", e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
