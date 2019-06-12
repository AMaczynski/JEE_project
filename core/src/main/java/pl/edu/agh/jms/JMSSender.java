package pl.edu.agh.jms;

import pl.edu.agh.api.IJMSSender;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Queue;

@Stateless
@Remote(IJMSSender.class)
public class JMSSender implements IJMSSender {

    @Resource(mappedName = "java:jboss/exported/jms/queue/SOA")
    private Queue queue;

    @Inject
    JMSContext context;

    public void sendMessage(String message) {
        try {
            Message msg = context.createMessage();
            context.createProducer().send(queue, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
