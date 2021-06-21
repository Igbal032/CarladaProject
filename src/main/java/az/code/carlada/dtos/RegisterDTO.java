package az.code.carlada.dtos;

import lombok.Data;

@Data
public class RegisterDTO {
    String name;
    String surname;
    String email;
    String password;
}
