package uz.pdp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo.entity.Languages;

public interface LanguagesRepository extends JpaRepository<Languages,Integer> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
