package pl.edu.agh.service;

import pl.edu.agh.api.IScheduleService;
import pl.edu.agh.dao.ScheduleDao;
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

import static pl.edu.agh.service.Utils.*;

@Stateless
@Remote(IScheduleService.class)
public class ScheduleService extends BaseService implements IScheduleService {

    // OK
    @Override
    public void addSchedules(List<Schedule> scheduleList) {
        EntityManager em = getEntityManager();
        for (Schedule schedule : scheduleList) {
            em.persist(dtoToDao(schedule.getAddress()) );
            em.persist(dtoToDao(schedule));
            em.getTransaction().commit();
        }
    }

    // OK
    @Override
    public List<Schedule> getSchedulesByUser(long userId) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<ScheduleDao> query = builder.createQuery(ScheduleDao.class);
        Root<Schedule> root = query.from(Schedule.class);
        Predicate predicate = builder.equal(root.get("user"), userId);
        query.where(predicate);
        List<ScheduleDao> schedules = em.createQuery(query).getResultList();
        if (schedules.isEmpty()) {
            return Collections.emptyList();
        }
        List<Schedule> daoSchedulesToDto = daoSchedulesToDto(schedules);
        return daoSchedulesToDto.stream()
                .sorted(Comparator.comparing(e -> e.getDayOfWeek().toString()))
                .collect(Collectors.toList());
    }

    // OK
    @Override
    public void deleteSchedule(long scheduleId) {
        EntityManager em = getEntityManager();
        ScheduleDao scheduleDao = em.find(ScheduleDao.class, scheduleId);
        em.remove(scheduleDao);
        em.getTransaction().commit();
    }

}
