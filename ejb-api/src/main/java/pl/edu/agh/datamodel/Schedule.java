package pl.edu.agh.datamodel;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Data
@Table(name = "schedules")
public class Schedule implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private Time time;
}
