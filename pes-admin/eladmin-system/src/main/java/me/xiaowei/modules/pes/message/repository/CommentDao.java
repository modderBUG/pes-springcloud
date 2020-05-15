package me.xiaowei.modules.pes.message.repository;

import me.xiaowei.modules.pes.message.domin.T_comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/1216:16
 * Version:1.0
 * Desc:
 */


public interface CommentDao extends JpaRepository<T_comment,Integer>, JpaSpecificationExecutor<T_comment> {

    @Query(value = "select * from t_comment",nativeQuery = true)
    Page<T_comment> pageableFindAll(Pageable pageable);

}