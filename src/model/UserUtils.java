package model;

import model.datastructures.User;

class UserUtils {

    private static final UserUtils USER_UTILS = new UserUtils();

    static UserUtils getInstance() {
        return USER_UTILS;
    }

    private User currentUser;

    private UserUtils() {}

    User getCurrentUser() {
        return currentUser;
    }

    void setCurrentUser(User user) {
        currentUser = user;
    }
}
