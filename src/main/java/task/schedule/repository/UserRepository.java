package task.schedule.repository;

import task.schedule.entity.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository {

    User saveUser(User user);
    Optional<User> findUserById(Long id);
    int updateUser(Long id, String name, String email);
    int deleteUser(Long id);
}
