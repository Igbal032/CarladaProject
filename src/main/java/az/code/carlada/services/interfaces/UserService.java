package az.code.carlada.services.interfaces;

import az.code.carlada.dtos.UserDTO;
import org.apache.catalina.User;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    AccessTokenResponse signin(UserDTO userDTO);

    UserRepresentation userRepresentation(UserDTO userDTO);

    CredentialRepresentation passwordCred(UserDTO userDTO);

    Boolean verifyUser(UserDTO userDTO, String token);

    void sendVerifyEmail(UserDTO userDTO);

    Keycloak connectKeycloak();
}