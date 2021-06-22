package az.code.carlada.controllers;

import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.services.ListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import az.code.carlada.services.SearchService;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/listings")
public class ListingController {
    ListingService listingService;
    SearchService searchService;

    public ListingController(ListingService listingService, SearchService searchService) {
        this.listingService = listingService;
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<List<ListingListDTO>> getAllListing(@RequestParam Integer page, @RequestParam Integer count){
        return new ResponseEntity(listingService.getAllListing(page, count), HttpStatus.OK);
    }


    @GetMapping("/vip")
    public ResponseEntity<List<ListingListDTO>> getAllVipListing(@RequestParam Integer page, @RequestParam Integer count){
        return new ResponseEntity(listingService.getAllVipListing(page, count), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingListDTO> getListingByIdNoLogin(@PathVariable Long id){
        return new ResponseEntity(listingService.getListingById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/search")
    public ResponseEntity search(@RequestParam(required = false) Map<String, String> allParams) {
        return new ResponseEntity(searchService.searchListings(allParams), OK);
    }
}
