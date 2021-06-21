package az.code.carlada.controllers;

import az.code.carlada.dtos.ListingCreationDTO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.services.ListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/profile")
public class ProfileController {

    ListingService listingService;

    public ProfileController(ListingService listingService) {
        this.listingService = listingService;
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
}