package ch.uzh.ifi.seal.soprafs20.service;

import ch.uzh.ifi.seal.soprafs20.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs20.entity.User;
import ch.uzh.ifi.seal.soprafs20.exceptions.SopraServiceException;
import ch.uzh.ifi.seal.soprafs20.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * User Service
 * This class is the "worker" and responsible for all functionality related to the user
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User newUser) {
        newUser.setToken(UUID.randomUUID().toString());
        newUser.setStatus(UserStatus.ONLINE);

        checkIfUserExists(newUser);

        // saves the given entity but data is only persisted in the database once flush() is called
        newUser = userRepository.save(newUser);
        userRepository.flush();

        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    /**
     * This is a helper method that will check the uniqueness criteria of the username and the name
     * defined in the User entity. The method will do nothing if the input is unique and throw an error otherwise.
     *
     * @param userToBeCreated
     * @throws SopraServiceException
     * @see ch.uzh.ifi.seal.soprafs20.entity.User
     */
    private void checkIfUserExists(User userToBeCreated) {
        User userByUsername = userRepository.findByUsername(userToBeCreated.getUsername());
        User userByName = userRepository.findByName(userToBeCreated.getName());

        String baseErrorMessage = "The %s provided %s not unique. Therefore, the user could not be created!";
        if (userByUsername != null && userByName != null) {
            throw new SopraServiceException(String.format(baseErrorMessage, "username and the name", "are"));
        }
        else if (userByUsername != null) {
            throw new SopraServiceException(String.format(baseErrorMessage, "username", "is"));
        }
        else if (userByName != null) {
            throw new SopraServiceException(String.format(baseErrorMessage, "name", "is"));
        }
    }

    /**
     * This method is the login authenticator. It checks if the login information combination provided by the input
     * is in the database or not. It will return the corresponding user object if yes, throws an exception if not.
     *
     * @param providedUser
     */
    public User authenticateUser(User providedUser){
        User userByUsername = userRepository.findByUsername(providedUser.getUsername());
        User userByName = userRepository.findByName(providedUser.getName());

        if(userByUsername == userByName && userByName != null){
            return userByName;
        }else{
            throw new SopraServiceException("This Username and Name combination doesn't exist. Try registering an account.");
        }
    }

}
