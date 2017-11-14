package com.example.dao;

import com.example.model.Accommodation;
import com.example.model.Accommodation_;
import com.example.model.User;
import org.springframework.stereotype.Repository;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AccommodationDaoImpl implements AccommodationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(final Accommodation accommodation) {
        entityManager.persist(accommodation);
    }

    @Override
    @Transactional
    public List<Accommodation> findAccommodations(final String street, final String streetNumber, final String houseNumber, final String city) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Accommodation> criteriaQuery = criteriaBuilder.createQuery(Accommodation.class);
        Root<Accommodation> accommodation = criteriaQuery.from(Accommodation.class);

        List<Predicate> predicates = new ArrayList<>();

        Optional.ofNullable(street)
                .map(value -> criteriaBuilder.equal(criteriaBuilder.lower(accommodation.get(Accommodation_.street)), value.toLowerCase()))
                .ifPresent(predicates::add);
        
        Optional.ofNullable(streetNumber)
                .map(value -> criteriaBuilder.equal(accommodation.get(Accommodation_.streetNumber), value))
                .ifPresent(predicates::add);

        Optional.ofNullable(houseNumber)
                .map(value -> criteriaBuilder.equal(accommodation.get(Accommodation_.houseNumber), value))
                .ifPresent(predicates::add);

        Optional.ofNullable(city)
                .map(value -> criteriaBuilder.equal(criteriaBuilder.lower(accommodation.get(Accommodation_.city)), value.toLowerCase()))
                .ifPresent(predicates::add);

        criteriaQuery.where(
                criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
        )
                .select(accommodation);

        TypedQuery<Accommodation> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public void bindAccommodationToUser(final List<Accommodation> accommodations, final User user) {
        if (user == null || accommodations.isEmpty()) {
            return;
        }

        accommodations.forEach(user::addAccommodation);
        entityManager.merge(user);
    }
}
