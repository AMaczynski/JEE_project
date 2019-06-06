package pl.edu.agh.datamodel;

import java.sql.Time;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Schedule.class)
public abstract class Schedule_ {

	public static volatile SingularAttribute<Schedule, Long> id;
	public static volatile SingularAttribute<Schedule, Time> time;

	public static final String ID = "id";
	public static final String TIME = "time";

}

