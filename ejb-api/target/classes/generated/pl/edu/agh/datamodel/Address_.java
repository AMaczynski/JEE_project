package pl.edu.agh.datamodel;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> street;
	public static volatile SingularAttribute<Address, Integer> buildingNumber;
	public static volatile SingularAttribute<Address, Long> id;
	public static volatile SingularAttribute<Address, Integer> apartmentNumber;

	public static final String CITY = "city";
	public static final String STREET = "street";
	public static final String BUILDING_NUMBER = "buildingNumber";
	public static final String ID = "id";
	public static final String APARTMENT_NUMBER = "apartmentNumber";

}

