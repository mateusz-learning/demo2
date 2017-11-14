package com.example.dao;

import com.example.model.Car;
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
import java.util.Optional;
import java.util.Set;

import static com.example.TestObjectFactory.createRandomCar;
import static com.example.TestObjectFactory.createRandomUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CarDaoImplTest {

    @Autowired
    private CarDaoImpl carDaoImpl;

    @InjectMocks
    private CarDaoImpl carDaoInjectMocks;

    @Mock
    private EntityManager entityManager;

    @Test
    public void shouldReturnerEmptyOptionalWhenLicenceNumberIsNull() {
        //when
        Optional<Car> resultCar = carDaoImpl.findCar(null);

        //then
        assertThat(resultCar).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenLicenceNumberIsEmpty() {
        //when
        Optional<Car> resultCar = carDaoImpl.findCar("");

        //then
        assertThat(resultCar).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnEmptyOptionalWhenSearchedLicenceNumberIsNotPresent() {
        //given
        Car car = createRandomCar();
        carDaoImpl.save(car);
        Car car2 = createRandomCar();
        carDaoImpl.save(car2);

        //when
        Optional<Car> resultCar = carDaoImpl.findCar("licenceNumberIsNotPresent");

        //then
        assertThat(resultCar).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnProperCarWhenSearchedLicenceNumberIsPresent() {
        //given
        Car car = createRandomCar();
        carDaoImpl.save(car);

        //when
        Car searchedCar = carDaoImpl.findCar(car.getLicenceNumber()).get();

        //then
        assertThat(car).isEqualTo(searchedCar);
    }

    @Test
    public void shouldReturnProperCarWhenGivenLicenceNumberHasMixedCaseLetters() {
        //given
        Car car = createRandomCar();
        car.setLicenceNumber("ABCDE12345");
        carDaoImpl.save(car);

        //when
        Car searchedCarLowerCase = carDaoImpl.findCar("abcde12345").get();
        Car searchedCarUpperCase = carDaoImpl.findCar("ABCDE12345").get();
        Car searchedCarMixedCase = carDaoImpl.findCar("aBcDe12345").get();

        //then
        assertThat(car).isEqualTo(searchedCarLowerCase).isEqualTo(searchedCarUpperCase).isEqualTo(searchedCarMixedCase);
    }

    @Test
    public void shouldNotMergeUserWhenCarIsNull() {
        //given
        Car car = null;
        User user = createRandomUser();

        //when
        carDaoInjectMocks.assignCarToUser(car, user);

        //then
        verify(entityManager, never()).merge(user);
    }

    @Test
    public void shouldNotMergeUserWhenUserIsNull() {
        //given
        Car car = createRandomCar();
        User user = null;

        //when
        carDaoInjectMocks.assignCarToUser(car, user);

        //then
        verify(entityManager, never()).merge(user);
    }

    @Test
    public void shouldNotMergeUserWhenUserAndCarAreNulls() {
        //given
        Car car = null;
        User user = null;

        //when
        carDaoInjectMocks.assignCarToUser(car, user);

        //then
        verify(entityManager, never()).merge(user);
    }

    @Test
    public void shouldMergeUserWhenUserAndCarAreNotNulls() {
        //given
        Car car = createRandomCar();
        User user = createRandomUser();

        //when
        carDaoInjectMocks.assignCarToUser(car, user);

        //then
        verify(entityManager).merge(user);
    }

    @Test
    public void shouldReturnCarThatBelongsToUser() {
        //given
        Car car = createRandomCar();
        Car car2 = createRandomCar();
        Car car3 = createRandomCar();
        User user = createRandomUser();
        User user2 = createRandomUser();

        //when
        carDaoInjectMocks.assignCarToUser(car, user);
        carDaoInjectMocks.assignCarToUser(car2, user);
        carDaoInjectMocks.assignCarToUser(car3, user2);
        Set<Car> resultCars = user.getCars();

        //then
        assertThat(resultCars).containsOnly(car, car2);
    }
}
