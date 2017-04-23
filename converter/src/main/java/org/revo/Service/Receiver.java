package org.revo.Service;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;

import java.util.stream.Collectors;

/**
 * Created by ashraf on 23/04/17.
 */
@MessageEndpoint
public class Receiver {
    @StreamListener(Processor.INPUT)
    public void receive(Message<String> message) throws InterruptedException {
        System.out.println(message.getPayload()+"   "+message.getHeaders().entrySet().stream().map(Object::toString).collect(Collectors.joining(",")));
    }
}
