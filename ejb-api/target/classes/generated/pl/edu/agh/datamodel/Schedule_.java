package pl.edu.agh.datamodel;

import java.time.DayOfWeek;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Schedule.class)
public abstract class Schedule_ {

	public static volatile SingularAttribute<Schedule, Address> address;
	public static volatile SingularAttribute<Schedule, DayOfWeek> dayOfWeek;
	public static volatile ListAttribute<Schedule, Course> course;
	public static volatile SingularAttribute<Schedule, Long> id;
	public static volatile SingularAttribute<Schedule, User> user;

	public static final String ADDRESS = "address";
	public static final String DAY_OF_WEEK = "dayOfWeek";
	public static final String COURSE = "course";
	public static final String ID = "id";
	public static final String USER = "user";

}

