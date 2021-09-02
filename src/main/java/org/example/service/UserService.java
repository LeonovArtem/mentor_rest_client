package org.example.service;

import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserService {
    @Autowired
    RestTemplate restTemplate;

    private final String URL = "http://91.241.64.178:7081/api/users";

    private String cookie;
    private String filteredCookie;
    private HttpHeaders httpHeaders;

    private String result = "";

    public List<User> getUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                }
        );

        saveCookie(responseEntity);

        List<User> users = responseEntity.getBody();

        return users;
    }

    public User getUser(int id) {
        User user = restTemplate.getForObject(
                URL + "/" + id,
                User.class
        );

        return user;
    }

    public String getResult() {
        return result;
    }

    public void saveUser(User user) {
        HttpEntity requestEntity = new HttpEntity(user, httpHeaders);


        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<String>() {
                }
        );

        result += responseEntity.getBody();

        System.out.println(responseEntity.getBody());
    }

    public void updateUser(User user) {
        HttpEntity requestEntity = new HttpEntity(user, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<String>() {
                }
        );

        result += responseEntity.getBody();

        System.out.println(responseEntity.getBody());
    }

    public void deleteUser(Long id) {
        HttpEntity requestEntity = new HttpEntity(null, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL + "/" + id,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<String>() {
                }
        );

        result += responseEntity.getBody();

        System.out.println(responseEntity.getBody());
    }

    private void saveCookie(ResponseEntity<?> responseEntity) {
        cookie = responseEntity.getHeaders().get("Set-Cookie").get(0);
        filteredCookie = cookie.substring(0, cookie.indexOf(';'));

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", filteredCookie);

        httpHeaders = requestHeaders;
    }
}
