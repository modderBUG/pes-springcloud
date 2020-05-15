package me.xiaowei.modules.pes.service.impl;

import me.xiaowei.modules.pes.domain.T_experiment;
import me.xiaowei.modules.pes.domain.T_freetime;
import me.xiaowei.modules.pes.repository.T_experimentDAO;
import me.xiaowei.modules.pes.repository.T_freetimeDAO;
import me.xiaowei.modules.pes.service.FreetimeService;
import me.xiaowei.modules.pes.service.TimeService;
import me.xiaowei.modules.pes.service.dto.FreetimeDTO;
import me.xiaowei.modules.pes.service.mapper.FreetimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/4/613:02
 * Version:1.0
 * Desc:
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = false, rollbackFor = Exception.class)
public class FreetimeServiceImpl implements FreetimeService {

    @Autowired
    T_freetimeDAO freetimeDAO;

    @Autowired
    TimeService timeService;

    @Autowired
    T_experimentDAO experimentDAO;

    @Autowired FreetimeMapper freetimeMapper;

    Lock lock=new ReentrantLock();

    @Override
    public void saveOne(T_freetime entity){
        freetimeDAO.save(entity);
    }

    @Override
    public void deleteOne(Integer id) {
        freetimeDAO.deleteById(id);
    }

    @Override
    public List<FreetimeDTO> select() {


        List<T_freetime> freetimes=freetimeDAO.listAllowCourse();
        List<FreetimeDTO> list = new LinkedList<>();
        for (T_freetime item:freetimes){
            T_experiment exp=experimentDAO.findByExpId(item.getExpId());
            list.add(freetimeMapper.from(exp,item));
        }

        return list;

    }

    @Override
    public List<T_freetime> selectAll() {
        return freetimeDAO.findAll();
    }

    @Override
    public List<FreetimeDTO> selectDTO() {

        List<T_freetime> freetimes=freetimeDAO.findAll();
        List<FreetimeDTO> list = new LinkedList<>();
        for (T_freetime item:freetimes){
            T_experiment exp=experimentDAO.findByExpId(item.getExpId());
            list.add(freetimeMapper.from(exp,item));
        }

        return list;
    }

    @Override
    public List<FreetimeDTO> selectByTeacherName( String teacherId) {
        List<T_freetime> freetimes=freetimeDAO.findByTeacherId(teacherId);
        List<FreetimeDTO> list = new LinkedList<>();
        for (T_freetime item:freetimes){
            T_experiment exp=experimentDAO.findByExpId(item.getExpId());
            list.add(freetimeMapper.from(exp,item));
        }

        return list;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public T_freetime pikeUp(Integer id, String username) {

        T_freetime course;
        lock.lock();
        try {
            if (freetimeDAO.getOne(id).getTeacherId()!=null){
                System.out.println("失败:课程已经被别的老师选啦");
                return null;
            }
            //保存教师id
            course=freetimeDAO.findById(id).get();
            course.setTeacherId(username);
            freetimeDAO.save(course);
            System.out.println("完成:教师id保存完成");

            timeService.insertAllList(course);
        }catch (Exception e){
            System.out.println("失败"+e);
            return null;
        }
        finally {
            lock.unlock();
        }
        return course;
    }
}
