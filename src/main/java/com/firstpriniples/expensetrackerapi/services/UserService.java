package com.firstpriniples.expensetrackerapi.services;

import com.firstpriniples.expensetrackerapi.domain.User;
import com.firstpriniples.expensetrackerapi.exceptions.EtAuthException;

public interface UserService {
    User validateUser(String email, String password) throws EtAuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException;
}