package fairfinance.pocketpartners.backend.users.interfaces.rest;

import fairfinance.pocketpartners.backend.users.domain.services.UserInformationCommandService;
import fairfinance.pocketpartners.backend.users.interfaces.rest.resources.AuthenticatedUserResource;
import fairfinance.pocketpartners.backend.users.interfaces.rest.resources.SignInResource;
import fairfinance.pocketpartners.backend.users.interfaces.rest.resources.SignUpResource;
import fairfinance.pocketpartners.backend.users.interfaces.rest.resources.UserInformationResource;
import fairfinance.pocketpartners.backend.users.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import fairfinance.pocketpartners.backend.users.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import fairfinance.pocketpartners.backend.users.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import fairfinance.pocketpartners.backend.users.interfaces.rest.transform.UserInformationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
    private final UserInformationCommandService userInformationCommandService;

    public AuthenticationController(UserInformationCommandService userInformationCommandService) {
        this.userInformationCommandService = userInformationCommandService;
    }

    /**
     * Handles the sign-in request.
     * @param signInResource the sign-in request body.
     * @return the authenticated user resource.
     */
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource signInResource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var authenticatedUser = userInformationCommandService.handle(signInCommand);
        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    /**
     * Handles the sign-up request.
     * @param signUpResource the sign-up request body.
     * @return the created user resource.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserInformationResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var user = userInformationCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserInformationResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);

    }
}