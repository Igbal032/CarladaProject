package az.code.carlada.controllers;

import az.code.carlada.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profile")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/wallet")
    public ResponseEntity<Double> wallet(@PathVariable Double amount){
        String email = "";//check
        Double userAmount = userService.addAmount(email,amount);
        return new ResponseEntity<>(userAmount,HttpStatus.OK);
    }

}
