package az.code.carlada.controllers;

import az.code.carlada.dtos.SearchDTO;
import az.code.carlada.services.DictionaryService;
import az.code.carlada.services.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/listings")
public class ListingController {
    SearchService searchService;

    public ListingController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(path = "/search")
    public ResponseEntity search(@RequestParam(required = false) Map<String, String> allParams) {
        return new ResponseEntity(searchService.searchListings(allParams), OK);
    }
}
