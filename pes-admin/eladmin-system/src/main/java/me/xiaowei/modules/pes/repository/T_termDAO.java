package me.xiaowei.modules.pes.repository;

import me.xiaowei.modules.pes.domain.T_term;
import me.xiaowei.modules.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface T_termDAO extends JpaRepository<T_term, Integer>, JpaSpecificationExecutor<T_term> {

}


