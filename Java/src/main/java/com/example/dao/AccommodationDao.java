package com.example.dao;

import com.example.model.Accommodation;
import com.example.model.User;

import java.util.List;

public interface AccommodationDao {

    void save(final Accommodation accommodation);

    List<Accommodation> findAccommodations(final String street, final String streetNumber, final String houseNumber, final String city);

    void bindAccommodationToUser(final List<Accommodation> accommodations, final User user);
}
