package az.code.carlada.controllers;

import az.code.carlada.dtos.*;
import az.code.carlada.exceptions.ImageNotFoundException;
import az.code.carlada.exceptions.ListingNotFound;
import az.code.carlada.exceptions.UserNotFound;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Image;
import az.code.carlada.services.interfaces.ImageService;
import az.code.carlada.services.interfaces.ListingService;
import az.code.carlada.services.interfaces.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import az.code.carlada.services.interfaces.SubscriptionService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/profile")
public class ProfileController {

    ListingService listingService;
    SubscriptionService subService;
    ProfileService profileService;
    ImageService imageService;

    public ProfileController(ListingService listingService, SubscriptionService subService, ProfileService profileService, ImageService imageService) {
        this.listingService = listingService;
        this.subService = subService;
        this.profileService = profileService;
        this.imageService = imageService;
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> handlerNotFoundException(UserNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<String> handlerNotFoundException(ImageNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ListingNotFound.class)
    public ResponseEntity<String> handlerNotFoundException(ListingNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @GetMapping("/listings")
    public ResponseEntity<List<ListingListDTO>> getAllListingByProfile(@RequestAttribute("user") UserDTO user, @RequestParam Integer page, @RequestParam Integer count) {
        return new ResponseEntity(listingService.getAllListingByProfile(page, count, user.getUsername()), HttpStatus.OK);
    }

    @PostMapping("/listings")
    public ResponseEntity<List<ListingGetDTO>> createNewListing(@RequestAttribute("user") UserDTO user, @RequestBody ListingCreationDTO listingCreationDTO) {
        return new ResponseEntity(listingService.saveListing(listingCreationDTO, user.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/listings/{id}")
    public ResponseEntity<List<ListingGetDTO>> createNewListing(@RequestAttribute("user") UserDTO user, @PathVariable Long id, @RequestBody ListingCreationDTO listingCreationDTO) {
        return new ResponseEntity(listingService.saveListing(listingCreationDTO.toBuilder().id(id).build(), user.getUsername()), HttpStatus.OK);
    }

    @DeleteMapping("/listings/{id}")
    public ResponseEntity<String> deleteListing(@RequestAttribute("user") UserDTO user, @PathVariable long id) {
        listingService.delete(id, user.getUsername());
        return new ResponseEntity("Listing is deleted succesfully", HttpStatus.OK);
    }

    @GetMapping("/listings/{id}")
    public ResponseEntity<ListingGetDTO> getListingByIdByProfile(@RequestAttribute("user") UserDTO user, @PathVariable Long id) {
        return new ResponseEntity(listingService.getListingByIdByProfile(id, user.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/listings/{listingId}/makevip")
    public ResponseEntity<TransactionListDTO> payForVipStatus(@RequestAttribute("user") UserDTO user, @PathVariable Long listingId) {
        return new ResponseEntity<>(profileService.payForVipStatus(listingId, user.getUsername()), HttpStatus.OK);
    }

    @PutMapping("/listings/{listingId}/makepaid")
    public ResponseEntity<TransactionListDTO> payForStandardStatus(@RequestAttribute("user") UserDTO user, @PathVariable Long listingId) {
        return new ResponseEntity<>(profileService.payForStandardStatus(listingId, user.getUsername()), HttpStatus.OK);
    }

    @GetMapping("/subscriptions")
    public ResponseEntity getSubscriptions(@RequestAttribute("user") UserDTO user) {
        return new ResponseEntity(subService.getSubscriptions(user.getUsername()), OK);
    }

    @GetMapping("/subscriptions/{id}")
    public ResponseEntity getSubscriptionById(@RequestAttribute("user") UserDTO user, @PathVariable Long id) {
        return new ResponseEntity(subService.getSubscriptionById(id, user.getUsername()), OK);
    }

    @PostMapping("/subscriptions")
    public ResponseEntity createSubscription(@RequestAttribute("user") UserDTO user, @RequestBody SubscriptionDTO subDTO) {
        return new ResponseEntity(subService.saveSubscription(subDTO, user.getUsername()), OK);
    }

    @PutMapping("/subscriptions/{id}")
    public ResponseEntity updateSubscription(@RequestAttribute("user") UserDTO user, @PathVariable Long id, @RequestBody SubscriptionDTO subDTO) {
        subDTO.setSubId(id);
        return new ResponseEntity(subService.saveSubscription(subDTO, user.getUsername()), OK);
    }

    @DeleteMapping("/subscriptions/{id}")
    public ResponseEntity disableSubscription(@RequestAttribute("user") UserDTO user, @PathVariable Long id) {
        subService.deleteSubscription(id, user.getUsername());
        return new ResponseEntity(OK);
    }

    @PutMapping("/wallet/increase")
    public ResponseEntity<TransactionListDTO> wallet(@RequestAttribute("user") UserDTO user, @RequestParam Double amount) {
        return new ResponseEntity<>(profileService.addAmount(user.getUsername(), amount), HttpStatus.OK);
    }

    @PutMapping("/listings/{id}/images")
    public ResponseEntity<Image> addImgByToListing(@RequestAttribute("user") UserDTO user, @PathVariable Long id, @RequestParam(name = "file") MultipartFile file) throws IOException {
        return new ResponseEntity(imageService.addImgToListing(id, file, user.getUsername()), OK);
    }

    @DeleteMapping(path = "/listings/{id}/images/{id1}")
    public ResponseEntity<String> deleteImgFromListingById(@RequestAttribute("user") UserDTO user, @PathVariable Long id, @PathVariable Long id1) throws IOException {
        imageService.deleteImgFromListing(id, id1, user.getUsername());
        return new ResponseEntity("Image is deleted succesfully", OK);
    }
    @PutMapping(path = "/listings/{id}/setThumbnail")
    public ResponseEntity<Image> setThumbnailForListing(@RequestAttribute("user") UserDTO user, @PathVariable Long id, @RequestParam(name = "file") MultipartFile file) throws IOException {
        return new ResponseEntity(imageService.setThumbnailForListing(id, file,user.getUsername()), OK);
    }
}
