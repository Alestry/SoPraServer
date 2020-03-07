package ch.uzh.ifi.seal.soprafs20.controller;

import ch.uzh.ifi.seal.soprafs20.entity.User;
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
     * @param userId ID of the profile's user
     * @return returned User
     */
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User gotUser(@PathVariable long userId){
        User retUser = userService.userByID(userId);
        return retUser;
    }

    /**
     * This is the method that calls userService to change the user's birthday.
     * @param newdata Aggregate data of birthday and username (the same as before if hasn't changed)
     * @param userId ID of the user we want the birthday set
     */
    @PutMapping("users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void setBirthday(@RequestBody String newdata, @PathVariable long userId){

        String[] temparr = newdata.split("!!!");
        String birthday = temparr[0];
        String username = temparr[1];

        userService.updateBirthday(birthday, userId);
        userService.updateUsername(username, userId);
    }

}
