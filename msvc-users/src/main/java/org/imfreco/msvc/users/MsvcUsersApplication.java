package org.imfreco.msvc.users;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Objects;

@SpringBootApplication
@EnableFeignClients
public class MsvcUsersApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().directory("./msvc-users/").load();
        System.setProperty("PORT", Objects.requireNonNull(dotenv.get("PORT")));
        System.setProperty("DB_HOST", Objects.requireNonNull(dotenv.get("DB_HOST")));
        System.setProperty("DB_NAME", Objects.requireNonNull(dotenv.get("DB_NAME")));
        System.setProperty("DB_USER", Objects.requireNonNull(dotenv.get("DB_USER")));
        System.setProperty("DB_PASS", Objects.requireNonNull(dotenv.get("DB_PASS")));
        System.setProperty("COURSES_CLIENT_URL", Objects.requireNonNull(dotenv.get("COURSES_CLIENT_URL")));

        SpringApplication.run(MsvcUsersApplication.class, args);
    }

}
