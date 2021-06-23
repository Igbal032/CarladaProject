package az.code.carlada.controllers;
import az.code.carlada.dtos.ListingCreationDTO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.services.ListingService;
import az.code.carlada.dtos.TransactionListDTO;
import az.code.carlada.services.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import az.code.carlada.dtos.SubscriptionDTO;
import az.code.carlada.services.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/profile")
public class ProfileController {

    ListingService listingService;
    SubscriptionService subService;
    ProfileService profileService;
    Logger logger = LoggerFactory.getLogger(ProfileController.class);

    public ProfileController(ListingService listingService, SubscriptionService subService, ProfileService profileService) {
        this.listingService = listingService;
        this.subService = subService;
        this.profileService = profileService;
    }

    @GetMapping("/listings")
    public ResponseEntity<List<ListingListDTO>> getAllListingByProfile(@RequestParam Integer page, @RequestParam Integer count) {
        return new ResponseEntity(listingService.getAllListingByProfile(page, count), HttpStatus.OK);
    }

    @PostMapping("/listings")
    public ResponseEntity<List<ListingGetDTO>> createNewListing(@RequestBody ListingCreationDTO listingCreationDTO) {
        return new ResponseEntity(listingService.saveListing(listingCreationDTO), HttpStatus.OK);
    }

    @PutMapping("/listings/{id}")

    public ResponseEntity<List<ListingGetDTO>> createNewListing(@PathVariable Long id, @RequestBody ListingCreationDTO listingCreationDTO) {
        return new ResponseEntity(listingService.saveListing(listingCreationDTO.toBuilder().id(id).build()), HttpStatus.OK);
    }

    @DeleteMapping("/listings/{id}")
    public ResponseEntity deleteListing(@PathVariable long id) {
        listingService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/listings/{id}")
    public ResponseEntity<ListingGetDTO> getListingByIdByProfile(@PathVariable Long id) {
        return new ResponseEntity(listingService.getListingByIdByProfile(id), HttpStatus.OK);
    }
    @PutMapping("/listings/{listingId}/makevip")
    public ResponseEntity<TransactionListDTO> payForVipStatus(@PathVariable Long listingId){
        String username = "shafig";//check
        return new ResponseEntity<>(profileService.payForVipStatus(listingId,username),HttpStatus.OK);
    }
    @PutMapping("/listings/{listingId}/makepaid")
    public ResponseEntity<TransactionListDTO> payForStandardStatus(@PathVariable Long listingId){
        String username = "shafig";//check
        return new ResponseEntity<>(profileService.payForStandardStatus(listingId,username),HttpStatus.OK);
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
        subService.deleteSubscription(id);
        return new ResponseEntity(OK);
    }

    @PutMapping("/wallet/increase")
    public ResponseEntity<TransactionListDTO> wallet(@RequestParam Double amount){
        String username = "igbal-hasanli";//check
        return new ResponseEntity<>(profileService.addAmount(username,amount),HttpStatus.OK);
    }
}
