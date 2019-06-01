package pl.edu.agh.datamodel;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue
    private long id;

    private String login;
    private String role;
}
