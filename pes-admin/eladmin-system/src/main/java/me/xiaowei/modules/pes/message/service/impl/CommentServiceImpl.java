package me.xiaowei.modules.pes.message.service.impl;

import me.xiaowei.modules.pes.message.domin.T_comment;
import me.xiaowei.modules.pes.message.repository.CommentDao;
import me.xiaowei.modules.pes.message.service.CommentService;
import me.xiaowei.modules.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/1314:43
 * Version:1.0
 * Desc:
 */

@Service
@CacheConfig(cacheNames = "t_comment")
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;

    @Autowired
    UserRepository userRepository;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public Page<T_comment> getAllComments(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "updateTime"));//PageRequest.of(pageNum,pageSize, Sort.by(Sort.Direction.DESC,"update_time"));
        return commentDao.findAll(pageable);
    }

    public List<T_comment> getAllCommentsList(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);//PageRequest.of(pageNum,pageSize, Sort.by(Sort.Direction.DESC,"update_time"));
        return commentDao.findAll();
    }

    @Override
    public void addOne(T_comment items) {


        if (userRepository.findByUsername(items.getStuName()).getUserAvatar() != null) {
            items.setUserAvatar(userRepository.findByUsername(items.getStuName()).getUserAvatar());
        }
        commentDao.save(items);
    }

    @Override
    public Boolean delOne(Integer id) {

        commentDao.deleteById(id);
        return true;
    }

    @Override
    public T_comment editOne(T_comment item) {

        commentDao.save(item);
        return item;
    }

    @Override
    public T_comment getOne(Integer id) {
        return commentDao.getOne(id);
    }
}
    