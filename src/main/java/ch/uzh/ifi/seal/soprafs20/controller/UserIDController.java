package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserGetDTO;
import ch.uzh.ifi.seal.soprafs20.rest.dto.UserPostDTO;
import ch.uzh.ifi.seal.soprafs20.rest.mapper.DTOMapper;
import ch.uzh.ifi.seal.soprafs20.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * UserID controller
 */
@RestController
public class UserIDController {

    private final UserService userService;

    UserIDController(UserService userService ){this.userService = userService;}

    /**
     * This method returns the whole user object when called by its ID.
     * @param
     * @return
     */
    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public /*UserGetDTO*/ String gotUser(){
        //*User*/String retUser = userService.userByID(id);
        //UserGetDTO retDTOUser = DTOMapper.INSTANCE.convertEntityToUserGetDTO(retUser);
        //return retDTOUser;
        return "comeon";
    }
}
