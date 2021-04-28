package com.mss.polyflow.shared.utilities.functionality;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mss.polyflow.shared.model.User;
import com.mss.polyflow.shared.repository.UserRepository;
import com.mss.polyflow.task_management.model.Category;
import com.mss.polyflow.task_management.model.Task;
import com.mss.polyflow.task_management.repository.category.CategoryRepository;
import com.mss.polyflow.task_management.repository.task.TaskRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SampleDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final PasswordEncoder delegatingPasswordEncoder;

    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;
    public SampleDataInitializer(UserRepository userRepository,
        CategoryRepository categoryRepository,
        PasswordEncoder delegatingPasswordEncoder,
        TaskRepository taskRepository,
        ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.delegatingPasswordEncoder = delegatingPasswordEncoder;
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        int len = args.length;
        log.info("-----------------------------------------------");
        log.info("printing the argument data of commandLineRunner");
        for(int i = 0; i < len; ++i) {
            log.info(args[i]);
        }
        log.info("end of printing of command line runner");
        log.info("------------------------------------------------");
        initializeUsers();

    }


    private void initializeUsers() {

        if(userRepository.findAll().size() > 0) {
            System.out.println("data already exists  -------------------------");
            return;
        }
        int n = 10;
        List<User> users = new ArrayList<>();
        for(int i = 1 ; i <= n; ++i) {
            users.add(User.builder()
                          .userId(null)
                          .username("user" + i)
                          .password(delegatingPasswordEncoder.encode("user" + i))
                          .email("user" + i + "@test.com")
                          .firstName("firstName" + i)
                          .lastName("lastName" + i)
                          .phoneNumber("123456789" + (i - 1))
                          .build());
        }
        users = userRepository.saveAll(users);
        initializeCategories(users.get(0).getUserId(), 165);
        initializeCategories(users.get(1).getUserId(), 34);
        userRepository.save(new User()
                                .setUserId(null)
                                .setUsername("user01")
                                .setPassword(delegatingPasswordEncoder.encode("user01"))
                                .setEmail("test@gmail.com")
                                .setFirstName("first")
                                .setLastName("lastname"));
    }

    private void initializeCategories(Long userId, int categorySize) {
        List<Category> categories = new ArrayList<>();

        for(int i = 1; i <= categorySize; ++i) {
            categories.add(new Category()
                               .setName("Category"  + "user - " + userId + " - " + (i))
                               .setDescription("Category Description " + "user - " + userId + " - " + (i))
                               .setUserId(userId));
        }

        categories = categoryRepository.saveAll(categories);
        for(int i = 0; i < 8; ++i) {
            initializeTasks(categories.get(i).getId(), userId, 300);
        }
    }

    private void initializeTasks(Long categoryId,Long userId, int taskCount) {
        List<Task> tasks = new ArrayList<>();

        for(int i = 1; i <= taskCount; ++i) {
            Task currentTasks = new Task()
                                    .setId(null)
                                    .setName("Task" + "user - " + userId + "  category - " + categoryId + " - "  + (i))
                                    .setDescription("Task Description"  + "user - " + userId + "  category - " + categoryId + " - "  + (i))
                                    .setCategoryId(categoryId);
            if(i < taskCount/2) {
                currentTasks.setTaskStatus(1);
            }
            tasks.add(currentTasks);
        }

        taskRepository.saveAll(tasks);
    }

    private void showData() throws JsonProcessingException {
        String data = "{\"users\":{\"address\":[{\"rue\":\"ruetest\",\"postal\":1111},{\"rue\":\"ruetest\",\"postal\":2222}],\"type\":\"string\",\"user\":[{\"argent\":122,\"id\":1,\"nom\":\"user1\",\"prenom\":\"last1\"},{\"argent\":200,\"id\":2,\"nom\":\"user2\",\"prenom\":\"last2\"},{\"argent\":1205,\"id\":3,\"nom\":\"user3\",\"prenom\":\"last3\"}]}}";
        JSONObject jsonData = new JSONObject(data);
        Map<String, Object> mapData = jsonData.toMap();
        TypeReference<HashMap<String,Object>> typeRef
            = new TypeReference<HashMap<String,Object>>() {};
        Map<String, Object> value = objectMapper.readValue(data, typeRef);
        log.info("Map data is : {}",  mapData);
        log.info("the other data is {}", value);

        value.keySet().forEach(System.out:: println);
        log.info("Printing entry set");
        value.entrySet().forEach(System.out:: println);
    }
}
