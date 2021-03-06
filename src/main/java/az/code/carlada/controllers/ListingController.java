package az.code.carlada.controllers;

import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.exceptions.ImageNotFoundException;
import az.code.carlada.exceptions.ListingNotFound;
import az.code.carlada.models.Image;
import az.code.carlada.services.interfaces.ImageService;
import az.code.carlada.services.interfaces.ListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import az.code.carlada.services.interfaces.SearchService;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/listings")
public class ListingController {
    ListingService listingService;
    SearchService searchService;
    ImageService imageService;

    public ListingController(ListingService listingService, SearchService searchService, ImageService imageService) {
        this.listingService = listingService;
        this.searchService = searchService;
        this.imageService = imageService;
    }

    @ExceptionHandler(ListingNotFound.class)
    public ResponseEntity<String> handlerNotFoundException(ListingNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<String> handlerNotFoundException(ImageNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<ListingListDTO>> getListingsByActiveStatus(@RequestParam Integer page, @RequestParam Integer count) {
        return new ResponseEntity(listingService.getListingsByActive(page, count, true), HttpStatus.OK);
    }

    @GetMapping("/vip")
    public ResponseEntity<List<ListingListDTO>> getAllVipListing(@RequestParam Integer page, @RequestParam Integer count) {
        return new ResponseEntity(listingService.getAllVipListing(page, count), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingListDTO> getListingByIdNoLogin(@PathVariable Long id) {
        return new ResponseEntity(listingService.getListingById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity search(@RequestParam(required = false) Map<String, String> allParams) {
        return new ResponseEntity(searchService.searchListings(allParams), OK);
    }

    @GetMapping(path = "/{id}/images")
    public ResponseEntity<Image> getAllImgByListingId(@PathVariable Long id) {
        return new ResponseEntity(imageService.getAllImgFromListing(id), OK);
    }

    @GetMapping(path = "/{id}/images/{id1}")
    public ResponseEntity<Image> getImgByListingId(@PathVariable Long id, @PathVariable Long id1) {
        return new ResponseEntity(imageService.getImgFromListing(id, id1), OK);
    }
}