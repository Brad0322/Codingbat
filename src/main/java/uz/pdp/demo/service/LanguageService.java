package uz.pdp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.demo.entity.Languages;
import uz.pdp.demo.payload.ApiResponse;
import uz.pdp.demo.payload.LanguageDto;
import uz.pdp.demo.repository.LanguagesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    private final LanguagesRepository languagesRepository;

    @Autowired
    public LanguageService(LanguagesRepository languagesRepository) {
        this.languagesRepository = languagesRepository;
    }

    public List<Languages> getLanguages() {
        return languagesRepository.findAll();
    }

    public Languages getLanguageById(Integer id) {
        Optional<Languages> optional = languagesRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    public ApiResponse addLanguage(LanguageDto languageDto) {

        boolean exists = languagesRepository.existsByName(languageDto.getName());
        if (exists) {
            return new ApiResponse("Bunday nomli dasturlash tili mavjud!", false);
        }
        Languages languages = new Languages();
        languages.setName(languageDto.getName());
        languagesRepository.save(languages);
        return new ApiResponse("Dasturlash tili mjuvaffaqiyatli qushildi!", true);
    }

    public ApiResponse editLanguage(Integer id, LanguageDto languageDto) {
        Optional<Languages> optional = languagesRepository.findById(id);
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday ID lik dasturlash tili mavjud emas!", false);
        }
        boolean exists = languagesRepository.existsByNameAndIdNot(languageDto.getName(), id);
        if (exists) {
            return new ApiResponse("Bunday nomli dasturlash tili mavjud!", false);
        }
        Languages languages = optional.get();
        languages.setName(languageDto.getName());
        languagesRepository.save(languages);
        return new ApiResponse("Dasturlash tili mjuvaffaqiyatli tahrirlandi!", true);
    }

    public ApiResponse deleteLanguage(Integer id) {
        Optional<Languages> optional = languagesRepository.findById(id);
        if (optional.isEmpty()) {
            return new ApiResponse("Bunday ID lik dasturlash tili mavjud emas!", false);
        }
        languagesRepository.deleteById(id);

        return new ApiResponse("Dasturlash tili mjuvaffaqiyatli uchirirldi!", true);
    }
}
