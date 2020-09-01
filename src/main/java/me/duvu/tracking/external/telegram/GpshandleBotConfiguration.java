package me.duvu.tracking.external.telegram;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GpshandleBotConfiguration {
    @Value("${vd5.telegram_token}")
    private String telegramToken;

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot.Builder(telegramToken).build();
    }
}
