package az.code.carlada.controllers;

import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.models.Image;
import az.code.carlada.services.ImageService;
import az.code.carlada.services.ListingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import az.code.carlada.services.SearchService;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping(path = "/{id}/setThumbnail")
    public ResponseEntity<Image> setThumbnailForListing(@PathVariable Long id, @RequestParam(name = "file") MultipartFile file) throws IOException {
        return new ResponseEntity(listingService.setThumbnailForListing(id, file), OK);
    }

    @GetMapping(path = "/{id}/images")
    public ResponseEntity<Image> getAllImgByListingId(@PathVariable Long id) {
        return new ResponseEntity(imageService.getAllImgFromListing(id), OK);
    }

    @GetMapping(path = "/{id}/images/{id1}")
    public ResponseEntity<Image> getImgByListingId(@PathVariable Long id, @PathVariable Long id1) {
        return new ResponseEntity(imageService.getImgFromListing(id, id1), OK);
    }

    @PutMapping("{id}/images")
    public ResponseEntity<Image> addImgByToListing(@PathVariable Long id, @RequestParam(name = "file") MultipartFile file) throws IOException {
        return new ResponseEntity(imageService.addImgToListing(id, file), OK);
    }

    @DeleteMapping(path = "/{id}/images/{id1}")
    public ResponseEntity<String> deleteImgFromListingById(@PathVariable Long id, @PathVariable Long id1) throws IOException {
        imageService.deleteImgFromListing(id, id1);
        return new ResponseEntity("Image is deleted succesfully",OK);
    }
}