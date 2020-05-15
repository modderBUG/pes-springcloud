package me.xiaowei.modules.pes.message.service;

import me.xiaowei.modules.pes.message.domin.T_comment;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/1314:41
 * Version:1.0
 * Desc:
 */
public interface CommentService {

    Page<T_comment> getAllComments(int pageNum,int pageSize);

    void addOne(T_comment comment);

    Boolean delOne(Integer id);

    T_comment editOne(T_comment item);


    T_comment getOne(Integer id);

    List<T_comment> getAllCommentsList(int pageNum, int pageSize);

}