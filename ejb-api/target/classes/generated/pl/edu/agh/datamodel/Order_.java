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
	public static volatile SingularAttribute<Order, Address> address;
	public static volatile ListAttribute<Order, Course> course;
	public static volatile SingularAttribute<Order, Long> id;
	public static volatile SingularAttribute<Order, User> user;
	public static volatile SingularAttribute<Order, Integer> status;

	public static final String DATE = "date";
	public static final String ADDRESS = "address";
	public static final String COURSE = "course";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String STATUS = "status";

}

