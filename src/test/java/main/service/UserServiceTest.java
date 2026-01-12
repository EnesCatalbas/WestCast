package main.service;

import application.model.Movie;
import application.model.User;
import application.repository.UserRepository;
import application.service.MovieService;
import application.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private MovieService movieService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        movieService = new MovieService();
        userService = new UserService(userRepository);

        try {
            var field = UserService.class.getDeclaredField("movieService");
            field.setAccessible(true);
            field.set(userService, movieService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testFindUserByUserName() {
        User mockUser = new User("userTest", "userPassword");
        when(userRepository.findByUserName("userTest")).thenReturn(mockUser);

        User result = userService.findByUserName("userTest");

        assertNotNull(result);
        assertEquals("userTest", result.getUserName());
        verify(userRepository, times(1)).findByUserName("userTest");
    }

    @Test
    void testFindUserByUserName_notFound() {
        when(userRepository.findByUserName("ghost")).thenReturn(null);

        User result = userService.findByUserName("ghost");

        assertNull(result);
        verify(userRepository, times(1)).findByUserName("ghost");
    }

    @Test
    void testSignUp_callsRepositorySave() {
        when(userRepository.findByUserName("newUser")).thenReturn(null);

        boolean result = userService.signup("newUser", "1234");

        assertTrue(result);
        verify(userRepository, times(1))
                .save(argThat(user ->
                        user.getUserName().equals("newUser") &&
                                user.getPassword().equals("1234")));
    }

    @Test
    void testSignUp_existingUser_returnsFalse() {
        when(userRepository.findByUserName("existingUser"))
                .thenReturn(new User("existingUser", "1234"));

        boolean result = userService.signup("existingUser", "1234");

        assertFalse(result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void testSearchMovie_returnsMovie() {
        Movie movie = new Movie();
        movie.setName("Interstellar");
        movie.setTime(169);

        movieService.getMovieList().add(movie);

        Movie result = userService.search("Interstellar");

        assertNotNull(result);
        assertEquals("Interstellar", result.getName());
    }

    @Test
    void testSearchMovie_notFound() {
        Movie movie = new Movie();
        movie.setName("Inception");
        movieService.getMovieList().add(movie);

        Movie result = userService.search("Matrix");

        assertNull(result);
    }

    @Test
    void testRateMovie_setsRating() {
        Movie movie = new Movie();
        movie.setName("Interstellar");
        movieService.getMovieList().add(movie);

        int score = userService.rate("Interstellar", 9);

        assertEquals(9, score);
        assertEquals(9, movie.getRating());
    }

    @Test
    void testAddToWatchList_Success() {
        // 1. HAZIRLIK (Gereksinimler: Mevcut bir kullanıcı ve mevcut bir film)
        String username = "testUser";
        String movieName = "Inception";
        User mockUser = new User(username, "pass");

        Movie movie = new Movie();
        movie.setName(movieName);
        movieService.getMovieList().add(movie);

        // UserRepository'nin bu kullanıcıyı bulacağını simüle et
        when(userRepository.findByUserName(username)).thenReturn(mockUser);

        // 2. AKSİYON
        userService.addToWatchList(username, movieName);

        // 3. DOĞRULAMA
        assertTrue(mockUser.getWatchList().contains(movieName), "Film listeye eklenmiş olmalı.");
        verify(userRepository, times(1)).save(mockUser); // DB'ye kaydedildi mi?
    }

    @Test
    void testAddToWatchList_MovieNotFound_ShouldThrowException() {
        // HAZIRLIK: Kullanıcı var ama film sistemde yok
        String username = "testUser";
        User mockUser = new User(username, "pass");
        when(userRepository.findByUserName(username)).thenReturn(mockUser);

        // AKSİYON & DOĞRULAMA: Film listede olmadığı için hata fırlatmalı
        assertThrows(RuntimeException.class, () -> {
            userService.addToWatchList(username, "Geçersiz Film");
        });
    }

    @Test
    void testGetWatchList_ReturnsCorrectSet() {
        // HAZIRLIK: İçinde zaten filmler olan bir kullanıcı simülasyonu
        String username = "testUser";
        User mockUser = new User(username, "pass");
        mockUser.getWatchList().add("Matrix");
        mockUser.getWatchList().add("Interstellar");

        when(userRepository.findByUserName(username)).thenReturn(mockUser);

        // AKSİYON
        java.util.Set<String> result = userService.getWatchList(username);

        // DOĞRULAMA
        assertEquals(2, result.size());
        assertTrue(result.contains("Matrix"));
        assertTrue(result.contains("Interstellar"));
    }
}
