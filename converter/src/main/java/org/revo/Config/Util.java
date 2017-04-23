package org.revo.Config;

import org.revo.Util.Hls;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;

import java.io.File;
import java.util.UUID;

/**
 * Created by ashraf on 23/04/17.
 */
@Configuration
public class Util {
    @Bean
    CommandLineRunner runner() {
        return args -> {
            File bento4 = new ClassPathResource("Bento4").getFile();
            Hls.init(bento4);
        };
    }

    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "5000", maxMessagesPerPoll = "1"))
    public MessageSource<String> timerMessageSource() {
        return () -> MessageBuilder.withPayload(UUID.randomUUID().toString()).build();
    }

}
