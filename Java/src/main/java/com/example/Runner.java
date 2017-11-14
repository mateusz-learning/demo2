package com.example;

import com.example.dao.AccommodationDaoImpl;
import com.example.dao.CarDaoImpl;
import com.example.dao.UserDaoImpl;
import com.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    AddSampleDataToDatabase addSampleDataToDatabase;

    @Autowired
    AccommodationDaoImpl accommodationDaoImpl;

    @Autowired
    CarDaoImpl carDaoImpl;

    @Autowired
    UserDaoImpl userDaoImpl;

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public void run(String... args) throws Exception {
        //addSampleDataToDatabase.createSampleRecords();
    }
}
