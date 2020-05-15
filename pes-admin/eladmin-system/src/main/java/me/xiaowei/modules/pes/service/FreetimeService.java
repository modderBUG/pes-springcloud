package me.xiaowei.modules.pes.service;


import me.xiaowei.modules.pes.domain.T_freetime;
import me.xiaowei.modules.pes.service.dto.FreetimeDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/4/613:02
 * Version:1.0
 * Desc:
 */
public interface FreetimeService {
    public void saveOne(T_freetime entity);

    public void deleteOne(Integer id);

    public List<FreetimeDTO> select();

    public T_freetime pikeUp(Integer id, String username);

    public List<T_freetime> selectAll();

    public List<FreetimeDTO> selectDTO();

    public List<FreetimeDTO> selectByTeacherName( String teacherId);

}