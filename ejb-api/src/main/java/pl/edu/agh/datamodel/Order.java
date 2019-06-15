package pl.edu.agh.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private long id;

    private List<Course> course;

    private Date date;

    private User user;

    private Address address;

    private int status = 0;
}
