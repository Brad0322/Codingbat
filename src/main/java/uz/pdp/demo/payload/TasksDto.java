package uz.pdp.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.demo.entity.Topics;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TasksDto {

    @NotNull(message = "Savol nomi kiritilishi shart!")
    private String name;

    @NotNull(message = "Savol kiritilmadi!")
    private String question;

    private String example;

    private String solution;

    private Integer topicId;
}
