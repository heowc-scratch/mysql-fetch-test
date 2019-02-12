package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static final String INSERT_SQL = "INSERT INTO push_message (`content`, `status`, `created_by`, `created_at`, `last_modified_at`) VALUES (?, ?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            int insertCount = 100_000;
            int batchSize = 100;

            List<PushMessage> batchList = IntStream.range(0, insertCount)
                    .mapToObj(i -> new PushMessage("content" + i, "wait", "heowc", LocalDateTime.now(), LocalDateTime.now()))
                    .collect(Collectors.toList());

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            jdbcTemplate.batchUpdate(INSERT_SQL, batchList, batchSize, (ps, arg) -> {
                ps.setString(1, arg.getContent());
                ps.setString(2, arg.getStatus());
                ps.setString(3, arg.getCreatedBy());
                ps.setTimestamp(4, Timestamp.valueOf(arg.getCreatedAt()));
                ps.setTimestamp(5, Timestamp.valueOf(arg.getLastModifiedAt()));
            });
            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
        };
    }
}
