package pl.edu.agh.datamodel;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.DayOfWeek;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Address address;

    @ManyToOne
    Course course;

    @Column
    private DayOfWeek dayOfWeek;
}
