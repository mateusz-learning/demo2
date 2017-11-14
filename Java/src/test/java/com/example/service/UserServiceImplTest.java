package com.example.service;

import com.example.dao.AccommodationDaoImpl;
import com.example.dao.UserDaoImpl;
import com.example.model.Accommodation;
import com.example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static com.example.TestObjectFactory.createRandomPostcode;
import static com.example.TestObjectFactory.createRandomUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceImplTest {

    @Autowired
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserDaoImpl userDaoImpl;

    @Mock
    private AccommodationDaoImpl accommodationDaoImpl;

    @Test
    public void shouldReturnStringWithErrorMessageWhenUserDoesNotExist() {
        //when
        String stringToDisplay = userServiceImpl.displayUser("non-existing-email@example.com");

        //then
        assertThat(stringToDisplay).isEqualTo("User doesn't exist.");
    }

    @Test
    public void shouldReturnStringWithUserAndWithoutAccommodations() {
        //given
        User user = createRandomUser();
        user.setUserName("jacek");

        //when
        when(userDaoImpl.findUser(any())).thenReturn(Optional.ofNullable(user));

        //then
        assertThat(userServiceImpl.displayUser(anyString())).isEqualTo("User name: jacek\n" +
                "---\n" +
                "Address: No accommodations to dispaly.");
    }

    @Test
    public void shouldReturnStringWithUserAndHisOneAccommodation() {
        //given
        User user = createRandomUser();
        user.setUserName("jacek");

        Accommodation accommodation = Accommodation.of("Legnicka", "5", createRandomPostcode(), "Wrocław");
        accommodation.setHouseNumber("10");
        user.addAccommodation(accommodation);

        //when
        when(userDaoImpl.findUser(any())).thenReturn(Optional.ofNullable(user));

        //then
        assertThat(userServiceImpl.displayUser(anyString())).isEqualTo("User name: jacek\n" +
                "---\n" +
                "Address: Legnicka 5/10 Wrocław");
    }

    @Test
    public void shouldReturnStringWithUserAndHisTwoAccommodations() {
        //given
        User user = createRandomUser();
        user.setUserName("jacek");

        Accommodation accommodation = Accommodation.of("Popowicka", "13", createRandomPostcode(), "Wrocław");
        accommodation.setHouseNumber("8");
        user.addAccommodation(accommodation);

        Accommodation accommodation2 = Accommodation.of("Street", "5", createRandomPostcode(), "Wrocław");
        accommodation2.setHouseNumber("10");
        accommodation2.setStreet("Street");

        user.addAccommodation(accommodation2);

        //when
        when(userDaoImpl.findUser(any())).thenReturn(Optional.ofNullable(user));

        //then
        String expectedString = userServiceImpl.displayUser(anyString());
        assertThat(expectedString).contains("Popowicka 13/8 Wrocław", "Street 5/10 Wrocław");
        assertThat(expectedString).contains("Street 5/10 Wrocław", "Popowicka 13/8 Wrocław");
    }
}
