package pl.edu.agh.api;

import pl.edu.agh.datamodel.Schedule;

import java.util.List;

public interface IScheduleService {

    void addSchedules(List<Schedule> scheduleList);
}
