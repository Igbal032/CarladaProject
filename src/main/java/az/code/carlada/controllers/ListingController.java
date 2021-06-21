package az.code.carlada.controllers;

import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.models.Listing;
import az.code.carlada.services.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/listings")
public class ListingController {

    ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping
    public ResponseEntity<List<ListingListDTO>> getAllListing(){
        return new ResponseEntity(listingService.getAllListing(), HttpStatus.OK);
    }


    @GetMapping("/vip")
    public ResponseEntity<List<ListingListDTO>> getAllVipListing(){
        return new ResponseEntity(listingService.getAllVipListing(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingListDTO> getListingByIdNoLogin(@PathVariable Long id){
        return new ResponseEntity(listingService.getListingById(id), HttpStatus.OK);
    }


    @GetMapping(path = "/dictionaries/makes")
    public ResponseEntity getMakes(){
        return new ResponseEntity(OK);
    }

}
