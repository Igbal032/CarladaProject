package az.code.carlada.controllers;

import az.code.carlada.dtos.UserDTO;
import az.code.carlada.services.interfaces.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping(value = "/users")
@RestController
public class AccountController {

    AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    //CREATE NEW USER WITHOUT ROLE
    @PostMapping(path = "/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(accountService.createUser(userDTO));
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(accountService.signin(userDTO));
    }
}
