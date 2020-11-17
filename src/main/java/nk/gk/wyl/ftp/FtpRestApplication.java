package nk.gk.wyl.ftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FtpRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FtpRestApplication.class, args);
    }

}
