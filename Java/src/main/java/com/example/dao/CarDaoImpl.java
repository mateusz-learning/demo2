package com.example.dao;

import com.example.model.Car;
import com.example.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Repository
public class CarDaoImpl implements CarDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(final Car car) {
        entityManager.persist(car);
    }

    @Override
    @Transactional
    public Optional<Car> findCar(final String licenceNumber) {
        if (isBlank(licenceNumber)) {
            return Optional.empty();
        }

        Query query = entityManager.createQuery("SELECT c from Car c WHERE lower(c.licenceNumber) = lower(:licenceNumber)")
                .setParameter("licenceNumber", licenceNumber);

        try {
            return Optional.ofNullable((Car) query.getSingleResult());
        } catch(NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void assignCarToUser(final Car car, final User user) {
        if (car == null || user == null) {
            return;
        }

        user.addCar(car);
        entityManager.merge(user);
    }
}
