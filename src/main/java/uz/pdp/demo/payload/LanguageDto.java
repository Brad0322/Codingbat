package uz.pdp.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LanguageDto {
    @NotNull(message = "Til nomi kiritilishi shart!")
    private String name;
}
