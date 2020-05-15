package me.xiaowei.modules.pes.repository;

import me.xiaowei.modules.pes.domain.T_experiment;
import me.xiaowei.modules.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface T_experimentDAO extends JpaRepository<T_experiment,Integer> , JpaSpecificationExecutor<T_experiment> {


    public T_experiment findByExpId(String expId);

    public T_experiment findByExpName(String expName);


}