package uz.pdp.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.demo.entity.Languages;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopicDto {

    @NotNull(message = "nom bush bo'lmasligi shart!")
    private String name;

    private String comment;

    private int languageId;
}
