package org.imfreco.msvc.courses;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Objects;

@SpringBootApplication
@EnableFeignClients
public class MsvcCoursesApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().directory("./msvc-courses/").load();
        System.setProperty("PORT", Objects.requireNonNull(dotenv.get("PORT")));
        System.setProperty("DB_HOST", Objects.requireNonNull(dotenv.get("DB_HOST")));
        System.setProperty("DB_NAME", Objects.requireNonNull(dotenv.get("DB_NAME")));
        System.setProperty("DB_USER", Objects.requireNonNull(dotenv.get("DB_USER")));
        System.setProperty("DB_PASS", Objects.requireNonNull(dotenv.get("DB_PASS")));
        System.setProperty("USERS_CLIENT_URL", Objects.requireNonNull(dotenv.get("USERS_CLIENT_URL")));

        SpringApplication.run(MsvcCoursesApplication.class, args);
    }

}
