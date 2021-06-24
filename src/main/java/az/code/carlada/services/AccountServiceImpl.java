package az.code.carlada.services;

import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.daos.interfaces.AccountDAO;
import az.code.carlada.dtos.UserDTO;
import az.code.carlada.services.interfaces.AccountService;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {
    Environment environment;
    AccountDAO accountDAO;
    ModelMapperComponent modelMapperComponent;

    public AccountServiceImpl(Environment environment, AccountDAO accountDAO, ModelMapperComponent modelMapperComponent) {
        this.environment = environment;
        this.accountDAO = accountDAO;
        this.modelMapperComponent = modelMapperComponent;
    }

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        Keycloak keycloak = connectKeycloak();
        //Call account service to save user
        UserRepresentation userRepresentation = userRepresentation(userDTO);
        //Get realm
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersRessource = realmResource.users();
        Response response = usersRessource.create(userRepresentation);
        userDTO.setStatusCode(response.getStatus());
        userDTO.setStatus(response.getStatusInfo().toString());
        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            // create password credential
            CredentialRepresentation passwordCred = passwordCred(userDTO);
            UserResource userResource = usersRessource.get(userId);
            RoleRepresentation realmRoleUser = realmResource.roles().get("user").toRepresentation();

            // Assign realm role student to user
            userResource.roles().realmLevel().add(Arrays.asList(realmRoleUser));
            // Set password credential
            userResource.resetPassword(passwordCred);
            //create newUser for database
            accountDAO.createUser(userDTO);
        }
        return userDTO;
    }

    @Override
    public AccessTokenResponse signin(UserDTO userDTO) {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("grant_type", "password");
        Configuration configuration =
                new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);
        return authzClient.obtainAccessToken(userDTO.getEmail(), userDTO.getPassword());
    }

    @Override
    public UserRepresentation userRepresentation(UserDTO userDTO) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setAttributes(Collections.singletonMap("phone", Collections.singletonList(userDTO.getPhoneNumber())));
        return user;
    }

    @Override
    public CredentialRepresentation passwordCred(UserDTO userDTO) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(userDTO.getPassword());
        return passwordCred;
    }

    @Override
    public Keycloak connectKeycloak() {
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(environment.getProperty("keycloak.auth-server-url"))
                .grantType(OAuth2Constants.PASSWORD).realm(environment.getProperty("app.keycloak.realmMain"))
                .clientId(environment.getProperty("app.keycloak.clientIdMain")).username(environment.getProperty("app.keycloak.usernameMain"))
                .password(environment.getProperty("app.keycloak.passwordMain"))
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        keycloak.tokenManager().getAccessToken();
        return keycloak;
    }
}