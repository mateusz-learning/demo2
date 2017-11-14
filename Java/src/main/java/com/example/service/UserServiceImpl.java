package com.example.service;

import com.example.dao.UserDaoImpl;
import com.example.model.Accommodation;
import com.example.model.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptySet;
import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoImpl userDaoImpl;

    @Override
    public String displayUser(String email) {
        Optional<User> user = userDaoImpl.findUser(email);

        if (!user.isPresent()) {
            return "User doesn't exist.";
        }

        Set<Accommodation> accommodations = user.map(User::getAccommodations).orElse(emptySet());
        String accommodationString;

        if (CollectionUtils.isNotEmpty(accommodations)) {
            accommodationString = accommodations.stream().map(this::getAccommodationString).collect(Collectors.joining(", "));
        }
        else {
            accommodationString =  "No accommodations to dispaly.";
        }
        return String.format("User name: %s\n---\nAddress: %s", user.get().getUserName(), accommodationString);
    }

    private String getAccommodationString(Accommodation accommodation) {
        return String.format("%s %s%s %s", accommodation.getStreet(), accommodation.getStreetNumber(), getHouseNumber(accommodation), accommodation.getCity());
    }

    private String getHouseNumber(Accommodation a) {
            return nonNull(a.getHouseNumber()) ? "/" + a.getHouseNumber() : StringUtils.EMPTY;
    }
}
