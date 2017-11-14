package com.example.dao;

import com.example.model.Car;
import com.example.model.User;

import java.util.Optional;

public interface CarDao {

    void save(final Car car);

    Optional<Car> findCar(final String licenceNumber);

    void assignCarToUser(final Car car, final User user);
}
