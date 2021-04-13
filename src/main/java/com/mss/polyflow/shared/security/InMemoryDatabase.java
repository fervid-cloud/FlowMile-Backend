package com.mss.polyflow.shared.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class InMemoryDatabase implements SmartInitializingSingleton {

    public  Map<String, List<String>> endPointPermissionMapping;

    public  Map<Object, Object> userPermissionMapping;

    private final ApplicationContext applicationContext;

    public InMemoryDatabase(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    {
        initializeUserData();
    }

    private void initializeUserData() {
        userPermissionMapping = new HashMap() {
            {
                put("user1", new ArrayList() {
                    {
                        add(new SimpleGrantedAuthority("READ"));
                    }
                });

                put("user2", new ArrayList() {
                    {
                        add(new SimpleGrantedAuthority("DELETE"));
                    }
                });

                put("user1", new ArrayList() {
                    {
                        add(new SimpleGrantedAuthority("READ"));
                        add(new SimpleGrantedAuthority("WRITE"));
                        add(new SimpleGrantedAuthority("DELETE"));
                    }
                });
            }
        };

    }

    private void showDatabaseDetails() {
        System.out.println("Showing the endPointPermissionMapping");
        System.out.println(endPointPermissionMapping);
        System.out.println("Showing the userPermissionMapping");
        System.out.println(userPermissionMapping);
    }

    @Override
    public void afterSingletonsInstantiated() {
        endPointPermissionMapping = new HashMap() {
            {
                put("/firstEndPoint", new ArrayList() {
                    {
                        add("READ");
                        add("WRITE");
                    }
                });

                put("/secondEndPoint", new ArrayList() {
                    {
                        add("DELETE");
                        add("READ");
                    }
                });
            };
        };

        showDatabaseDetails();
        printBeans();

    }

    private void printBeans() {

        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println(beanName + " : " + applicationContext.getBean(beanName).getClass().toString());
        }

    }

}
