package pl.edu.agh.webapp;

import pl.edu.agh.api.IScheduleService;
import pl.edu.agh.datamodel.Course;
import pl.edu.agh.datamodel.Schedule;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

public class JMS {

}

