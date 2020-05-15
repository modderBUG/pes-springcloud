package me.xiaowei.modules.pes.websocket.repository;

import me.xiaowei.modules.pes.domain.T_grade;
import me.xiaowei.modules.pes.websocket.domin.T_message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface T_messageDao extends JpaRepository<T_message,Integer> , JpaSpecificationExecutor<T_message> {


    List<T_message> findByFromName(String fromName);


    @Query(value = "select * from t_message where to_user_id=?1 or from_name=?1 order by update_time ", nativeQuery = true)
    List<T_message> findmsg(String fromName);

    @Query(value = "select * from t_message where to_user_id='' order by update_time ", nativeQuery = true)
    List<T_message> findMsgForGroup();

    T_message findByToUserId(String toUserId);
}

