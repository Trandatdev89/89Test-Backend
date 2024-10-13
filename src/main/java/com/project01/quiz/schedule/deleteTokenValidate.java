package com.project01.quiz.schedule;

import com.project01.quiz.repository.InvalidateTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
public class deleteTokenValidate {

    @Autowired
    private InvalidateTokenRepository invalidateTokenRepository;

    @Scheduled(fixedDelay = 1800000,initialDelay = 5000)
    public void scheduleFixedDelayTask() {
        invalidateTokenRepository.deleteExpiryTime();
        System.out.println("Delete token expiry time Success!");
    }
}
