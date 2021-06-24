package az.code.carlada.controllers;

import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.services.interfaces.ListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    ListingService listingService;

    public UserController(ListingService listingService) {
        this.listingService = listingService;
    }

    @GetMapping("/{slug}/listings")
    public ResponseEntity<List<ListingListDTO>> getAllByAppUserUsername(@PathVariable String slug, Integer page, Integer count){
        return new ResponseEntity(listingService.getAllListingBySlug(slug, page, count), HttpStatus.OK);
    }
}
