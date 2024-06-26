package ra.module05api.service.impl;

import com.google.api.Authentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.module05api.dto.request.SignInRequest;
import ra.module05api.dto.request.SignUpRequest;
import ra.module05api.dto.response.SignInDtoResponse;
import ra.module05api.entity.Role;
import ra.module05api.entity.RoleName;
import ra.module05api.entity.User;
import ra.module05api.exception.DataFieldExistException;
import ra.module05api.exception.InvalidException;
import ra.module05api.exception.UsernameOrPasswordException;
import ra.module05api.repository.RoleRepository;
import ra.module05api.repository.UserRepository;
import ra.module05api.security.jwt.JwtProvider;
import com.google.api.Authentication;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    //Sign up
    public void signUp(SignUpRequest signUpRequest) throws DataFieldExistException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())){
            throw new DataFieldExistException("username is exist");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())){
            throw new DataFieldExistException("email is exist");
        }
        User user = mapper.map(signUpRequest, User.class); // chuyển dổi
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); // mã hóa mật khâu
        user.setStatus(true);
        Role roleUser = roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not exist"));
        user.setRoleSet(new HashSet<>());
        user.getRoleSet().add(roleUser);
        userRepository.save(user);
    };

    //Sign in
    public SignInDtoResponse signIn(SignInRequest signInRequest) throws UsernameOrPasswordException {
        // xác thực thông qua username và password
        org.springframework.security.core.Authentication authentication = null;

        // xaác thực thành công
        User user = userRepository.findByUsername(signInRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("not found"));
        // Check block user
        if (user != null && !user.isStatus()) {
            throw new InvalidException("User is blocked");
        }

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
        } catch (AuthenticationException e){
            throw new InvalidException("username hoặc mật khẩu khong chính xác");
        }

        // lấy ra userDetail
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // tao token
        String accessToken = jwtProvider.generateToken(userDetails);
        // chuyển danh sách quyền Set<Role> -> List<String>
        List<String> roles = user.getRoleSet().stream().map(r->r.getRoleName().name()).toList();
        return SignInDtoResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .accessToken(accessToken)
                .birthDay(user.getBirthDay())
                .username(user.getUsername())
                .role(roles).build();
    };
}
