package pl.edu.agh.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedules")
public class ScheduleDao implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private UserDao user;

    @ManyToOne
    private AddressDao address;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<CourseDao> course;

    @Column
    private String time;

    @Column
    private DayOfWeek dayOfWeek;
}
