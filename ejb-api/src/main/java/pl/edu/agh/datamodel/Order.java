package pl.edu.agh.datamodel;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    private List<Course> courses;

    @Column
    private Date date;

    @ManyToOne
    private User user;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private int buildingNumber;

    @Column
    private int apartmentNumber;

    @ManyToOne
    private Schedule schedule;
}
