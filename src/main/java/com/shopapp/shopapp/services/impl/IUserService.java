package com.shopapp.shopapp.services.impl;

import com.shopapp.shopapp.dto.request.UserRegisterDTO;
import com.shopapp.shopapp.exception.DataNotFoundException;
import com.shopapp.shopapp.model.User;

public interface IUserService {
    User createUser(UserRegisterDTO userRegisterDTO) throws DataNotFoundException;
    String login(String phoneNumber,String password) throws Exception;
}
