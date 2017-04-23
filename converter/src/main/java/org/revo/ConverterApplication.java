package org.revo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*
@EnableBinding({Processor.class})
*/
//@EnableEurekaClient

public class ConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConverterApplication.class, args);
    }
    /*

    @Autowired
    private Processor processor;

        this.processor.output().send(MessageBuilder.withPayload(value).build());
    }
*/

}
/*

@MessageEndpoint
class ss {
    @StreamListener(Processor.INPUT)
    public void ss(Message<String> message) throws InterruptedException {
        System.out.println(message.getPayload());
        Thread.sleep(1000);
    }
}
*/
