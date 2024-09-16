package com.example.poker;

import com.example.poker.PlayerStats.StatsRepository;
import com.example.poker.Results.GameResultRepository;
import com.example.poker.Session.GameSessionRepository;
import com.example.poker.Users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.socket.config.annotation.EnableWebSocket;


@SpringBootApplication
@EnableWebSocket
@EnableJpaRepositories
public class Main {


    //Runs Main
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    //Inits the Table Repos
    @Bean
    CommandLineRunner initData(
            UserRepository userRepository,
            GameSessionRepository gameSessionRepository,
            GameResultRepository gameResultRepository,
            StatsRepository statsRepository) {
        return args -> {

        };
    }
}
