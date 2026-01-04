package main.controller;

import application.WestCastApplication;
import application.model.Movie;
import application.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = WestCastApplication.class)
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        String username = "enes" + System.currentTimeMillis();
        mockMvc.perform(post("/user/signUp")
                        .param("userName", username)
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(content().string("Signup successful"));

        MvcResult result = mockMvc.perform(post("/user/login")
                        .param("userName", username)
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("token is: ([A-Za-z0-9\\-_\\.]+)")
                .matcher(responseBody);
        if (matcher.find()) {
            token = matcher.group(1).trim();
        } else {
            throw new RuntimeException("Token parse edilemedi: " + responseBody);
        }
    }

    @Test
    void testSearchMovie() throws Exception {
        Movie movie = new Movie();
        movie.setName("Interstellar");
        movie.setTime(169);
        movieService.getMovieList().add(movie);

        mockMvc.perform(get("/user/search")
                        .header("Authorization", "Bearer " + token)
                        .param("movieName", "Interstellar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Interstellar")));
    }

    @Test
    void testRateMovie() throws Exception {
        Movie movie = new Movie();
        movie.setName("Interstellar");
        movie.setTime(169);
        movieService.getMovieList().add(movie);

        mockMvc.perform(post("/user/rate")
                        .header("Authorization", "Bearer " + token)
                        .param("movieName", "Interstellar")
                        .param("score", "5"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("You gave 5 to Interstellar named movie")));
    }

    @Test
    void testSignUpWithNewUser_shouldSucceed() throws Exception {
        String username = "newUser_" + System.currentTimeMillis();

        mockMvc.perform(post("/user/signUp")
                        .param("userName", username)
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(content().string("Signup successful"));
    }


    @Test
    void testSignUpWithExistingUser_shouldFail() throws Exception {
        String username = "duplicateUser";

        mockMvc.perform(post("/user/signUp")
                        .param("userName", username)
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(content().string("Signup successful"));

        mockMvc.perform(post("/user/signUp")
                        .param("userName", username)
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(content().string("User already exists"));  // Burayı kontrol ediyoruz
    }

    @Test
    void testLogin_shouldSucceed() throws Exception {
        String username = "loginUser_" + System.currentTimeMillis();
        String password = "1234";

        mockMvc.perform(post("/user/signUp")
                        .param("userName", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string("Signup successful"));

        mockMvc.perform(post("/user/login")
                        .param("userName", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString("login is successful! your token is:")
                ));
    }


    @Test
    void testSignUpLoginAndSearchFlow() throws Exception {
        String username = "flowUser" + System.currentTimeMillis();
        String password = "1234";

        mockMvc.perform(post("/user/signUp")
                        .param("userName", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(content().string("Signup successful"));

        MvcResult loginResult = mockMvc.perform(post("/user/login")
                        .param("userName", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andReturn();

        String loginResponse = loginResult.getResponse().getContentAsString();
        java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("token is: ([A-Za-z0-9\\-_\\.]+)")
                .matcher(loginResponse);

        assertTrue(matcher.find(), "Token bulunamadı!");
        String token = matcher.group(1).trim();

        mockMvc.perform(get("/user/search")
                        .header("Authorization", "Bearer " + token)
                        .param("movieName", "Interstellar"))
                .andExpect(status().isOk());
    }


}
