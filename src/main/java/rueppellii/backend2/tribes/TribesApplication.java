package rueppellii.backend2.tribes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TribesApplication {

//  @Bean
//  public BCryptPasswordEncoder bCryptPasswordEncoder() {
//    return new BCryptPasswordEncoder();
//  }

  public static void main(String[] args) {
    SpringApplication.run(TribesApplication.class, args);
  }

}

