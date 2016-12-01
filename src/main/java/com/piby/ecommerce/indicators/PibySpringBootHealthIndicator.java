package com.piby.ecommerce.indicators;
//package com.piby.springboot.indicators;
//
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import org.springframework.boot.actuate.health.Health;
//import org.springframework.boot.actuate.health.HealthIndicator;
//import org.springframework.stereotype.Component;
//
///**
// * Created by marco on 29/11/2016.
// */
//
//@Component
//public class PibySpringBootHealthIndicator implements HealthIndicator {
//
//    @Override
//    public Health health() {
//
//        try {
//            String url = "http://localhost:8080/";
//            int respondeCode = ((HttpURLConnection) new URL(url).openConnection()).getResponseCode();
//            if(respondeCode >= 200 && respondeCode < 300 ){
//                return Health.up().withDetail("URL", url).build();
//            }else{
//                return Health.down().withDetail("HTTP status  code ", respondeCode).withDetail("URL", url).build();
//            }
//        } catch (IOException e) {
//            return Health.down(e).build();
//        }
//    }
//}
