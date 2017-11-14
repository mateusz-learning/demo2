package com.example;

import com.example.enums.Gender;
import com.example.model.Accommodation;
import com.example.model.Car;
import com.example.model.User;
import org.apache.commons.text.RandomStringGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class TestObjectFactory {
    private static final RandomStringGenerator upperCaseGenerator = new RandomStringGenerator.Builder().withinRange('A', 'Z').build();
    private static final RandomStringGenerator lowerCaseGenerator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
    private static final RandomStringGenerator numberStringGenerator = new RandomStringGenerator.Builder().withinRange('0', '9').build();
    private static final Random random = new Random();

    public static Accommodation createRandomAccommodation() {
        Accommodation accommodation = Accommodation.of(createRandomString(10), createRandomStreetNumberString(), createRandomPostcode(), createRandomString(10));
        accommodation.setHouseNumber(createRandomHouseNumberString());

        return accommodation;
    }

    public static Car createRandomCar() {
        Car car = Car.of(createRandomLicenceNumber());

        return car;
    }

    public static User createRandomUser() {
        String username = createRandomUsername();

        User user = User.of(username, createRandomGender(), createRandomDate(), createRandomEmail(username));
        user.setNumberOfChildren(createRandomChildrenNumber());
        user.setHasInsurance(createRandomInsurance());

        return user;
    }

    public static String createRandomString(int length) {
        return upperCaseGenerator.generate(1) + lowerCaseGenerator.generate(length -1);
    }

    public static String createRandomStreetNumberString() {
        return String.valueOf(random.nextInt(99) + 1);
    }

    public static String createRandomHouseNumberString() {
        return String.valueOf(random.nextInt(20) + 1);
    }

    public static String createRandomPostcode() {
        return String.valueOf(random.nextInt(9) + 1) + numberStringGenerator.generate(4);
    }

    public static String createRandomLicenceNumber() {
        return upperCaseGenerator.generate(5) + numberStringGenerator.generate(10);
    }

    public static String createRandomUsername() {
        return upperCaseGenerator.generate(1) + lowerCaseGenerator.generate(9) + " " + upperCaseGenerator.generate(1) + lowerCaseGenerator.generate(7);
    }

    public static Gender createRandomGender() {
        ArrayList<Gender> genders = new ArrayList<Gender>();
        genders.add(Gender.MALE);
        genders.add(Gender.FEMALE);

        return genders.get(random.nextInt(2));
    }

    public static LocalDate createRandomDate() {
        return LocalDate.of(1915, 1, 1).plusYears(random.nextInt(101)).plusDays(random.nextInt(366));
    }

    public static String createRandomEmail(String username) {
        return username.replace(' ', '.').toLowerCase() + "@example.com";
    }

    public static int createRandomChildrenNumber() {
        return random.nextInt(6);
    }

    public static Boolean createRandomInsurance() {
        return random.nextBoolean();
    }
}
