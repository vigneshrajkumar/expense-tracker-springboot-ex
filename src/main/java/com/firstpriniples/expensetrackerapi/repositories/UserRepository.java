package com.firstpriniples.expensetrackerapi.repositories;

import com.firstpriniples.expensetrackerapi.domain.User;
import com.firstpriniples.expensetrackerapi.exceptions.EtAuthException;

public interface UserRepository {
    Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;
    User findByEmailAndPassword(String email, String password) throws EtAuthException;
    Integer getCountByEmail(String email);
    User findById(Integer userId);
}