package com.example.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "car", indexes = {
        @Index(columnList = "user_id", name = "car_index_user_id"),
        @Index(columnList = "licence_number", name = "car_index_licence_number")
})
@EqualsAndHashCode(of = {"licenceNumber"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of")
@Getter
@Setter
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Size(max = 50)
    @Column(name = "licence_number", length = 50, nullable = false, unique = true)
    @NonNull
    private String licenceNumber;
}
