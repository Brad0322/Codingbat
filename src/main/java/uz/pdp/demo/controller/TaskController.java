package uz.pdp.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.demo.entity.Tasks;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.payload.TasksDto;
import uz.pdp.demo.service.TaskService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public HttpEntity<List<Tasks>> getTasks() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @GetMapping("/{id}")
    public HttpEntity<Tasks> getTask(@PathVariable Integer id) {
        Tasks tasks = taskService.getById(id);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public HttpEntity<ApiResponse> addTask(@Valid @RequestBody TasksDto tasksDto) {
        ApiResponse apiResponse = taskService.addTask(tasksDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409)
                .body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> editTask(@PathVariable Integer id, @Valid @RequestBody TasksDto tasksDto) {
        ApiResponse apiResponse = taskService.editTask(id, tasksDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409)
                .body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteTask(@PathVariable Integer id) {
        ApiResponse apiResponse = taskService.deleteTask(id);
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
