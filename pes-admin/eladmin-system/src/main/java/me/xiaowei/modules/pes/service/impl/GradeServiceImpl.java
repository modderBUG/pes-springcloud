package me.xiaowei.modules.pes.service.impl;

import me.xiaowei.modules.pes.repository.T_gradeDAO;
import me.xiaowei.modules.pes.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/4/611:46
 * Version:1.0
 * Desc:
 */
@Service
public class GradeServiceImpl implements GradeService {
    @Autowired
    T_gradeDAO gradeDao;

    @Override
    public Object[] getClassSchedule(String username) {
        return gradeDao.getClassSchedule(username);
    }
}
