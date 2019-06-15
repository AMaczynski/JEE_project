package pl.edu.agh.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "courses")
public class CourseDao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double prize;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryDao category;

    @Column
    private long counter;

    @Column(nullable = false)
    private String size;

    @Column
    private Boolean isApproved;

    @Column
    private boolean isArchived;

    @Column
    private int ordered = 0;

}


