package pl.edu.agh.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

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
    private int apartmentNumber;
}
