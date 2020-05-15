package me.xiaowei.modules.pes.message.service.impl;

import me.xiaowei.modules.pes.message.domin.T_reply;
import me.xiaowei.modules.pes.message.repository.CommentDao;
import me.xiaowei.modules.pes.message.repository.ReplyDao;
import me.xiaowei.modules.pes.message.service.ReplyService;
import me.xiaowei.modules.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/1315:39
 * Version:1.0
 * Desc:
 */
@Service
public class ReplyServiceImpl  implements ReplyService {
    @Autowired
    ReplyDao replyDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    UserRepository userRepository;


    @Override
    public Page<T_reply> getAllComments(int id, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "update_time"));
        return replyDao.findByComment(id, pageable);
    }

    @Override
    public void addOne(T_reply item) {

        replyDao.save(item);

    }

    @Override
    public Boolean delOne(Integer id) {
        try {
            replyDao.delete(replyDao.getOne(id));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public T_reply editOne(T_reply item) {
        replyDao.save(item);
        return item;
    }

    @Override
    public T_reply getOne(Integer id) {
        return replyDao.getOne(id);
    }

    @Override
    public Page<T_reply> findByCommentId(Integer id, Pageable pageable) {

        return replyDao.findByComment(id, pageable);
    }
}
