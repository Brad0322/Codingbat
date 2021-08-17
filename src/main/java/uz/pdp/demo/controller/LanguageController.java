package uz.pdp.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.demo.entity.Languages;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.payload.LanguageDto;
import uz.pdp.demo.service.LanguageService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/lanuage")
public class LanguageController {
    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public HttpEntity<?> languagesList() {
        return ResponseEntity.ok(languageService.getLanguages());
    }

    @GetMapping("/{id}")
    public HttpEntity<Languages> getLanguageById(@PathVariable Integer id) {
        return ResponseEntity.ok(languageService.getLanguageById(id));
    }

    @PostMapping
    public HttpEntity<ApiResponse> addLanguages(@Valid @RequestBody LanguageDto languageDto) {
        ApiResponse apiResponse = languageService.addLanguage(languageDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409)
                .body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editLanguage(@PathVariable Integer id, @Valid @RequestBody LanguageDto languageDto) {
        ApiResponse apiResponse = languageService.editLanguage(id, languageDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409)
                .body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteLanguage(@PathVariable Integer id) {
        ApiResponse apiResponse = languageService.deleteLanguage(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409)
                .body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
