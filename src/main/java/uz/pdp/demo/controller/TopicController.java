package uz.pdp.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.demo.entity.Topics;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.payload.TopicDto;
import uz.pdp.demo.service.TopicsService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/topic")
public class TopicController {
    private final TopicsService topicsService;

    @Autowired
    public TopicController(TopicsService topicsService) {
        this.topicsService = topicsService;
    }

    @GetMapping
    public HttpEntity<List<Topics>> getTopics() {
        return ResponseEntity.ok(topicsService.getTopics());
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getTopicById(@PathVariable Integer id) {
        return ResponseEntity.ok(topicsService.getTopicById(id));
    }

    @PostMapping
    public HttpEntity<ApiResponse> addTopics(@Valid @RequestBody TopicDto topicDto) {
        ApiResponse apiResponse = topicsService.addTopics(topicDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409)
                .body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editTopic(@PathVariable Integer id, @Valid @RequestBody TopicDto topicDto) {
        ApiResponse apiResponse = topicsService.editTopics(id, topicDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409)
                .body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteTopic(@PathVariable Integer id) {
        ApiResponse apiResponse = topicsService.deleteTopic(id);
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
