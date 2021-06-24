package az.code.carlada.services.interfaces;

import az.code.carlada.dtos.UserDTO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

public interface AccountService {
    UserDTO createUser(UserDTO userDTO);
    AccessTokenResponse signin(UserDTO userDTO);
    UserRepresentation userRepresentation(UserDTO userDTO);
    CredentialRepresentation passwordCred(UserDTO userDTO);
    Keycloak connectKeycloak();
}