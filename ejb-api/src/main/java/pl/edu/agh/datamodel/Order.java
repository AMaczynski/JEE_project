package pl.edu.agh.datamodel;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Course course;

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
    private int roomNumber;

    @ManyToOne
    private Schedule schedule;
}
