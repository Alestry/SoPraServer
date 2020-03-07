package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserPostDTO;
import ch.uzh.ifi.seal.soprafs20.rest.mapper.DTOMapper;
import ch.uzh.ifi.seal.soprafs20.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void authenticatingRightUserReturnsCorrectUser() throws Exception {
        //testUser = The user we want to get back when successfully authenticated
        User testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test Name");
        testUser.setUsername("Test Username");
        testUser.setToken("Test Token");
        testUser.setStatus(UserStatus.ONLINE);

        //UserPostDTO = User that we give the server to authenticate
        UserPostDTO testUserPostDTO = new UserPostDTO();
        testUserPostDTO.setName("Test Name");
        testUserPostDTO.setUsername("Test Username");

        //given
        given(userService.authenticateUser(Mockito.any())).willReturn(testUser);
        //when
        MockHttpServletRequestBuilder putRequest = put("/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(testUserPostDTO));
        //then
        mockMvc.perform(putRequest).andExpect(status().isOk());
        //returnUser = The user we actually get back from the server upon authenticating
        User returnUser = userService.authenticateUser(DTOMapper.INSTANCE.convertUserPostDTOtoEntity(testUserPostDTO));

        //Now we compare returnUser to testUser

        assertEquals(returnUser.getName(), testUser.getName());
        assertEquals(returnUser.getUsername(), testUser.getUsername());
        assertEquals(returnUser.getId(), testUser.getId());
        assertNotEquals(0, returnUser.getToken().length());
    }

    @Test
    public void incorrectCredentialsReturnsError() throws Exception{
        //testUser = The actual credentials we want
        User testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test Name");
        testUser.setUsername("Test Username");
        testUser.setToken("Test Token");
        testUser.setStatus(UserStatus.ONLINE);

        //UserPostDTO = The wrong credentials as input
        UserPostDTO testUserPostDTO = new UserPostDTO();
        testUserPostDTO.setName("Wrong Name");
        testUserPostDTO.setUsername("Wrong Username");

        //given
        given(userService.authenticateUser(Mockito.any())).willThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        //when
        MockHttpServletRequestBuilder putRequest = put("/login").contentType(MediaType.APPLICATION_JSON).content(asJsonString(testUserPostDTO));
        //then
        mockMvc.perform(putRequest).andExpect(status().isUnauthorized());

        assertNotEquals(testUser.getName(), testUserPostDTO.getName());
        assertNotEquals(testUser.getUsername(), testUserPostDTO.getUsername());

    }



    /**
     * Helper Method to convert userPostDTO into a JSON string such that the input can be processed
     * Input will look like this: {"name": "Test User", "username": "testUsername"}
     * @param object
     * @return string
     */
    private String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new SopraServiceException(String.format("The request body could not be created.%s", e.toString()));
        }
    }
}

