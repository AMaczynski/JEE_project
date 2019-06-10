package pl.edu.agh.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MessageReceiver implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = null;
        try {
            if (message instanceof TextMessage) {
                long courseId = message.getLongProperty("course_id");
                textMessage = (TextMessage) message;
                String text = textMessage.getText();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
