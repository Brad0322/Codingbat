package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import uz.pdp.demo.entity.Tasks;
import uz.pdp.demo.entity.Topics;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.payload.TasksDto;
import uz.pdp.demo.repository.TasksRepository;
import uz.pdp.demo.repository.TopicsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TasksRepository tasksRepository;
    private final TopicsRepository topicsRepository;

    @Autowired
    public TaskService(TasksRepository tasksRepository, TopicsRepository topicsRepository) {
        this.tasksRepository = tasksRepository;
        this.topicsRepository = topicsRepository;
    }

    public List<Tasks> getTasks() {
        return tasksRepository.findAll();
    }

    public Tasks getById(Integer id) {
        return tasksRepository.getById(id);
    }

    public ApiResponse addTask(TasksDto tasksDto) {
        Optional<Topics> byId =
                topicsRepository.findById(tasksDto.getTopicId());
        if (byId.isEmpty()) {
            return new ApiResponse("Bunday topic topilmadi", false);
        }
        Tasks tasks = new Tasks();
        tasks.setName(tasksDto.getName());
        tasks.setQuestion(tasksDto.getQuestion());
        tasks.setExample(tasksDto.getExample());
        tasks.setSolution(tasksDto.getSolution());
        tasks.setTopics(byId.get());
        tasksRepository.save(tasks);
        return new ApiResponse("Vazifa muvaffaqiyatli qushildi", true);
    }

    public ApiResponse editTask(Integer id, TasksDto tasksDto) {
        Optional<Topics> byId =
                topicsRepository.findById(tasksDto.getTopicId());
        if (byId.isEmpty()) {
            return new ApiResponse("Bunday topic topilmadi", false);
        }
        Topics topics = byId.get();
        Optional<Tasks> optional = tasksRepository.findById(id);
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday vazifa topilmadi", false);
        }
        Tasks tasks = optional.get();
        tasks.setName(tasksDto.getName());
        tasks.setExample(tasksDto.getExample());
        tasks.setQuestion(tasksDto.getQuestion());
        tasks.setSolution(tasksDto.getSolution());
        tasks.setTopics(topics);
        tasksRepository.save(tasks);
        return new ApiResponse("Vazifa muvaffaqiyatli tahrirlandi", true);
    }

    public ApiResponse deleteTask(Integer id) {
        Optional<Tasks> optional = tasksRepository.findById(id);
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday vazifa topilmadi", false);
        }
        tasksRepository.deleteById(id);
        return new ApiResponse("Vazifa muvaffaqiyatli tahrirlandi", true);
    }

}
