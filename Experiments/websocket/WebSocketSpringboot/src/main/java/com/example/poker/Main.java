package com.example.poker;

import com.example.poker.PlayerStats.StatsRepository;
import com.example.poker.Results.GameResultRepository;
import com.example.poker.Session.GameSessionRepository;
import com.example.poker.Users.User;
import com.example.poker.Users.UserController;
import com.example.poker.Users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;


@SpringBootApplication
@EnableWebSocket
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initData(
            UserRepository userRepository,
            GameSessionRepository gameSessionRepository,
            GameResultRepository gameResultRepository,
            StatsRepository statsRepository) {
        return args -> {

            User user777 = new User();
            user777.setUsername("Elicia");
            user777.setEmail("Elicia@somemail.com");
            user777.setPassword("123");
            user777.setChips(1000);

            userRepository.save(user777);
        };
    }
}
