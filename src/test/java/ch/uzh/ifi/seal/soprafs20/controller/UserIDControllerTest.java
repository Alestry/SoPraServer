package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserGetDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserPostDTO;
import ch.uzh.ifi.seal.soprafs20.rest.mapper.DTOMapper;
import ch.uzh.ifi.seal.soprafs20.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserIDController.class)

public class UserIDControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void correctUserProfileIsRetrieved() throws Exception{
        //given
        //User that is expected to be returned
        User testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test Name");
        testUser.setUsername("Test Username");
        testUser.setToken("Test Token");
        testUser.setStatus(UserStatus.ONLINE);

        //User to be retrieved
        UserPostDTO testUserPostDTO = new UserPostDTO();
        testUserPostDTO.setName("Test Name");
        testUserPostDTO.setUsername("Test Username");
        //Set the ID
        User otherTestUser = DTOMapper.INSTANCE.convertUserPostDTOtoEntity(testUserPostDTO);
        otherTestUser.setId(1L);

        //Given
        given(userService.userByID(testUser.getId())).willReturn(testUser);
        //When
        MockHttpServletRequestBuilder getRequest = get("/users/"+otherTestUser.getId());
        //Then
        mockMvc.perform(getRequest).andExpect(status().isOk());

        //Assert that it is indeed the correct user
        assertEquals(otherTestUser.getId(), testUser.getId());
        assertEquals(otherTestUser.getName(), testUser.getName());
        assertEquals(otherTestUser.getUsername(), testUser.getUsername());
    }

    @Test
    public void nonExistingUserIDReturnsError() throws Exception{
        //We just send an arbitrary number (Here: 8) to the server to check for a user
        //Because it just started, it will be empty, so it will have to throw a 404 error

        //Given
        long testID = 8;
        given(userService.userByID(testID)).willReturn(null);
        //When
        MockHttpServletRequestBuilder getRequest = get("/users"+testID);
        //Then
        mockMvc.perform(getRequest).andExpect(status().isNotFound());
    }

    @Test
    public void changingUserInformationActuallyChangesIt() throws Exception{
        //given
        //Set up 2 users: one with original data, other one with updated data
        User testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test Name");
        testUser.setUsername("Test Username");
        testUser.setToken("Test Token");
        testUser.setStatus(UserStatus.ONLINE);
        testUser.setBirthdate("-");

        User testUser2 = new User();
        testUser2.setId(1L);
        testUser2.setName("Test Name");
        testUser2.setUsername("New Username");
        testUser2.setToken("Test Token");
        testUser2.setStatus(UserStatus.ONLINE);
        testUser2.setBirthdate("New Birthdate");

        //Given
        given(userService.userByID(Mockito.anyLong())).willReturn(testUser);
        //When
        MockHttpServletRequestBuilder putRequest = put("/users/1").contentType(MediaType.TEXT_PLAIN).content("New Birthdate!!!New Username");
        //Then
        mockMvc.perform(putRequest).andExpect(status().isNoContent());

        //Assert update
        assertNotEquals(testUser.getUsername(), testUser2.getUsername());
        assertNotEquals(testUser.getBirthdate(), testUser2.getBirthdate());
    }

    @Test
    public void userProfileNotFoundWhenUpdatingThrowsException() throws Exception{
        //Again, we just send an arbitrary number (Here: 8) to the server to check for a user
        //Because it just started, it will be empty, so it will have to throw a 404 error

        //Given
        long testID = 8;
        given(userService.userByID(testID)).willReturn(null);
        //When
        MockHttpServletRequestBuilder putRequest = put("/users"+testID);
        //Then
        mockMvc.perform(putRequest).andExpect(status().isNotFound());
    }

}
