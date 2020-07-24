package web.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import web.service.Service;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private Service service;

    @Override
    public void run(String... strings) throws Exception {
        service.getUsers();
        service.postUser();
        service.putUser();
        service.deleteUser();
    }
}
