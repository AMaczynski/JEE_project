package pl.edu.agh.webapp;

import lombok.Setter;
import pl.edu.agh.api.IJMSSender;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@ManagedBean(name = "JMS")
@ViewScoped
public class JMS {

    @EJB(lookup = "java:global/core/JMSSender")
    private IJMSSender jmsSender;

    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:jboss/exported/jms/queue/SOA")
    private Queue queue;

    @Setter
    private String message;

    public void sendMessage() {
        jmsSender.sendMessage("Hello bitches", 1);
    }

    public String receiveMessage() {
        String message = context.createConsumer(queue).receiveBody(String.class);
        if (message == null)
            message = "pusto w kolejce";
        return message;
    }
}


