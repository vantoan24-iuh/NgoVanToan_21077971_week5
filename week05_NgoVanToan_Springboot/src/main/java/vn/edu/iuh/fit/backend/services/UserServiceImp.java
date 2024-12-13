package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.entities.User;

public interface UserServiceImp {
    User getUserByUsernameAndPassword(String username, String password);

    User getUser(String username, String password);
}
