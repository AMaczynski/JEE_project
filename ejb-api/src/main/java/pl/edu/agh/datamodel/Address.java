package pl.edu.agh.datamodel;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@Data
public class Address {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private int buildingNumber;

    @Column
    private int roomNumber;
}
