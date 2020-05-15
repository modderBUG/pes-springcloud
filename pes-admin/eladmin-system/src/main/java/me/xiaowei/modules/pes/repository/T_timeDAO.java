package me.xiaowei.modules.pes.repository;


import me.xiaowei.modules.pes.domain.T_time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface T_timeDAO extends JpaRepository<T_time, Integer>, JpaSpecificationExecutor<T_time> {

   // public  T_time findById(Integer id);

    public List<T_time> findByExpId(String expId);



    public List<T_time> findByExpTime(Integer expTime);

    public List<T_time> findByTimeTimes(Integer timeTimes);

    public List<T_time> findByTimeWeek(Integer timeWeek);

    public List<T_time> findByTimeSchedule(Integer timeSchedule);

    public List<T_time> findByTeacherId(String teacherId);

    public List<T_time> findByExpIdAndTeacherId(String expId,String teacherId);

    @Query(value = "SELECT DISTINCT e.exp_name,e.exp_id,t.time_times,t.time_week,t.time_schedule FROM `t_time` t LEFT JOIN `t_experiment` e on t.exp_id=e.exp_id WHERE t.time_times=?1",nativeQuery = true)
    List<Object> getExpAndTime(Integer timeTimes);

    @Query(value = "SELECT e.exp_name,e.exp_id,t.exp_time,t.time_times,t.time_week,t.time_schedule,c.teacher_name FROM t_time t LEFT JOIN t_experiment e  on e.exp_id=t.exp_id LEFT JOIN t_teacher c on t.teacher_id=c.teacher_id ",nativeQuery = true)
    List<Object> getAllowExpAndTime();

    @Query(value = "SELECT DISTINCT e.exp_name,e.exp_id,t.exp_time,t.time_times,t.time_week,t.time_schedule,c.teacher_name FROM t_time t LEFT JOIN t_experiment e  on e.exp_id=t.exp_id LEFT JOIN t_teacher c on t.teacher_id=c.teacher_id LEFT JOIN t_grade g ON t.exp_id=g.exp_id WHERE g.stu_num=?1 ",nativeQuery = true)
    List<Object> getStuExpAndTime(String stuNum);

    @Query(value = "SELECT DISTINCT e.exp_name,e.exp_id,t.exp_time,t.time_times,t.time_week,t.time_schedule,c.teacher_name FROM t_time t LEFT JOIN t_experiment e  on e.exp_id=t.exp_id LEFT JOIN t_grade g ON t.exp_id=g.exp_id LEFT JOIN t_teacher c on g.teacher_name=c.teacher_name WHERE g.stu_num=?1 ",nativeQuery = true)
    List<Object> listStudentCourseAndTeacher(String stuNum);

    @Query(value = "SELECT DISTINCT exp_id,teacher_id FROM `t_time` WHERE teacher_id !=''",nativeQuery = true)
    List<Object> listCourseLimit();

//    @Query(value = "SELECT DISTINCT exp_id,teacher_id FROM `t_time`",nativeQuery = true)
//    List<Object> listRedisData();

}