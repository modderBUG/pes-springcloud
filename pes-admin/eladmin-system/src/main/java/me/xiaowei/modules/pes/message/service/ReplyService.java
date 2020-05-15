package me.xiaowei.modules.pes.message.service;


import me.xiaowei.modules.pes.message.domin.T_reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/1314:41
 * Version:1.0
 * Desc:
 */
public interface ReplyService {

    Page<T_reply> getAllComments(int id,int pageNum, int pageSize);

    void addOne(T_reply comment);

    Boolean delOne(Integer id);

    T_reply editOne(T_reply item);

    T_reply  getOne(Integer id);

    Page<T_reply> findByCommentId(Integer id, Pageable pageable);

}