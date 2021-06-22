package az.code.carlada.services;

import az.code.carlada.dtos.UserDTO;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

public interface AccountService {
    UserDTO createUser(UserDTO userDTO);
    UserRepresentation user(UserDTO userDTO);
}