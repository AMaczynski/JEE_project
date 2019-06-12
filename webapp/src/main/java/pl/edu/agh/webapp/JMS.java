package pl.edu.agh.webapp;

import pl.edu.agh.api.IScheduleService;
import pl.edu.agh.datamodel.Course;
import pl.edu.agh.datamodel.Schedule;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

@MessageDriven(mappedName = "java:jboss/exported/jms/queue/SOA", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/SOA")
})
@ManagedBean(name = "JMS")
@ViewScoped
public class JMS implements MessageListener {

    @EJB(lookup = "java:global/core/ScheduleService")
    private IScheduleService scheduleService;

    @ManagedProperty(value = "#{User}")
    private UserService userService;

    @Override
    public void onMessage(Message inMessage) {
        TextMessage msg = null;
        boolean interestedUser = false;
        Course deletedCourse = null;
        try {
            if (inMessage instanceof TextMessage) {
                msg = (TextMessage) inMessage;
                System.out.println(msg.getText());
                long userId = userService.getUser().getId();
                System.out.println(userId);
                List<Schedule> schedules = scheduleService.getSchedulesByUser(2);
                long courseId = Long.parseLong(msg.getText());
                for (Schedule schedule : schedules) {
                    List<Course> courses = schedule.getCourse();
                    for (Course course : courses) {
                        if (course.getId() == courseId) {
                            interestedUser = true;
                            deletedCourse = course;
                            break;
                        }
                    }
                }
                if (interestedUser) {
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("One of your schedules order has been deleted", deletedCourse.toString()));
                    System.out.println(deletedCourse.toString());
                }
            } else {
                System.out.println("Message of wrong type: " +
                        inMessage.getClass().getName());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

