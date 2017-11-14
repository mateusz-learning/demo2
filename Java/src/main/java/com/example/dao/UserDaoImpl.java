package com.example.dao;

import com.example.enums.Gender;
import com.example.model.User;
import com.example.model.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(final User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public Optional<User> findUser(final String email) {
        Query query = entityManager.createQuery("SELECT u from User u WHERE lower(u.email) = lower(:email)")
                .setParameter("email", email);

        try {
            return Optional.ofNullable((User) query.getSingleResult());
        } catch(NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public List<User> findUsersByGender(final Gender gender) {
        Query query = entityManager.createQuery("SELECT u from User u WHERE u.gender = :gender")
                .setParameter("gender", gender);

        return query.getResultList();
    }

    @Override
    @Transactional
    public List<User> findUsers(final LocalDate minimumDate, final LocalDate maximumDate, final String stringToContain) {
        if (minimumDate != null && maximumDate != null && minimumDate.isAfter(maximumDate)) {
            return new ArrayList<>();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> user = criteriaQuery.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        Optional.ofNullable(minimumDate)
                .map(value -> criteriaBuilder.greaterThanOrEqualTo(user.get(User_.dateOfBirth), value))
                .ifPresent(predicates::add);

        Optional.ofNullable(maximumDate)
                .map(value -> criteriaBuilder.lessThanOrEqualTo(user.get(User_.dateOfBirth), value))
                .ifPresent(predicates::add);

        Optional.ofNullable(stringToContain)
                .map(value -> criteriaBuilder.like(criteriaBuilder.lower(user.get(User_.userName)), "%" + value.toLowerCase() + "%"))
                .ifPresent(predicates::add);

        criteriaQuery.where(
                criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
        )
                .select(user);

        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
