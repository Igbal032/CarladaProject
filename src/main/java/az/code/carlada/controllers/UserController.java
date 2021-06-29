package az.code.carlada.controllers;

import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.services.interfaces.ListingService;

import az.code.carlada.exceptions.ListingNotFound;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    ListingService listingService;

    public UserController(ListingService listingService) {
        this.listingService = listingService;
    }

    @ExceptionHandler(ListingNotFound.class)
    public ResponseEntity<String> handlerNotFoundException(ListingNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{slug}/listings")
    public ResponseEntity<List<ListingListDTO>> getAllByAppUserUsername(@PathVariable String slug, Integer page, Integer count){
        return new ResponseEntity(listingService.getAllListingBySlug(slug, page, count), HttpStatus.OK);
    }
}
