package az.code.carlada.controllers;

import az.code.carlada.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/wallet/increase")
    public ResponseEntity<Double> wallet(@RequestParam Double amount){
        String email = "igbal-hasanli";//check
        Double userAmount = userService.addAmount(email,amount);
        return new ResponseEntity<>(userAmount,HttpStatus.OK);
    }

}
