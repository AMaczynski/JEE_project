package pl.edu.agh.webapp;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class JmsMsg {
    private Date date;
    private String message;
}
