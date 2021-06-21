package az.code.carlada.controllers;

import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.enums.Status;
import az.code.carlada.models.Listing;
import az.code.carlada.services.PaymentService;
import az.code.carlada.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile/payment")
public class PaymentController {

    UserService userService;
    PaymentService paymentService;

    public PaymentController(UserService userService,PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @PostMapping("/payStatus")
    public ResponseEntity<?> payForStatus(@RequestParam String statusType, @RequestParam Long listingId){
        String username = "igbal-hasanli";//check
        Listing listing = paymentService.payForListingStatus(listingId,statusType,username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
