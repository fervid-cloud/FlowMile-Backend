package com.example.demo.utilities;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SampleDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public SampleDataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        int len = args.length;
        log.info("-----------------------------------------------");
        log.info("printing the argument data of commandLineRunner");
        for(int i = 0; i < len; ++i) {
            System.out.println(args[i]);
        }
        log.info("end of printing of command line runner");
        log.info("------------------------------------------------");
        int n = 10;
        for(int i = 1 ; i <= n; ++i) {
            User user = new User();
            user.setUserId(i);
            user.setUsername("user" + i + "@test.com");
            user.setEmailId(user.getUsername());
            user.setFirstName("firstName" + i);
            user.setLastName("lastName + i");
            user.setAge(i);
            user.setPhoneNumber("12345689" + i);
            user.setPassword("123" + i);
            userRepository.save(user);
        }
    }
}
