package ra.module05api.service.impl;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ra.module05api.dto.UserDto;
import ra.module05api.entity.Role;
import ra.module05api.entity.User;
import ra.module05api.exception.InvalidException;
import ra.module05api.exception.NotFoundException;
import ra.module05api.exception.ResourceNotFoundException;
import ra.module05api.repository.RoleRepository;
import ra.module05api.repository.UserRepository;
import ra.module05api.service.IUserService;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    //get list
    @Override
    public List<User> getListUser() {
        return userRepository.findAll();
    }

    //get by id
    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException("Can not find user by id " + id);
        }

        return user;
    }

    //add new user
    @Override
    public User createNewUser(UserDto userDto) {
        User user = userRepository.findByEmailOrUsername(userDto.getEmail(), userDto.getUsername());

        if (user != null) {
            throw new InvalidException("Duplicate user by email: " + user.getEmail() + ", username: " + user.getUsername());
        }
        Role role = roleRepository.findById(userDto.getRoleId()).orElse(null);
        if (role == null) {
            throw new NotFoundException("Can not found role");
        }

        user = User.builder()
                .fullName(userDto.getFullName().trim())
                .username(userDto.getUsername().trim())
                .password(userDto.getPassword().trim())
                .email(userDto.getEmail().trim())
                .roleSet(Set.of(role))
                .status(Boolean.TRUE)
                .build();

        return userRepository.save(user);
    }

//toggle
    @SneakyThrows
    public Object toggleStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setStatus(!user.isStatus());
        userRepository.save(user);

        return "Oke";
    }
}
