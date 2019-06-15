package pl.edu.agh.datamodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    private long id;

    private String name;
    private double prize;

    private Category category;

    private long counter;

    private String size;

    private Boolean isApproved;

    private boolean isArchived;

    private int ordered = 0;

}


