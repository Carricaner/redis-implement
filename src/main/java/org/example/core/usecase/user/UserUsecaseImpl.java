package org.example.core.usecase.user;


import org.example.core.usecase.user.port.UserAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUsecaseImpl implements UserUsecase {
    private final UserAdapter userAdapter;

    @Autowired
    public UserUsecaseImpl(UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }


    @Override
    public void createRole() {
        userAdapter.createRole();
    }
}
