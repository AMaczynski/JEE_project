package pl.edu.agh.service;

import pl.edu.agh.api.IScheduleService;
import pl.edu.agh.datamodel.Schedule;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Remote(IScheduleService.class)
public class ScheduleService extends BaseService implements IScheduleService {

    @Override
    public void addSchedules(List<Schedule> scheduleList) {
        EntityManager em = getEntityManager();
        for (Schedule schedule : scheduleList) {
            em.persist(schedule);
            em.getTransaction().commit();
        }
    }

    @Override
    public List<Schedule> getSchedulesByUser(long userId) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Schedule> query = builder.createQuery(Schedule.class);
        Root<Schedule> root = query.from(Schedule.class);
        Predicate predicate = builder.equal(root.get("user"), userId);
        query.where(predicate);
        List<Schedule> schedules = em.createQuery(query).getResultList();
        if (schedules.isEmpty()) {
            return Collections.emptyList();
        }
        return schedules.stream()
                .sorted(Comparator.comparing(e -> e.getDayOfWeek().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSchedule(long scheduleId) {
        EntityManager em = getEntityManager();
        Schedule schedule = em.find(Schedule.class, scheduleId);
        System.out.println(schedule.getCourse().getName() + " : lol : " + schedule.getId());
        em.remove(schedule);
        em.getTransaction().commit();
    }
}
