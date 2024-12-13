package vn.edu.iuh.fit.backend.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.entities.User;
import vn.edu.iuh.fit.backend.respositories.UserRepository;
import vn.edu.iuh.fit.backend.services.UserServiceImp;

@Service
public class UserService implements UserServiceImp {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password).orElse(null);
        System.out.println(user);
        if (user == null) {
            return null;
        }
        return user;

    }

    @Override
    public User getUser(String username, String password) {
        return null;
    }
}
