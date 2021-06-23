//package az.code.carlada.controllers;
//import java.util.HashMap;
//import java.util.Map;
//import javax.ws.rs.core.Response;
//import az.code.carlada.dtos.UserDTO;
//import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
//import org.keycloak.OAuth2Constants;
//import org.keycloak.admin.client.CreatedResponseUtil;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//import org.keycloak.admin.client.resource.RealmResource;
//import org.keycloak.admin.client.resource.UserResource;
//import org.keycloak.admin.client.resource.UsersResource;
//import org.keycloak.authorization.client.AuthzClient;
//import org.keycloak.authorization.client.Configuration;
//import org.keycloak.representations.AccessTokenResponse;
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.RoleRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//@RequestMapping(value = "/users")
//@RestController
//public class AccountController {
//
//    private static final Logger log = LoggerFactory.getLogger(UserController.class);
//
//    @Value("${keycloak.auth-server-url}")
//    private String authServerUrl;
//    @Value("${keycloak.realm}")
//    private String realm;
//    @Value("${keycloak.resource}")
//    private String clientId;
//    //Get client secret from the Keycloak admin console (in the credential tab)
//    @Value("${keycloak.credentials.secret}")
//    private String clientSecret;
//
//    /*
//    * CREATE NEW USER WITHOUT ROLE
//    * */
//    @PostMapping(path = "/create")
//    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
//        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
//                .username("ihasanli2021")
//                .password("12345")
//                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
//                .username("ihasanli2021").password("12345")
//                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
//        System.out.println(OAuth2Constants.PASSWORD);
//        keycloak.tokenManager().getAccessToken();
//
//        UserRepresentation user = new UserRepresentation();
//        user.setEnabled(true);
//        user.setUsername(userDTO.getEmail());
//        user.setFirstName(userDTO.getFirstname());
//        user.setLastName(userDTO.getLastname());
//        user.setEmail(userDTO.getEmail());
//
//        /*
//        Get realm
//         */
//        RealmResource realmResource = keycloak.realm(realm);
//        UsersResource usersRessource = realmResource.users();
//
//        Response response = usersRessource.create(user);
//
//        userDTO.setStatusCode(response.getStatus());
//        userDTO.setStatus(response.getStatusInfo().toString());
//
//        if (response.getStatus() == 201) {
//            String userId = CreatedResponseUtil.getCreatedId(response);
//            log.info("Created userId {}", userId);
//            // create password credential
//            CredentialRepresentation passwordCred = new CredentialRepresentation();
//            passwordCred.setTemporary(false);
//            passwordCred.setType(CredentialRepresentation.PASSWORD);
//            passwordCred.setValue(userDTO.getPassword());
//            UserResource userResource = usersRessource.get(userId);
//            // Set password credential
//            userResource.resetPassword(passwordCred);
//        }
//        return ResponseEntity.ok(userDTO);
//    }
//
//    @PostMapping(path = "/signin")
//    public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
//
//        Map<String, Object> clientCredentials = new HashMap<>();
//        clientCredentials.put("secret", clientSecret);
//        clientCredentials.put("grant_type", "password");
//        Configuration configuration =
//                new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
//        AuthzClient authzClient = AuthzClient.create(configuration);
//        AccessTokenResponse response =
//                authzClient.obtainAccessToken(userDTO.getEmail(), userDTO.getPassword());
//        return ResponseEntity.ok(response);
//    }
//}