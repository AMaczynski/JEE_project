package pl.edu.agh.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserDao implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String password;

    @OneToOne
    private AddressDao address;

    private int role = 0;
}

