package pl.edu.agh.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

	public static volatile SingularAttribute<Role, String> role;
	public static volatile SingularAttribute<Role, Long> id;
	public static volatile SingularAttribute<Role, String> login;

	public static final String ROLE = "role";
	public static final String ID = "id";
	public static final String LOGIN = "login";

}

