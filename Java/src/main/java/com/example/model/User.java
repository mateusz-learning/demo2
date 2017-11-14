package com.example.model;

import com.example.enums.Gender;
import lombok.*;
import org.hibernate.annotations.Check;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static java.util.Collections.unmodifiableSet;

@Entity
@Table(name = "usr", indexes = {
        @Index(columnList = "date_of_birth", name = "usr_index_date_of_birth")
})
@Check(constraints = "gender in('MALE', 'FEMALE')")
@EqualsAndHashCode(of = {"email"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 40)
    @Column(name = "user_name", length = 40, nullable = false)
    @NonNull
    private String userName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    @NonNull
    private Gender gender;

    @NotBlank
    @Column(name = "date_of_birth", nullable = false)
    @NonNull
    private LocalDate dateOfBirth;

    @NotBlank
    @Size(max = 255)
    @Column(name = "email", length = 255, nullable = false, unique = true)
    @NonNull
    private String email;

    @Min(0)
    @Column(name = "number_of_children", nullable = true)
    private Integer numberOfChildren;

    @Column(name = "has_insurance", nullable = true)
    private Boolean hasInsurance;

    @ManyToMany(mappedBy = "users",
            cascade ={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.EAGER)

    private Set<Accommodation> accommodations = new HashSet<>();

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.EAGER)
    private Set<Car> cars = new HashSet<>();

    public Set<Accommodation> getAccommodations() {
        return unmodifiableSet(accommodations);
    }

    public void addAccommodation(Accommodation accommodation) {
        accommodation.addUser(this);
        accommodations.add(accommodation);
    }

    public void removeAccommodation(Accommodation accommodation) {
        accommodation.removeUser(this);
        accommodations.remove(accommodation);
    }
    public Set<Car> getCars() {
        return unmodifiableSet(cars);
    }

    public void addCar(Car car) {
        car.setUser(this);
        cars.add(car);
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }
}
