package com.example.dao;

import com.example.model.Accommodation;
import com.example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.*;

import static com.example.TestObjectFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccommodationDaoImplTest {

    @Autowired
    private AccommodationDaoImpl accommodationDaoImpl;

    @InjectMocks
    private AccommodationDaoImpl accommodationDaoInjectMocks;

    @Mock
    private EntityManager entityManager;

    @Test
    public void shouldReturnListOfAccommodationsWithMatchingStreet() {
        //given
        Accommodation accommodation = createRandomAccommodation();
        accommodation.setStreet("Legnicka");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = createRandomAccommodation();
        accommodation2.setStreet("Legnick");
        accommodationDaoImpl.save(accommodation2);
        Accommodation accommodation3 = createRandomAccommodation();
        accommodation3.setStreet("Legnickaa");
        accommodationDaoImpl.save(accommodation3);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations("Legnicka", null, null, null);

        //then
        assertThat(resultList).containsOnly(accommodation);
    }

    @Test
    public void shouldReturnListOfAccommodationsWithMatchingStreetNumber() {
        //given
        Accommodation accommodation = createRandomAccommodation();
        accommodation.setStreetNumber("30");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = createRandomAccommodation();
        accommodation2.setStreetNumber("3");
        accommodationDaoImpl.save(accommodation2);
        Accommodation accommodation3 = createRandomAccommodation();
        accommodation3.setStreetNumber("303");
        accommodationDaoImpl.save(accommodation3);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations(null, "30", null, null);

        //then
        assertThat(resultList).containsOnly(accommodation);
    }

    @Test
    public void shouldReturnListOfAccommodationsWithMatchingHouseNumber() {
        //given
        Accommodation accommodation = createRandomAccommodation();
        accommodation.setHouseNumber("10");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = createRandomAccommodation();
        accommodation2.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation2);
        Accommodation accommodation3 = createRandomAccommodation();
        accommodation3.setHouseNumber("101");
        accommodationDaoImpl.save(accommodation3);
        Accommodation accommodation4 = createRandomAccommodation();
        accommodation4.setHouseNumber(null);
        accommodationDaoImpl.save(accommodation4);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations(null, null, "10", null);

        //then
        assertThat(resultList).containsOnly(accommodation);
    }

    @Test
    public void shouldReturnListOfAccommodationsWithMatchingCity() {
        //given
        Accommodation accommodation = createRandomAccommodation();
        accommodation.setCity("Wrocław");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = createRandomAccommodation();
        accommodation2.setCity("Wroc");
        accommodationDaoImpl.save(accommodation2);
        Accommodation accommodation3 = createRandomAccommodation();
        accommodation3.setCity("Wrocławw");
        accommodationDaoImpl.save(accommodation3);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations(null, null, null, "Wrocław");

        //then
        assertThat(resultList).containsOnly(accommodation);
    }

    @Test
    public void shouldReturnEmptyListOfAccommodationsWhenStreetMatchesOnlyPartly() {
        //given
        Accommodation accommodation = createRandomAccommodation();
        accommodation.setStreet("L");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = createRandomAccommodation();
        accommodation2.setStreet("Legnicka");
        accommodationDaoImpl.save(accommodation2);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations("Leg", null, null, null);

        //then
        assertThat(resultList).isEmpty();
    }

    @Test
    public void shouldReturnEmptyListOfAccommodationsWhenStreetNumberMatchesOnlyPartly() {
        //given
        Accommodation accommodation = createRandomAccommodation();
        accommodation.setStreetNumber("1");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = createRandomAccommodation();
        accommodation2.setStreetNumber("100");
        accommodationDaoImpl.save(accommodation2);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations(null, "10", null, null);

        //then
        assertThat(resultList).isEmpty();
    }

    @Test
    public void shouldReturnEmptyListOfAccommodationsWhenHouseNumberMatchesOnlyPartly() {
        //given
        Accommodation accommodation = createRandomAccommodation();
        accommodation.setHouseNumber("13");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = createRandomAccommodation();
        accommodation2.setHouseNumber("33");
        accommodationDaoImpl.save(accommodation2);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations(null, null, "3", null);

        //then
        assertThat(resultList).isEmpty();
    }

    @Test
    public void shouldReturnEmptyListOfAccommodationsWhenCityMatchesOnlyPartly() {
        //given
        Accommodation accommodation = createRandomAccommodation();
        accommodation.setCity("Wrocław");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = createRandomAccommodation();
        accommodation2.setCity("Włocławek");
        accommodationDaoImpl.save(accommodation2);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations(null, null, null, "cł");

        //then
        assertThat(resultList).isEmpty();
    }


    @Test
    public void shouldReturnListWithAccommodationWhenAllParametersMatch() {
        //given
        Accommodation accommodation = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Wrocław");
        accommodation.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Wrocław");
        accommodation2.setHouseNumber("2");
        accommodationDaoImpl.save(accommodation2);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations("Legnicka", "1", "1", "Wrocław");

        //then
        assertThat(resultList).containsOnly(accommodation);
    }

    @Test
    public void ShouldReturnEmptyListBecauseStreetParameterDoesNotMatch() {
        //given
        Accommodation accommodation = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Wrocław");
        accommodation.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations("StreetDoesNotMatch", "1", "1", "Wrocław");

        //then
        assertThat(resultList).isEmpty();
    }

    @Test
    public void ShouldReturnEmptyListBecauseStreetNumberDoesNotMatch() {
        //given
        Accommodation accommodation = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Wrocław");
        accommodation.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations("Legnicka", "StreetNumberDoesNotMatch", "1", "Wrocław");

        //then
        assertThat(resultList).isEmpty();

    }

    @Test
    public void ShouldReturnEmptyListBecauseHouseNumberDoesNotMatch() {
        //given
        Accommodation accommodation = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Wrocław");
        accommodation.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations("Legnicka", "1", "HouseNumberDoesNotMatch", "Wrocław");

        //then
        assertThat(resultList).isEmpty();
    }

    @Test
    public void ShouldReturnEmptyListBecauseCityDoesNotMatch() {
        //given
        Accommodation accommodation = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Wrocław");
        accommodation.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations("Legnicka", "1", "1", "CityDoesNotMatch");

        //then
        assertThat(resultList).isEmpty();
    }

    @Test
    public void shouldReturnListOfAccommodationsWhenStreetIsNUll() {
        //given
        Accommodation accommodation = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Wrocław");
        accommodation.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = Accommodation.of("Popowicka", "1", createRandomPostcode(), "Wrocław");
        accommodation2.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation2);
        Accommodation accommodation3 = Accommodation.of("Popowicka", "1", createRandomPostcode(), "Wrocław");
        accommodation3.setHouseNumber("2");
        accommodationDaoImpl.save(accommodation3);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations(null, "1", "1", "Wrocław");

        //then
        assertThat(resultList).containsOnly(accommodation, accommodation2);
    }

    @Test
    public void shouldReturnListOfAccommodationsWhenStreetNumberIsNull() {
        //given
        Accommodation accommodation = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Wrocław");
        accommodation.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = Accommodation.of("Legnicka", "2", createRandomPostcode(), "Wrocław");
        accommodation2.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation2);
        Accommodation accommodation3 = Accommodation.of("Popowicka", "1", createRandomPostcode(), "Wrocław");
        accommodation3.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation3);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations("Legnicka", null, "1", "Wrocław");

        //then
        assertThat(resultList).containsOnly(accommodation, accommodation2);
    }

    @Test
    public void shouldReturnListOfAccommodationsWhenHouseNumberIsNUll() {
        //given
        Accommodation accommodation = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Wrocław");
        accommodation.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Wrocław");
        accommodation2.setHouseNumber("2");
        accommodationDaoImpl.save(accommodation2);
        Accommodation accommodation3 = Accommodation.of("Popowicka", "1", createRandomPostcode(), "Wrocław");
        accommodation3.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation3);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations("Legnicka", "1", null, "Wrocław");

        //then
        assertThat(resultList).containsOnly(accommodation, accommodation2);

    }

    @Test
    public void shouldReturnListOfAccommodationsWhenCityIsNUll() {
        //given
        Accommodation accommodation = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Wrocław");
        accommodation.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = Accommodation.of("Legnicka", "1", createRandomPostcode(), "Szczecin");
        accommodation2.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation2);
        Accommodation accommodation3 = Accommodation.of("Popowicka", "1", createRandomPostcode(), "Wrocław");
        accommodation3.setHouseNumber("1");
        accommodationDaoImpl.save(accommodation3);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations("Legnicka", "1", "1", null);

        //then
        assertThat(resultList).containsOnly(accommodation, accommodation2);
    }

    @Test
    public void shouldReturnListOfAccommodationsWhenAllParametersAreNulls() {
        //given
        Accommodation accommodation = createRandomAccommodation();
        accommodationDaoImpl.save(accommodation);
        Accommodation accommodation2 = createRandomAccommodation();
        accommodationDaoImpl.save(accommodation2);
        Accommodation accommodation3 = createRandomAccommodation();
        accommodationDaoImpl.save(accommodation3);

        //when
        List<Accommodation> resultList = accommodationDaoImpl.findAccommodations(null, null, null, null);

        //then
        assertThat(resultList).containsOnly(accommodation, accommodation2, accommodation3);
    }

    @Test
    public void shouldNotMergeUserWhenAccommodationListIsEmpty() {
        //given
        List<Accommodation> accommodations = new ArrayList<>();
        User user = createRandomUser();

        //when
        accommodationDaoInjectMocks.bindAccommodationToUser(accommodations, user);

        //then
        verify(entityManager, never()).merge(user);
    }

    @Test
    public void shouldNotMergeUserWhenUserIsNull() {
        //given
        List<Accommodation> accommodations = new ArrayList<>();
        accommodations.add(createRandomAccommodation());
        accommodations.add(createRandomAccommodation());
        User user = null;

        //when
        accommodationDaoInjectMocks.bindAccommodationToUser(accommodations, user);

        //then
        verify(entityManager, never()).merge(user);
    }

    @Test
    public void shouldMergeUserWhenAccommodationListIsNotEmptyAndUserIsNotNull() {
        //given
        List<Accommodation> accommodations = new ArrayList<>();
        accommodations.add(createRandomAccommodation());
        accommodations.add(createRandomAccommodation());
        User user = createRandomUser();

        //when
        accommodationDaoInjectMocks.bindAccommodationToUser(accommodations, user);

        //then
        verify(entityManager).merge(user);
    }

    @Test
    public void shouldReturnAccommodationsThatBelongToUser() {
        //given
        List<Accommodation> accommodations = new ArrayList<>();
        List<Accommodation> accommodations2 = new ArrayList<>();
        Accommodation accommodation = createRandomAccommodation();
        Accommodation accommodation2 = createRandomAccommodation();
        Accommodation accommodation3 = createRandomAccommodation();
        accommodations.add(accommodation);
        accommodations.add(accommodation2);
        accommodations2.add(accommodation3);

        User user = createRandomUser();
        User user2 = createRandomUser();


        //when
        accommodationDaoInjectMocks.bindAccommodationToUser(accommodations, user);
        accommodationDaoInjectMocks.bindAccommodationToUser(accommodations2, user2);
        Set<Accommodation> resultAccommodations = user.getAccommodations();

        //then
        assertThat(resultAccommodations).containsOnly(accommodation, accommodation2);
    }
}
