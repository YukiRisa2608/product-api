package ra.module05api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ra.module05api.entity.Cart;
import ra.module05api.entity.User;

@Getter
@Setter
@AllArgsConstructor
public class UserCartDto {

    private User user;

    private Cart cart;

}
