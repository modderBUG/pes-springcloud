package me.xiaowei.modules.pes.repository;



import me.xiaowei.modules.pes.domain.T_freetime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface T_freetimeDAO extends JpaRepository<T_freetime, Integer>, JpaSpecificationExecutor<T_freetime> {

    Optional<T_freetime> findById(Integer id);

    @Query(value = "select * from `t_freetime` where teacher_id is NULL",nativeQuery = true)
    List<T_freetime> listAllowCourse();

    List<T_freetime> findByTeacherId(String teacherId);
}
