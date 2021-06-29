package az.code.carlada.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserDTO {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String username;
    private String phoneNumber;
    private double amount;
    private int statusCode;
    private String status;
}
