package pl.edu.agh.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Course.class)
public abstract class Course_ {

	public static volatile SingularAttribute<Course, Integer> ordered;
	public static volatile SingularAttribute<Course, String> size;
	public static volatile SingularAttribute<Course, Boolean> isArchived;
	public static volatile SingularAttribute<Course, String> name;
	public static volatile SingularAttribute<Course, Long> id;
	public static volatile SingularAttribute<Course, Category> category;
	public static volatile SingularAttribute<Course, Boolean> isApproved;
	public static volatile SingularAttribute<Course, Double> prize;

	public static final String ORDERED = "ordered";
	public static final String SIZE = "size";
	public static final String IS_ARCHIVED = "isArchived";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String IS_APPROVED = "isApproved";
	public static final String PRIZE = "prize";

}

