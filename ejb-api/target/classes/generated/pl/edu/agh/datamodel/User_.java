package pl.edu.agh.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Address> address;
	public static volatile SingularAttribute<User, Integer> role;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> login;

	public static final String PASSWORD = "password";
	public static final String ADDRESS = "address";
	public static final String ROLE = "role";
	public static final String ID = "id";
	public static final String LOGIN = "login";

}

