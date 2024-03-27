package ra.module05api.service;
import ra.module05api.dto.UserDto;
import ra.module05api.entity.User;
import ra.module05api.exception.ResourceNotFoundException;

import java.util.List;

public interface IUserService {
    List<User> getListUser();
    User getUserById(Long id);
    User createNewUser(UserDto userDto);
    Object toggleStatus(Long id) throws ResourceNotFoundException;
}
