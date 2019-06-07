package pl.edu.agh.datamodel;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, Date> date;
	public static volatile ListAttribute<Order, Course> courses;
	public static volatile SingularAttribute<Order, Schedule> schedule;
	public static volatile SingularAttribute<Order, String> city;
	public static volatile SingularAttribute<Order, String> street;
	public static volatile SingularAttribute<Order, Integer> buildingNumber;
	public static volatile SingularAttribute<Order, Long> id;
	public static volatile SingularAttribute<Order, User> user;
	public static volatile SingularAttribute<Order, Integer> apartmentNumber;

	public static final String DATE = "date";
	public static final String COURSES = "courses";
	public static final String SCHEDULE = "schedule";
	public static final String CITY = "city";
	public static final String STREET = "street";
	public static final String BUILDING_NUMBER = "buildingNumber";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String APARTMENT_NUMBER = "apartmentNumber";

}

