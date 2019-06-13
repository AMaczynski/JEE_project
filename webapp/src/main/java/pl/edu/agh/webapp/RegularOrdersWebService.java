package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.api.IScheduleService;
import pl.edu.agh.datamodel.Schedule;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ViewScoped
@ManagedBean(name = "RegularOrder")
@Data
public class RegularOrdersWebService {

    @EJB(lookup = "java:global/core/ScheduleService")
    IScheduleService scheduleService;

    @ManagedProperty(value = "#{User}")
    private UserService userService;

    private List<Schedule> schedules;
    private Schedule selectedSchedule;

    @PostConstruct
    private void init() {
        schedules = scheduleService.getSchedulesByUser(userService.getUser().getId());
    }

    public void deleteSchedule() {
        schedules.remove(selectedSchedule);
        scheduleService.deleteSchedule(selectedSchedule.getId());
    }
}

