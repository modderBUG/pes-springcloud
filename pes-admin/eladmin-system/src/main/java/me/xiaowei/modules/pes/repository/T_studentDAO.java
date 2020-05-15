package me.xiaowei.modules.pes.repository;

import me.xiaowei.modules.pes.domain.T_grade;
import me.xiaowei.modules.pes.domain.T_student;
import me.xiaowei.modules.pes.domain.T_teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface T_studentDAO extends JpaRepository<T_student,String> , JpaSpecificationExecutor<T_student> {

    T_student findByStuNum(String stuNum);

    public List<T_student> findByStuClass(String stuClass);

}
