package pl.edu.agh.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "address")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDao implements Serializable {

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
