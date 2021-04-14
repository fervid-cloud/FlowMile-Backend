package com.mss.polyflow.shared.utilities.functionality;

import com.mss.polyflow.shared.model.User;
import com.mss.polyflow.shared.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SampleDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;


    private final ObjectMapper objectMapper;
    public SampleDataInitializer(UserRepository userRepository,
        ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
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
        initializeUsers();

    }


    private void initializeUsers() {
        int n = 10;
        for(int i = 1 ; i <= n; ++i) {
            userRepository.save(User.builder()
            .userId((long)i)
            .username("user" + i + "@test.com")
            .password("user" + i + "123")
            .email("user" + i + "@test.com")
            .firstName("firstName" + i)
            .lastName("lastName" + i)
            .phoneNumber("123456789" + (i - 1))
            .build());
        }
    }

    private void showData() throws JsonProcessingException {
        String data = "{\"users\":{\"address\":[{\"rue\":\"ruetest\",\"postal\":1111},{\"rue\":\"ruetest\",\"postal\":2222}],\"type\":\"string\",\"user\":[{\"argent\":122,\"id\":1,\"nom\":\"user1\",\"prenom\":\"last1\"},{\"argent\":200,\"id\":2,\"nom\":\"user2\",\"prenom\":\"last2\"},{\"argent\":1205,\"id\":3,\"nom\":\"user3\",\"prenom\":\"last3\"}]}}";
        JSONObject jsonData = new JSONObject(data);
        Map<String, Object> mapData = jsonData.toMap();
        TypeReference<HashMap<String,Object>> typeRef
            = new TypeReference<HashMap<String,Object>>() {};
        Map<String, Object> value = objectMapper.readValue(data, typeRef);
        System.out.println("Map data is : " + mapData);
        System.out.println("the other data is " + value);

        value.keySet().forEach(System.out:: println);
        System.out.println("Printing entry set");
        value.entrySet().forEach(System.out:: println);
    }
}
