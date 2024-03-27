package ra.module05api.service;

import ra.module05api.dto.UserDto;
import ra.module05api.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getListUser();
    User getUserById(Long id);
    User createNewUser(UserDto userDto);
    User updateUserById(Long id, UserDto userDto);
    User deleteUserById(Long id);
}
