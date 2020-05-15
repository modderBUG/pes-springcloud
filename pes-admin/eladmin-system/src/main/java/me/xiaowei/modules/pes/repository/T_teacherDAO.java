package me.xiaowei.modules.pes.repository;

import me.xiaowei.modules.pes.domain.T_teacher;
import me.xiaowei.modules.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface T_teacherDAO extends JpaRepository<T_teacher,String> , JpaSpecificationExecutor<T_teacher> {

 T_teacher findByTeacherId(String teacherId);

T_teacher findByTeacherName(String teacherName);
}