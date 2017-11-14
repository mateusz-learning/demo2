package com.example.model;

import com.example.enums.Gender;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SetAttribute<User, Car> cars;
	public static volatile SingularAttribute<User, Integer> numberOfChildren;
	public static volatile SingularAttribute<User, Gender> gender;
	public static volatile SetAttribute<User, Accommodation> accommodations;
	public static volatile SingularAttribute<User, LocalDate> dateOfBirth;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> userName;
	public static volatile SingularAttribute<User, Boolean> hasInsurance;
	public static volatile SingularAttribute<User, String> email;

}

