package az.code.carlada.controllers;

import az.code.carlada.dtos.SubscriptionDTO;
import az.code.carlada.services.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/profile")
public class ProfileController {
    SubscriptionService subService;

    public ProfileController(SubscriptionService subService) {
        this.subService = subService;
    }

    @GetMapping("/subscriptions")
    public ResponseEntity getSubscriptions() {
        return new ResponseEntity(subService.getSubscriptions(), OK);
    }

    @GetMapping("/subscriptions/{id}")
    public ResponseEntity getSubscriptionById(@PathVariable Long id) {
        return new ResponseEntity(subService.getSubscriptionById(id), OK);
    }

    @PostMapping("/subscriptions")
    public ResponseEntity createSubscription(@RequestBody SubscriptionDTO subDTO) {
        return new ResponseEntity(subService.saveSubscription(subDTO), OK);
    }

    @PutMapping("/subscriptions/{id}")
    public ResponseEntity updateSubscription(@PathVariable Long id, @RequestBody SubscriptionDTO subDTO) {
        subDTO.setSubId(id);
        return new ResponseEntity(subService.saveSubscription(subDTO), OK);
    }

    @DeleteMapping("/subscriptions/{id}")
    public ResponseEntity disableSubscription(@PathVariable Long id) {
        subService.disableSubscription(id);
        return new ResponseEntity(OK);
    }
}
