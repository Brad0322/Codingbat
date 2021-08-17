package uz.pdp.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.demo.entity.User;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.payload.UserDto;
import uz.pdp.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public HttpEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public HttpEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping()
    public HttpEntity<ApiResponse> addUser(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.addUsers(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409)
                .body(apiResponse);
    }

    @PutMapping
    public HttpEntity<ApiResponse> editUser(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.forgotPassword(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409)
                .body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
        ApiResponse apiResponse = userService.deleteUser(id);
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
