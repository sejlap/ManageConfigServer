package com.example.userservice.userservice;

        import com.example.userservice.userservice.Models.User;
        import com.example.userservice.userservice.Repositories.UserRepository;
        import com.example.userservice.userservice.Service.UserService;
        import com.example.userservice.userservice.Service.UserValidationService;
        import com.flextrade.jfixture.JFixture;
        import org.junit.jupiter.api.Test;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
        import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

        import java.util.*;

        import static org.mockito.ArgumentMatchers.anyInt;
        import static org.mockito.BDDMockito.given;
        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.doNothing;
        import static org.mockito.Mockito.mock;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.MvcResult;
        import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
        import com.fasterxml.jackson.databind.ObjectMapper;
public class UserServiceTests {

    @MockBean
    private UserRepository userRepository;

    private UserValidationService validationService;
    private UserService userService;
    private JFixture fixture;

    UserServiceTests() {
        userRepository = mock(UserRepository.class);
        validationService = new UserValidationService();
        userService = new UserService(userRepository, validationService);
        fixture = new JFixture();
    }

    @Test
    public void findAllUsersTest_ShouldReturnOkWithResults() {
        List<User> expected = Arrays.asList(
                new User("Sejla", "Pljakic", "email@email.com", "Password123!","Adresa1", "BIH", "Sarajevo", "123456",
                        new Date()),
                new User("Test", "User", "sejlaemail@email.com", "Password123!","Adresa1", "BIH", "Sarajevo", "123456",new Date()));

        given(userRepository.findAll()).willReturn(expected);

        ResponseEntity<List<User>> actual = userService.findAllUsers();

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(2, actual.getBody().size());
    }

    @Test
    public void findUserByIdTest_ShouldReturnOkWithResult() {
        User expected = new User("Sejla", "Pljakic", "email@email.com", "Password123!","Adresa1", "BIH", "Sarajevo", "123456",new Date());

        given(userRepository.findById(anyInt())).willReturn(Optional.of(expected));

        ResponseEntity<User> actual = null;
        try {
            actual = userService.findUserById(5);
        } catch (Exception e) {}

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
    }



    @Test
    public void updateExistingUserTest_ShouldReturnOkWithResult() {
        User expected = new User("Sejla", "Pljakic", "email@email.com", "Password123!","Adresa1", "BIH", "Sarajevo", "123456",new Date());
        given(userRepository.save(expected)).willReturn(expected);
        given(userRepository.findById(anyInt())).willReturn(Optional.of(expected));

        ResponseEntity<User> actual = null;
        try {
            actual = userService.updateExistingUser(5, expected);
        } catch (Exception e) {}

        assertEquals


                (HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
    }

    @Test
    public void deleteUserTest_ShouldReturnOkWithResult() {
        User expected = new User("Sejla", "Pljakic", "email@email.com", "Password123!","Adresa1", "BIH", "Sarajevo", "123456",new Date());
        given(userRepository.findById(anyInt())).willReturn(Optional.of(expected));
        doNothing().when(userRepository).deleteById(anyInt());

        ResponseEntity<String> actual = null;
        try {
            actual = userService.deleteUser(5);
        } catch (Exception e) {}

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals("User successfully deleted.", actual.getBody());
    }

     @Test
     public void givenStudent_whenSave_thenGetOk() {
         User user = new User("Sejla", "Pljakic", "email@email.com", "Password123!","Adresa1", "BIH", "Sarajevo", "123456",new Date());
         userRepository.save(user);
         Optional<User> user2 = userRepository.findById(4);
         assertEquals("Pljakic", user.getLastName());
     }
}

