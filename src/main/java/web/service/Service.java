package web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import web.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Service
public class Service {

    private final RestTemplate myRestTemplate;

    @Value("${myrest.url}")
    private String restUrl;

    private static String cookie;

    public Service(RestTemplate myRestTemplate) {
        this.myRestTemplate = myRestTemplate;
    }

    public void getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User[]> entity = new HttpEntity<User[]>(headers);

        ResponseEntity<User[]> response = myRestTemplate.exchange(restUrl, HttpMethod.GET, entity, User[].class);

        HttpHeaders httpHeaders = response.getHeaders();
        cookie = httpHeaders.getFirst(HttpHeaders.SET_COOKIE);

        User[] users = response.getBody();

        for (User u : users) {
            System.out.println(u.getId() + " " + u.getName() + " " + u.getLastName() + " " + u.getAge());
        }
    }

    public void postUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = new HashMap<>();
        map.put("id", 3);
        map.put("name", "James");
        map.put("lastName", "Brown");
        map.put("age", "11");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> result = myRestTemplate.postForEntity(restUrl,
                entity, String.class);
        System.out.println("Status code:" + result.getStatusCode());
        System.out.println(result.getBody());
    }

    public void putUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = new HashMap<>();
        map.put("id", "3");
        map.put("name", "Thomas");
        map.put("lastName", "Shelby");
        map.put("age", "11");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = myRestTemplate.exchange(restUrl,
                HttpMethod.PUT, entity, String.class, 3);
        System.out.println(response.getBody());
    }

    public void deleteUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = myRestTemplate.exchange(restUrl + "/{id}",
                HttpMethod.DELETE, entity, String.class, 3);
        System.out.println(response.getBody());
    }
}
