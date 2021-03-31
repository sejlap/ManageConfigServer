package com.example.userservice.userservice;
import com.example.userservice.userservice.Controllers.UserController;
import com.example.userservice.userservice.ErrorHandling.GlobalException;
import com.example.userservice.userservice.ErrorHandling.InvalidRequestException;
import com.example.userservice.userservice.ErrorHandling.UserNotFoundException;
import com.example.userservice.userservice.Models.User;
import com.example.userservice.userservice.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Date;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllersTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(UserController.class)
                .setControllerAdvice(new GlobalException())
                .build();
    }

    @Test
    public void findUserById_ShouldReturnOkWithResult() throws Exception {
        User expected = new User("Sejla", "Pljakic", "email@email.com", "Password123!","Adresa1", "BIH", "Sarajevo", "123456",new Date());
        expected.setUserId(1);

        given(userService.findUserById(anyInt())).willReturn(new ResponseEntity<>(expected, HttpStatus.OK));

        mvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", is(expected.getUserId())))
                .andExpect(jsonPath("$.firstName", is("Sejla")));
    }


    @Test
    public void findUserById_ShouldReturn400_WhenIdIsInvalid() throws Exception {
        given(userService.findUserById(anyInt()))
                .willThrow(new InvalidRequestException("Received Id is not valid."));

        mvc.perform(get("/user/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findUserById_ShouldReturn404_WhenUserIsNotFound() throws Exception {
        given(userService.findUserById(anyInt()))
                .willThrow(new UserNotFoundException("User with received ID was not found."));

        mvc.perform(get("/user/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addNewUser_ShouldReturnOkWithResult() throws Exception {
        User expected = new User("Sejla", "Pljakic", "noviemail@email.com", "Password123!","Adresa1", "BIH", "Sarajevo", "123456",new Date());
        expected.setUserId(1);

        given(userService.saveUser(expected)).willReturn(new ResponseEntity<>(expected, HttpStatus.OK));

        mvc.perform(post("/user/add")
                .content(new ObjectMapper().writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addNewUser_ShouldReturn400_WhenRequestIsInvalid() throws Exception {
        User user = new User("", "", "", "Password123!","Adresa1", "BIH", "Sarajevo", "123456",new Date());

        given(userService.saveUser(ArgumentMatchers.<User>any()))
                .willThrow(new InvalidRequestException("Properties that must be provided: First Name, Last Name, Email."));

        mvc.perform(post("/user/add")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateUser_ShouldReturnOkWithResult() throws Exception {
        User expected =  new User("Sejla", "Pljakic", "noviemail@email.com", "Password123!","Adresa1", "BIH", "Sarajevo", "123456",new Date());
        expected.setUserId(1);
        given(userService.updateExistingUser(1, expected)).willReturn(new ResponseEntity<>(expected, HttpStatus.OK));
        mvc.perform(put("/user/update/1")
                .content(new ObjectMapper().writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void updateUser_ShouldReturn400_WhenIdIsInvalid() throws Exception {
        given(userService.updateExistingUser(anyInt(), ArgumentMatchers.<User>any()))
                .willThrow(new InvalidRequestException(""));
        mvc.perform(put("/user/update/0")
                .content(new ObjectMapper().writeValueAsString(new User()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void updateUser_ShouldReturn404_WhenUserIsNotFound() throws Exception {
        given(userService.updateExistingUser(anyInt(), ArgumentMatchers.<User>any()))
                .willThrow(new UserNotFoundException(""));

        mvc.perform(put("/user/update/45")
                .content(new ObjectMapper().writeValueAsString(new User()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteUser_ShouldReturnOkWithResult() throws Exception {
        given(userService.deleteUser(anyInt())).willReturn(
                new ResponseEntity<>("User successfully deleted", HttpStatus.OK));

        mvc.perform(delete("/user/delete/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser_ShouldReturn400_WhenIdIsInvalid() throws Exception {
        given(userService.deleteUser(anyInt()))
                .willThrow(new InvalidRequestException(""));

        mvc.perform(delete("/user/delete/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUser_ShouldReturn404_WhenUserIsNotFound() throws Exception {
        given(userService.deleteUser(anyInt()))
                .willThrow(new UserNotFoundException(""));

        mvc.perform(delete("/user/delete/45")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



}
