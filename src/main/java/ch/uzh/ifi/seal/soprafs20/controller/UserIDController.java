package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserGetDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserPostDTO;
import ch.uzh.ifi.seal.soprafs20.rest.mapper.DTOMapper;
import ch.uzh.ifi.seal.soprafs20.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * UserID controller
 */
@RestController
public class UserIDController {

    //private final UserService userService;

    //UserIDController(UserService userService ){this.userService = userService;}

    @GetMapping("/userid:id")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserGetDTO gotUser(@RequestBody long id){
        return null;
    }
}
