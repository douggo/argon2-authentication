package com.douggo.login.application.gateway;

import com.douggo.login.domain.entity.Password;
import com.douggo.login.domain.entity.User;

public interface PasswordGateway {

    void createPassword(User user, Password password);

}
