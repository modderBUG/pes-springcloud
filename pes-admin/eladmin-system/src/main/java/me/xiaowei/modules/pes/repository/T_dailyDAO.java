package me.xiaowei.modules.pes.repository;

import me.xiaowei.modules.pes.domain.T_daily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/5/913:39
 * Version:1.0
 * Desc:
 */


public interface T_dailyDAO extends JpaRepository<T_daily,Integer>, JpaSpecificationExecutor<T_daily> {

    @Query(value = "select * from t_daily Order By update_time Desc limit 5", nativeQuery = true)
    List<T_daily> findByLimit5();

    @Query(value = "select * from t_daily Order By update_time Desc limit ?1", nativeQuery = true)
    List<T_daily> findByLimit(Integer num);
}