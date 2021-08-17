package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.demo.entity.Languages;
import uz.pdp.demo.entity.Topics;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.payload.TopicDto;
import uz.pdp.demo.repository.LanguagesRepository;
import uz.pdp.demo.repository.TopicsRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Service
public class TopicsService {
    private final TopicsRepository topicsRepository;
    private final LanguagesRepository languagesRepository;

    @Autowired
    public TopicsService(TopicsRepository topicsRepository, LanguagesRepository languagesRepository) {
        this.topicsRepository = topicsRepository;
        this.languagesRepository = languagesRepository;
    }

    public List<Topics> getTopics() {
        return topicsRepository.findAll();
    }

    public Topics getTopicById(Integer id) {
        return topicsRepository.findById(id).get();
    }

    public ApiResponse addTopics(TopicDto topicDto) {
        Topics topics = new Topics();
        Optional<Languages> optional = languagesRepository.findById(topicDto.getLanguageId());
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday dasturlash tili bazadan topilmadi.", false);
        }
        topics.setName(topicDto.getName());
        topics.setComment(topicDto.getComment());
        topics.setLanguages(optional.get());
        topicsRepository.save(topics);
        return new ApiResponse("Topic qushildi.", true);
    }

    public ApiResponse editTopics(Integer id,TopicDto topicDto){
        Optional<Topics> topicsOptional = topicsRepository.findById(id);
        if (topicsOptional.isEmpty()) {
            return new ApiResponse("Bunday topic bazadan topilmadi.", false);
        }
        Optional<Languages> optional = languagesRepository.findById(topicDto.getLanguageId());
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday dasturlash tili bazadan topilmadi.", false);
        }
        Topics topics = topicsOptional.get();
        topics.setName(topicDto.getName());
        topics.setComment(topicDto.getComment());
        topics.setLanguages(optional.get());
        return new ApiResponse("Topic tahrirlandi",true);
    }

    public ApiResponse deleteTopic(Integer id){
        Optional<Topics> optional = topicsRepository.findById(id);
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday topic bazadan topilmadi.", false);
        }
        topicsRepository.deleteById(id);
        return new ApiResponse("Topic uchirildi.",true);
    }
}
