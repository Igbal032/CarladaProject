package az.code.carlada.dtos;

import lombok.Data;

@Data
public class RegisterDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
}
