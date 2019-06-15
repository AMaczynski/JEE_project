package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.api.IScheduleService;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ManagedBean(name = "JMSS")
@SessionScoped
public class JMSS {
    @Inject
    private JMSContext context;
    @Resource(mappedName = "java:jboss/exported/jms/queue/SOA")
    Queue myQueue;

    List<JmsMsg> messages = new ArrayList<>();

    @EJB(lookup = "java:global/core/ScheduleService")
    private IScheduleService scheduleService;

    public String receiveMessage() {
        Message received = context.createConsumer(myQueue).receive(1000);
        String message = null;
        System.out.println("Receiving");
        if (received == null)
            message = "Nic nie ma w kolejce";
        else {
            try {
                message = received.getBody(String.class);
                JmsMsg newMessage = new JmsMsg();
                newMessage.setDate(new Date());
                newMessage.setMessage(message);
                messages.add(newMessage);
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("You've got a message", message));

            } catch (JMSException e) {
                System.out.println("E");
            }
        }
        System.out.println(message);

        return message;
    }
}


