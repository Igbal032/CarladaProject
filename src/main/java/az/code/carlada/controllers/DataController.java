package az.code.carlada.controllers;

import az.code.carlada.exceptions.DataNotFound;
import az.code.carlada.services.DictionaryService;
import az.code.carlada.services.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = "/api/v1/data")
public class DataController {
    DictionaryService dictionaryService;

    public DataController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @ExceptionHandler(DataNotFound.class)
    public ResponseEntity<String> handlerNotFoundException(DataNotFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/makes")
    public ResponseEntity getMakes() {
        return new ResponseEntity(dictionaryService.getMakes(), OK);
    }

    @GetMapping(path = "/makes/{id}/models")
    public ResponseEntity getModels(@PathVariable Long id) {
        return new ResponseEntity(dictionaryService.getModels(id), OK);
    }

    @GetMapping(path = "/cities")
    public ResponseEntity getCities() {
        return new ResponseEntity(dictionaryService.getCities(), OK);
    }

    @GetMapping(path = "/bodyTypes")
    public ResponseEntity getBodyTypes() {
        return new ResponseEntity(dictionaryService.getBodyTypes(), OK);
    }

    @GetMapping(path = "/fuelTypes")
    public ResponseEntity getFuelTypes() {
        return new ResponseEntity(dictionaryService.getFuelTypes(), OK);
    }


}
