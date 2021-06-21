package az.code.carlada.controllers;

import az.code.carlada.dtos.TransactionListDTO;
import az.code.carlada.services.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    ProfileService profileService;
    Logger logger = LoggerFactory.getLogger(ProfileController.class);
    public ProfileController(ProfileService userService) {
        this.profileService = userService;
    }
    @PutMapping("/wallet/increase")
    public ResponseEntity<TransactionListDTO> wallet(@RequestParam Double amount){
        String username = "igbal-hasanli";//check
        return new ResponseEntity<>(profileService.addAmount(username,amount),HttpStatus.OK);
    }
    @PostMapping("/listings/{listingId}/makevip")
    public ResponseEntity<TransactionListDTO> payForVipStatus(@PathVariable Long listingId){
        String username = "igbal-hasanli";//check
        return new ResponseEntity<>(profileService.payForVipStatus(listingId,username),HttpStatus.OK);
    }
    @PostMapping("/listings/{listingId}/makepaid")
    public ResponseEntity<TransactionListDTO> payForStandardStatus(@PathVariable Long listingId){
        String username = "igbal-hasanli";//check
        return new ResponseEntity<>(profileService.payForStandardStatus(listingId,username),HttpStatus.OK);
    }

}
