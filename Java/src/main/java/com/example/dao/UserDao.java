package com.example.dao;

import com.example.enums.Gender;
import com.example.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    void save(final User user);

    Optional<User> findUser(final String email);

    List<User> findUsersByGender(final Gender gender);

    List<User> findUsers(final LocalDate minimumDate, final LocalDate maximumDate, final String stringToContain);
}
