package me.xiaowei.modules.pes.service.impl;

import me.xiaowei.modules.pes.domain.T_freetime;
import me.xiaowei.modules.pes.domain.T_time;
import me.xiaowei.modules.pes.repository.T_timeDAO;
import me.xiaowei.modules.pes.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/4/615:30
 * Version:1.0
 * Desc:
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = false, rollbackFor = Exception.class)
public class TimeServiceImpl implements TimeService {
    @Autowired
    T_timeDAO t_timeDAO;


    @Override
    public void insertAllList(T_freetime item) {
        List<T_time> timeList = new LinkedList<>();
        String[] timesArry = item.getTimeTimes().split("-");
        String[] weekArry = item.getTimeWeek().split("-");
        String[] scheduleArry = item.getTimeSchedule().split("-");

        for (String times : timesArry) {
            for (int i = 0; i < weekArry.length; i++) {
                timeList.add(newTime(
                        item.getExpId(),
                        times + weekArry[i] + scheduleArry[i],
                        Integer.parseInt(times),
                        Integer.parseInt(weekArry[i]),
                        Integer.parseInt(scheduleArry[i]),
                        item.getTeacherId()
                ));
            }
        }
        t_timeDAO.saveAll(timeList);
    }

    private T_time newTime(String b, String c, Integer d, Integer e, Integer f, String g) {
        T_time expTime = new T_time();
        expTime.setExpId(b);
        expTime.setExpTime(c);
        expTime.setTimeTimes(d);
        expTime.setTimeWeek(e);
        expTime.setTimeSchedule(f);
        expTime.setTeacherId(g);
        return expTime;
    }
}
