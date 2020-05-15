package me.xiaowei.modules.pes.repository;

import me.xiaowei.modules.pes.domain.T_experiment;
import me.xiaowei.modules.pes.domain.T_grade;
import me.xiaowei.modules.pes.service.dto.ClassScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface T_gradeDAO extends JpaRepository<T_grade,Integer> , JpaSpecificationExecutor<T_grade> {


   List<T_grade> findByExpId(String expId);
   List<T_grade> findByExpTime(String expTime);
   List<T_grade> findByStuNum(String stuNum);

   @Query(value = "SELECT * from t_grade WHERE stu_num=?1 AND exp_id =?2",nativeQuery = true)
   List<T_grade> findByStuNumAndExpId(String stuNum,String expId);

   List<T_grade> findByAttendance(String attendance);

   List<T_grade> findByExpIdAndTimeTimesAndTimeWeek(String expId, String timeTimes, String timeWeek);
   T_grade findByExpIdAndTimeTimesAndTimeWeekAndStuNum(String expId, String timeTimes, String timeWeek,String stuNum);

    @Query(value = "SELECT time_week,COUNT(time_week) FROM `t_grade` WHERE stu_num=?1 GROUP BY time_week",nativeQuery = true)
    List<Map<String,Integer>> getWeekLimit(String stuNum);

    @Query(value = "SELECT COUNT(time_times) FROM `t_grade` g WHERE stu_num=?1 GROUP BY time_times LIMIT 1",nativeQuery = true)
    Integer getMaxLimit(String stuNum);

    @Query(value = "SELECT DISTINCT(exp_id) FROM `t_grade` WHERE stu_num=?1",nativeQuery = true)
    List<String> getAllExpId(String stuNum);

    @Query(value = "SELECT DISTINCT e.exp_name,e.exp_id,e.exp_lab,t.exp_time,t.time_times,t.time_week,t.time_schedule,c.teacher_name FROM t_grade g LEFT JOIN t_experiment e on g.exp_id=e.exp_id LEFT JOIN t_time t on e.exp_id=t.exp_id LEFT JOIN t_teacher c on t.teacher_id=c.teacher_id WHERE g.stu_num = :stuNum ",nativeQuery = true)
    //List<ClassScheduleDTO> getClassSchedule(@Param("stuNum") String stuNum);
    Page<Object[]> getClassSchedulePage(@Param("stuNum") String stuNum, Pageable pageable);

    @Query(value = "SELECT DISTINCT e.exp_name,e.exp_id,e.exp_lab,t.exp_time,t.time_times,t.time_week,t.time_schedule,c.teacher_name FROM t_grade g LEFT JOIN t_experiment e on g.exp_id=e.exp_id LEFT JOIN t_time t on e.exp_id=t.exp_id LEFT JOIN t_teacher c on t.teacher_id=c.teacher_id WHERE g.stu_num = :stuNum ",nativeQuery = true)
    Object[] getClassSchedule(@Param("stuNum") String stuNum);






}
