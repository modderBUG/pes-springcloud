package me.xiaowei.modules.pes.message.repository;

import me.xiaowei.modules.pes.message.domin.T_comment;
import me.xiaowei.modules.pes.message.domin.T_reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/1216:17
 * Version:1.0
 * Desc:
 */
public interface ReplyDao extends JpaRepository<T_reply,Integer>, JpaSpecificationExecutor<T_reply> {

    @Query(value = "select * from t_reply where comment_id =?1", nativeQuery = true)
    Page<T_reply> findByComment(Integer commentId, Pageable pageable);
}

