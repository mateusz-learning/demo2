package com.example.dao;

import com.example.enums.Gender;
import com.example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.TestObjectFactory.createRandomUser;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDaoImplTest {

    @Autowired
    private UserDaoImpl userDaoImpl;

    @Test
    public void shouldReturnProperUserWhenGivenEmailExists() {
        //given
        User user = createRandomUser();

        //when
        userDaoImpl.save(user);
        Optional<User> result = userDaoImpl.findUser(user.getEmail());

        //then
        assertThat(result).isEqualTo(Optional.ofNullable(user));
    }

    @Test
    public void shouldReturnEmptyOptionalWhenGivenEmailDoesNotMatch() {
        //when
        Optional<User> result = userDaoImpl.findUser("non-existing-email@example.com");

        //then
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnListOfUsersWithGivenGender() {
        //given
        User user = createRandomUser();
        user.setGender(Gender.MALE);
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setGender(Gender.MALE);
        userDaoImpl.save(user2);
        User user3 = createRandomUser();
        user3.setGender(Gender.FEMALE);
        userDaoImpl.save(user3);
        User user4 = createRandomUser();
        user4.setGender(Gender.FEMALE);
        userDaoImpl.save(user4);

        //when
        List<User> resultList = userDaoImpl.findUsersByGender(Gender.FEMALE);

        //then
        assertThat(resultList).containsOnly(user3, user4);
    }

    @Test
    public void shouldReturnListOfUsersWhenAllParametersAreGiven() {
        //given
        User user = createRandomUser();
        user.setUserName("Grzesiek");
        user.setDateOfBirth(LocalDate.of(1950, 5, 12));
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setUserName("Grażyna");
        user2.setDateOfBirth(LocalDate.of(1960, 10, 10));
        userDaoImpl.save(user2);
        User user3 = createRandomUser();
        user3.setUserName("Gerwazy");
        user3.setDateOfBirth(LocalDate.of(1970, 12, 8));
        userDaoImpl.save(user3);
        User user4 = createRandomUser();
        user4.setUserName("Ania");
        user4.setDateOfBirth(LocalDate.of(1980, 10, 7));
        userDaoImpl.save(user4);
        User user5 = createRandomUser();
        user5.setUserName("Justyna");
        user5.setDateOfBirth(LocalDate.of(1965, 6, 8));
        userDaoImpl.save(user5);

        //when
        List<User> resultList = userDaoImpl.findUsers(LocalDate.of(1955, 1, 1), LocalDate.of(1985, 1, 1), "g");

        //then
        assertThat(resultList).containsOnly(user2, user3);
    }

    @Test
    public void shouldReturnListOfUsersWhenStringParameterContainsLowerCaseAndUpperCaseLetters() {
        //given
        User user = createRandomUser();
        user.setUserName("checkNaMeCase");
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setUserName("checknamecase");
        userDaoImpl.save(user2);
        User user3 = createRandomUser();
        user3.setUserName("checkNAMEcase");
        userDaoImpl.save(user3);
        User user4 = createRandomUser();
        user4.setUserName("Andrzej");
        userDaoImpl.save(user4);

        //when
        List<User> resultListAllLowerCase = userDaoImpl.findUsers(null, null, "name");
        List<User> resultListAllUpperCase = userDaoImpl.findUsers(null, null, "NAME");
        List<User> resultListMixedCase = userDaoImpl.findUsers(null, null, "nAMe");

        //then
        assertThat(resultListAllLowerCase).containsOnly(user, user2, user3);
        assertThat(resultListAllUpperCase).containsOnly(user, user2, user3);
        assertThat(resultListMixedCase).containsOnly(user, user2, user3);
    }

    @Test
    public void shouldReturnListOfUsersWhenMinimumDateIsNull() {
        //given
        User user = createRandomUser();
        user.setDateOfBirth(LocalDate.of(1970, 2, 3));
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setDateOfBirth(LocalDate.of(1980, 5, 8));
        userDaoImpl.save(user2);

        //when
        List<User> resultList = userDaoImpl.findUsers(null, LocalDate.of(1975, 2, 3), "");

        //then
        assertThat(resultList).containsOnly(user);
    }

    @Test
    public void shouldReturnListOfUsersWhenMinimumDateIsEdgeCase() {
        //given
        User user = createRandomUser();
        user.setDateOfBirth(LocalDate.of(1970, 2, 3));
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setDateOfBirth(LocalDate.of(1980, 5, 8));
        userDaoImpl.save(user2);
        User user3 = createRandomUser();
        user3.setDateOfBirth(LocalDate.of(1990, 1, 1));
        userDaoImpl.save(user3);

        //when
        List<User> resultList = userDaoImpl.findUsers(LocalDate.of(1980, 5, 8), LocalDate.of(2000, 2, 3), "");

        //then
        assertThat(resultList).containsOnly(user2, user3);
    }

    @Test
    public void shouldReturnListOfUsersWhenMaximumDateIsNull() {
        //given
        User user = createRandomUser();
        user.setDateOfBirth(LocalDate.of(1970, 2, 3));
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setDateOfBirth(LocalDate.of(1980, 5, 8));
        userDaoImpl.save(user2);

        //when
        List<User> resultList = userDaoImpl.findUsers(LocalDate.of(1975, 1, 1), null, "");

        //then
        assertThat(resultList).containsOnly(user2);
    }

    @Test
    public void shouldReturnListOfUsersWhenMaximumDateIsEdgeCase() {
        //given
        User user = createRandomUser();
        user.setDateOfBirth(LocalDate.of(1950, 1, 1));
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setDateOfBirth(LocalDate.of(1965, 11, 30));
        userDaoImpl.save(user2);
        User user3 = createRandomUser();
        user3.setDateOfBirth(LocalDate.of(1970, 1, 1));

        //when
        List<User> resultList = userDaoImpl.findUsers(LocalDate.of(1940, 1, 1), LocalDate.of(1965, 11, 30), "");

        //then
        assertThat(resultList).containsOnly(user, user2);
    }

    @Test
    public void shouldReturnListOfUsersWhenMinimumDateAndMaximumDateAreNulls() {
        //given
        User user = createRandomUser();
        user.setDateOfBirth(LocalDate.of(1950, 1, 1));
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setDateOfBirth(LocalDate.of(1960, 1, 1));
        userDaoImpl.save(user2);

        //when
        List<User> resultList = userDaoImpl.findUsers(null, null, "");

        //then
        assertThat(resultList).containsOnly(user, user2);
    }

    @Test
    public void shouldReturnListOfUsersWhenMinimumDateIsGreaterThanMaximumDate() {
        //given
        User user = createRandomUser();
        user.setDateOfBirth(LocalDate.of(1960, 1, 1));
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userDaoImpl.save(user2);

        //when
        List<User> resultList = userDaoImpl.findUsers(LocalDate.of(1980, 1, 1), LocalDate.of(1970, 1, 1), "");

        //then
        assertThat(resultList).isEmpty();
    }

    @Test
    public void shouldReturnListOfUsersWhenGivenStringIsEmpty() {
        //given
        User user = createRandomUser();
        user.setUserName("Andrzej");
        user.setDateOfBirth(LocalDate.of(1960, 1, 1));
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setUserName("Basia");
        user2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userDaoImpl.save(user2);

        //when
        List<User> resultList = userDaoImpl.findUsers(LocalDate.of(1950, 1, 1), LocalDate.of(1980, 1, 1), "");

        //then
        assertThat(resultList).containsOnly(user, user2);
    }

    @Test
    public void shouldReturnListOfUsersWhenGivenStringIsNull() {
        //given
        User user = createRandomUser();
        user.setUserName("Andrzej");
        user.setDateOfBirth(LocalDate.of(1960, 1, 1));
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setUserName("Basia");
        user2.setDateOfBirth(LocalDate.of(1970, 1, 1));
        userDaoImpl.save(user2);

        //when
        List<User> resultList = userDaoImpl.findUsers(LocalDate.of(1950, 1, 1), LocalDate.of(1980, 1, 1), null);

        //then
        assertThat(resultList).containsOnly(user, user2);
    }

    @Test
    public void shouldReturnListOfUsersWhenUsernameContainsGivenString() {
        //given
        User user = createRandomUser();
        user.setUserName("Jacek");
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setUserName("Jan");
        userDaoImpl.save(user2);
        User user3 = createRandomUser();
        user3.setUserName("Grzesiek");
        userDaoImpl.save(user3);

        //when
        List<User> resultList = userDaoImpl.findUsers(null, null, "ja");

        //then
        assertThat(resultList).containsOnly(user, user2);
    }

    @Test
    public void shouldReturnListWithUserWhenUsernameIsTheSameAsGivenString() {
        //given
        User user = createRandomUser();
        user.setUserName("Danuta");
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setUserName("Basia");
        userDaoImpl.save(user2);

        //when
        List<User> resultList = userDaoImpl.findUsers(null, null, "danuta");

        //then
        assertThat(resultList).containsOnly(user);
    }

    @Test
    public void shouldReturnListOfUsersWhenAllParametersAreNulls() {
        //given
        User user = createRandomUser();
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        userDaoImpl.save(user2);
        User user3 = createRandomUser();
        userDaoImpl.save(user3);

        //when
        List<User> resultList = userDaoImpl.findUsers(null, null, null);

        //then
        assertThat(resultList).containsOnly(user, user2, user3);
    }

    @Test
    public void shouldReturnEmptyListWhenGivenParametersDoesNotMatchToAnyUser() {
        //given
        User user = createRandomUser();
        user.setDateOfBirth(LocalDate.of(1950, 1, 1));
        userDaoImpl.save(user);
        User user2 = createRandomUser();
        user2.setDateOfBirth(LocalDate.of(1960, 1, 1));
        userDaoImpl.save(user2);

        //when
        List<User> resultList = userDaoImpl.findUsers(LocalDate.of(1970, 1, 1), LocalDate.of(1980, 1, 1), null);

        //then
        assertThat(resultList).isEmpty();
    }
}
