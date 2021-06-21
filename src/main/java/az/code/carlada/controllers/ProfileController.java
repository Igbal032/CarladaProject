package az.code.carlada.controllers;

import az.code.carlada.dtos.ListingCreationDTO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.services.ListingService;
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

    public ProfileController(ListingService listingService, SubscriptionService subService) {
        this.listingService = listingService;
        this.subService = subService;
    }

    @GetMapping("/listings")
    public ResponseEntity<List<ListingListDTO>> getAllListingByProfile() {
        return new ResponseEntity(listingService.getAllListingByProfile(), HttpStatus.OK);
    }

    @PostMapping("/listings")
    public ResponseEntity<List<ListingGetDTO>> createNewListing(@RequestBody ListingCreationDTO listingCreationDTO) {
        return new ResponseEntity(listingService.saveListing(listingCreationDTO), HttpStatus.OK);
    }

    @PutMapping("/listings/{id}")
    public ResponseEntity<List<ListingGetDTO>> createNewListing(@PathVariable long id, @RequestBody ListingCreationDTO listingCreationDTO) {
        //return null;
        return new ResponseEntity(listingService.saveListing(listingCreationDTO.toBuilder().id(id).build()), HttpStatus.OK);
    }

    @DeleteMapping("/listings/{id}")
    public ResponseEntity deleteListing(@PathVariable long id) {
        listingService.delete(id);
        return new ResponseEntity( HttpStatus.OK);
    }

    @GetMapping("/listings/{id}")
    public ResponseEntity<ListingGetDTO> getListingByIdByProfile(@PathVariable Long id) {
        return new ResponseEntity(listingService.getListingByIdByProfile(id), HttpStatus.OK);
    }

    @PutMapping("/listings/{id}/makevip")
    public ResponseEntity<String> makeListingVip(@PathVariable Long id){
        return new ResponseEntity(listingService.makeListVip(id), HttpStatus.OK);
    }

    @PutMapping("/listings/{id}/makepaid")
    public ResponseEntity<String> makeListingPaid(@PathVariable Long id){

        return new ResponseEntity(listingService.makeListPaid(id), HttpStatus.OK);
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
