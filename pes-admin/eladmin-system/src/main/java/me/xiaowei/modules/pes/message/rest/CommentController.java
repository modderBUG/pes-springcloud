package me.xiaowei.modules.pes.message.rest;

import io.swagger.annotations.ApiOperation;
import me.xiaowei.annotation.AnonymousAccess;
import me.xiaowei.aop.log.Log;
import me.xiaowei.modules.pes.message.domin.T_comment;
import me.xiaowei.modules.pes.message.service.CommentService;

import me.xiaowei.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/1216:19
 * Version:1.0
 * Desc:
 */
@RestController
@RequestMapping("/pes/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Log("获取留言")
    @ApiOperation("获取留言")
    @GetMapping
    @AnonymousAccess
    public ResponseEntity getAllComments(@RequestParam(value = "keywords", required = false) String keywords,
                                         @RequestParam(value = "stuNum", required = false) String stuNum,
                                         @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        Page<T_comment> page = commentService.getAllComments(pageNum, pageSize);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Log("获取留言（全部）")
    @ApiOperation("获取留言（全部）")
    @GetMapping("/all")
    @AnonymousAccess
    public ResponseEntity getAllCommentsList(@RequestParam(value = "keywords", required = false) String keywords,
                                             @RequestParam(value = "stuNum", required = false) String stuNum,
                                             @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(commentService.getAllCommentsList(pageNum, pageSize), HttpStatus.OK);
    }

    @Log("发表留言")
    @ApiOperation("发表留言")
    @PostMapping
    public ResponseEntity postOneComment(@RequestBody T_comment items) {
        if (SecurityUtils.getUsername() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        items.setStuName(SecurityUtils.getUsername());
        commentService.addOne(items);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("删除留言")
    @ApiOperation("删除留言")
    @DeleteMapping("/{id}")
    public ResponseEntity delOneComment(@PathVariable("id") Integer id) {
        if (SecurityUtils.getUsername() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!commentService.getOne(id).getStuName().equals(SecurityUtils.getUsername())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(commentService.delOne(id), HttpStatus.OK);
    }

    @Log("修改留言")
    @ApiOperation("修改留言")
    @PutMapping
    public ResponseEntity editOneComment(@RequestBody T_comment items) {
        if (SecurityUtils.getUsername() == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!items.getStuName().equals(SecurityUtils.getUsername())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        commentService.editOne(items);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
