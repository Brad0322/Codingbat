package uz.pdp.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    @NotNull(message = "email bush bo'lmasligi shart!")
    private String email;

    @NotNull(message = "parol kiritilmagan!")
    private String password;
}
