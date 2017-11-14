package com.example.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Accommodation.class)
public abstract class Accommodation_ {

	public static volatile SingularAttribute<Accommodation, String> streetNumber;
	public static volatile SingularAttribute<Accommodation, String> city;
	public static volatile SingularAttribute<Accommodation, String> street;
	public static volatile SingularAttribute<Accommodation, String> houseNumber;
	public static volatile SingularAttribute<Accommodation, String> postcode;
	public static volatile SingularAttribute<Accommodation, Long> id;
	public static volatile SetAttribute<Accommodation, User> users;

}

