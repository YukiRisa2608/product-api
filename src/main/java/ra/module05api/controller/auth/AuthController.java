package ra.module05api.controller.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.module05api.dto.request.SignInRequest;
import ra.module05api.dto.request.SignUpRequest;
import ra.module05api.dto.response.ResponseDtoSuccess;
import ra.module05api.dto.response.SignInDtoResponse;
import ra.module05api.exception.DataFieldExistException;
import ra.module05api.exception.UsernameOrPasswordException;
import ra.module05api.service.AuthenticationService;

@RestController
@RequestMapping("/api.com/v2/auth")  // công khai
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) throws UsernameOrPasswordException {
        SignInDtoResponse data = authenticationService.signIn(signInRequest);
        return new ResponseEntity<>(new ResponseDtoSuccess(data,HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) throws DataFieldExistException {
        authenticationService.signUp(signUpRequest);
        return new ResponseEntity<>(new ResponseDtoSuccess("Đăng kí thành công",HttpStatus.CREATED), HttpStatus.CREATED);
    }
}
