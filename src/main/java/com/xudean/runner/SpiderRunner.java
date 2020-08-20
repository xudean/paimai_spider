package com.xudean.runner;

import com.xudean.spider.AliFangChanSpier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SpiderRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        AliFangChanSpier.startSpider();
    }
}
