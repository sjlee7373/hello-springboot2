package io.zia.springboot.hellospringboot;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@Configuration
class DisableSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/**");
    }
}
@SpringBootApplication
public class HelloSpringbootApplication {

//    final static Logger log=Logger.getLogger(HelloSpringbootApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringbootApplication.class, args);
    }
}

@RestController
@Slf4j
class HelloRest1Controller{
    private MeterRegistry meterRegistry;
    @Value("${message:default message}")
    String message;

    public HelloRest1Controller(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }
    @GetMapping("/hello")
    public String Hello(){
        log.debug("Message"  + message);
        meterRegistry.counter("hello.invoke.count").increment();
        return  message;
    }

    @GetMapping("/test")
    public String test(){
        meterRegistry.counter("test.invoke.count").increment();
        return  message;
    }
}