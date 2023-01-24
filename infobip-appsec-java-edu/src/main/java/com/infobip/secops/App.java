package com.infobip.secops;

import com.infobip.secops.model.Person;
import com.infobip.secops.model.ShopItem;
import com.infobip.secops.repository.PersonRepository;
import com.infobip.secops.repository.ShopItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class App {

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @Bean(name = "admin")
    public Person adminUser() {
        return new Person(1L, "admin", "Administrat0rPassw0rd1!2");
    }

    @EventListener
    public void onReady(ApplicationReadyEvent event) {
        ApplicationContext ac = event.getApplicationContext();

        Person admin = (Person) ac.getBean("admin");
        PersonRepository personRepository = event.getApplicationContext().getBean(PersonRepository.class);
        List<Person> users = Arrays.asList(
                admin,
                new Person(2L, "test", "HashDatPassW1thS4lt"),
                new Person(3L, "user", "NoMorePassw0rdId34s")
        );
        personRepository.saveAll(users);

        ShopItemRepository shopItemRepository = event.getApplicationContext().getBean(ShopItemRepository.class);
        List<ShopItem> items = Arrays.asList(
                new ShopItem(1L, 10, "Canned water"),
                new ShopItem(2L, 1, "Canned air"),
                new ShopItem(3L, 1337, "Silver bullet")
        );
        shopItemRepository.saveAll(items);
    }
}
