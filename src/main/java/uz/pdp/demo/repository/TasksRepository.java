package uz.pdp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.demo.entity.Tasks;

public interface TasksRepository extends JpaRepository<Tasks,Integer> {
}
