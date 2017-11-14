package com.example.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

@Entity
@Table(name = "accommodation", indexes = {
        @Index(columnList = "street", name = "accommodation_index_street"),
        @Index(columnList = "street_number", name = "accommodation_index_street_number"),
        @Index(columnList = "house_number", name = "accommodation_index_house_number"),
        @Index(columnList = "city", name = "accommodation_index_city")
})
@EqualsAndHashCode(of = {"street", "streetNumber", "houseNumber", "city"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
@Getter
@Setter
public class Accommodation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "street", length = 255, nullable = false)
    @lombok.NonNull private String street;

    @NotBlank
    @Size(max = 50)
    @Column(name = "street_number", length = 50, nullable = false)
    @NonNull
    private String streetNumber;

    @Size(max = 50)
    @Column(name = "house_number", length = 50, nullable = true)
    private String houseNumber;

    @NotBlank
    @Size(max = 50)
    @Column(name = "postcode", length = 50, nullable = false)
    @NonNull
    private String postcode;

    @NotBlank
    @Size(max = 255)
    @Column(name = "city", length = 255, nullable = false)
    @NonNull
    private String city;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usr_accommodation",
            joinColumns = { @JoinColumn(name = "accommodation_id")},
            inverseJoinColumns = { @JoinColumn(name = "user_id")}, indexes = {
            @Index(columnList = "user_id", name = "usr_accommodation_index_user_id")
    }
    )
    private Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return unmodifiableSet(users);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User usr) {
        users.remove(usr);
    }
}
