package pl.edu.agh.datamodel;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    private long id;

    private User user;

    private Address address;

    private List<Course> course;

    private String time;

    private DayOfWeek dayOfWeek;
}
