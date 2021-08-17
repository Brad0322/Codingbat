package uz.pdp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo.entity.Topics;

public interface TopicsRepository extends JpaRepository<Topics,Integer> {
}
