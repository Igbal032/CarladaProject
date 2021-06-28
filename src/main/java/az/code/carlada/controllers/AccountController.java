package az.code.carlada.controllers;

import az.code.carlada.dtos.UserDTO;
import az.code.carlada.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/account")
@RestController
public class AccountController {

    UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    //CREATE NEW USER WITHOUT ROLE
    @PostMapping(path = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @GetMapping(path = "/user/verify")
    public ResponseEntity<?> verifyUser(@RequestAttribute("user") UserDTO user, @RequestParam String token) {
        return ResponseEntity.ok(userService.verifyUser(user, token));
    }

    @GetMapping(path = "/user/send-verify-email")
    public ResponseEntity<?> sendVerifyEmail(@RequestAttribute("user") UserDTO user) {
        userService.sendVerifyEmail(user.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.signin(userDTO));
    }
}
