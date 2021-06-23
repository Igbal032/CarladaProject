package az.code.carlada.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private double amount;
    private int statusCode;
    private String status;
}
