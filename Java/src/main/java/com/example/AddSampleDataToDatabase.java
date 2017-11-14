package com.example;

import com.example.dao.AccommodationDaoImpl;
import com.example.dao.CarDaoImpl;
import com.example.dao.UserDaoImpl;
import com.example.enums.Gender;
import com.example.model.Accommodation;
import com.example.model.Car;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AddSampleDataToDatabase {

    @Autowired
    private AccommodationDaoImpl accommodationDaoImpl;

    @Autowired
    private CarDaoImpl carDaoImpl;

    @Autowired
    private UserDaoImpl userDaoImpl;

    public void createSampleRecords() {
        addSampleAccommodations();
        addSampleCars();
        addSampleUsers();
        assignSampleCarsToUsers();
        bindAccommodationToUser();
    }

    public void addSampleAccommodations() {
        Accommodation accommodation = Accommodation.of("Legnicka", "20", "58250", "Wrocław");
        accommodation.setHouseNumber("5");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = Accommodation.of("Grabiszyńska", "2", "52410", "Wrocław");
        accommodation2.setHouseNumber("8");
        accommodationDaoImpl.save(accommodation2);
        Accommodation accommodation3 = Accommodation.of("Długa", "15", "53650", "Wrocław");
        accommodation3.setHouseNumber("18");
        accommodationDaoImpl.save(accommodation3);
        Accommodation accommodation4 = Accommodation.of("Krakowska", "3", "54150", "Wrocław");
        accommodation4.setHouseNumber("10");
        accommodationDaoImpl.save(accommodation4);
        Accommodation accommodation5 = Accommodation.of("Reymonta", "8", "52260", "Wrocław");
        accommodation5.setHouseNumber("14");
        accommodationDaoImpl.save(accommodation5);
        Accommodation accommodation6 = Accommodation.of("Legnicka", "10", "52250", "Wrocław");
        accommodation6.setHouseNumber("16");
        accommodationDaoImpl.save(accommodation6);
        Accommodation accommodation7 = Accommodation.of("Popowicka", "12", "53280", "Wrocław");
        accommodation7.setHouseNumber("7");
        accommodationDaoImpl.save(accommodation7);
        Accommodation accommodation8 = Accommodation.of("Legnicka", "5", "51000", "Wrocław");
        accommodation8.setHouseNumber("5");
        accommodationDaoImpl.save(accommodation8);
    }

    public void addSampleCars() {
        carDaoImpl.save(Car.of("AB12345"));
        carDaoImpl.save(Car.of("QW24680"));
        carDaoImpl.save(Car.of("AS23571"));
        carDaoImpl.save(Car.of("DW98765"));
        carDaoImpl.save(Car.of("ZX36912"));
    }

    public void addSampleUsers() {
        User user = User.of("Andrzej Kowalski", Gender.MALE, LocalDate.of(1950, 1, 1), "andrzej@example.com");
        user.setNumberOfChildren(4);
        user.setHasInsurance(true);
        userDaoImpl.save(user);
        User user2 = User.of("Barbara Kowalska", Gender.FEMALE, LocalDate.of(1980, 2, 5), "barbara@example.com");
        user2.setNumberOfChildren(2);
        user2.setHasInsurance(false);
        userDaoImpl.save(user2);
        User user3 = User.of("Cezary Nowak", Gender.MALE, LocalDate.of(1950, 1, 24), "cezary@example.com");
        user3.setNumberOfChildren(5);
        user3.setHasInsurance(false);
        userDaoImpl.save(user3);
        User user4 = User.of("dorota", Gender.FEMALE, LocalDate.of(1960, 5, 18), "dorota@example.com");
        user4.setNumberOfChildren(0);
        user4.setHasInsurance(true);
        userDaoImpl.save(user4);
        User user5 = User.of("elżbieta", Gender.FEMALE, LocalDate.of(1970, 6, 7), "elzbieta@example.com");
        user5.setNumberOfChildren(1);
        user5.setHasInsurance(false);
        userDaoImpl.save(user5);
        User user6 = User.of("feliks", Gender.MALE, LocalDate.of(1975, 11, 5), "feliks@example.com");
        user6.setNumberOfChildren(2);
        user6.setHasInsurance(false);
        userDaoImpl.save(user6);
    }

    public void assignSampleCarsToUsers() {
        carDaoImpl.assignCarToUser(carDaoImpl.findCar("AB12345").orElse(null), userDaoImpl.findUser("andrzej@example.com").orElse(null));
        carDaoImpl.assignCarToUser(carDaoImpl.findCar("QW24680").orElse(null), userDaoImpl.findUser("barbara@example.com").orElse(null));
        carDaoImpl.assignCarToUser(carDaoImpl.findCar("AS23571").orElse(null), userDaoImpl.findUser("cezary@example.com").orElse(null));
    }

    public void bindAccommodationToUser() {
        accommodationDaoImpl.bindAccommodationToUser(accommodationDaoImpl.findAccommodations("Grabiszyńska", "2", "8", "Wrocław"), userDaoImpl.findUser("andrzej@example.com").orElse(null));
        accommodationDaoImpl.bindAccommodationToUser(accommodationDaoImpl.findAccommodations("Legnicka", "10", "16", "Wrocław"), userDaoImpl.findUser("andrzej@example.com").orElse(null));
        accommodationDaoImpl.bindAccommodationToUser(accommodationDaoImpl.findAccommodations("Popowicka", "12", "7", "Wrocław"), userDaoImpl.findUser("andrzej@example.com").orElse(null));
        accommodationDaoImpl.bindAccommodationToUser(accommodationDaoImpl.findAccommodations("Grabiszyńska", "2", "8", "Wrocław"), userDaoImpl.findUser("barbara@example.com").orElse(null));
        accommodationDaoImpl.bindAccommodationToUser(accommodationDaoImpl.findAccommodations("Legnicka", "20", "5", "Wrocław"), userDaoImpl.findUser("cezary@example.com").orElse(null));
        accommodationDaoImpl.bindAccommodationToUser(accommodationDaoImpl.findAccommodations("Długa", "15", "18", "Wrocław"), userDaoImpl.findUser("dorota@example.com").orElse(null));
        accommodationDaoImpl.bindAccommodationToUser(accommodationDaoImpl.findAccommodations("Reymonta", "8", "14", "Wrocław"), userDaoImpl.findUser("elzbieta@example.com").orElse(null));
        accommodationDaoImpl.bindAccommodationToUser(accommodationDaoImpl.findAccommodations("Legnicka", "5", "5", "Wrocław"), userDaoImpl.findUser("feliks@example.com").orElse(null));
    }
}
