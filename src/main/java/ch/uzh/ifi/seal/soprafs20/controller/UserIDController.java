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

    private final UserService userService;

    UserIDController(UserService userService ){this.userService = userService;}

    /**
     * This method returns the whole user object when called by its ID.
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public /*UserGetDTO*/ User gotUser(/*@RequestBody UserPostDTO user*/@PathVariable long id){
        User retUser = userService.userByID(id);
        UserGetDTO retDTOUser = DTOMapper.INSTANCE.convertEntityToUserGetDTO(retUser);
        //return retDTOUser;
        return retUser;
    }

    /**
     * This is the method that calls userService to change the user's birthday.
     */
    @PutMapping("users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String setBirthday(@RequestBody String birthdate, @PathVariable long id){
        String returnBirthday = userService.updateBirthday(birthdate, id);
        return returnBirthday;
    }

}
