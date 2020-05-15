package me.xiaowei.modules.pes.message.rest;

import io.swagger.annotations.ApiOperation;
import me.xiaowei.aop.log.Log;
import me.xiaowei.modules.pes.message.domin.T_comment;
import me.xiaowei.modules.pes.message.domin.T_reply;
import me.xiaowei.modules.pes.message.service.CommentService;
import me.xiaowei.modules.pes.message.service.ReplyService;
import me.xiaowei.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User：modderBUG
 * Date：2020/3/1315:41
 * Version:1.0
 * Desc:
 */
@RestController
@RequestMapping("/pes/api/reply")
public class ReplyController {
    @Autowired
    ReplyService replyService;

    @Autowired
    CommentService commentService;

    @Log("获取回复")
    @ApiOperation("获取回复")
    @GetMapping
    public ResponseEntity getByCommentId(@RequestParam(value = "keywords", required = false) String keywords,
                                         @RequestParam(value = "commentId", required = false) Integer commentId,
                                         @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return new ResponseEntity<>(replyService.findByCommentId(commentId, pageable), HttpStatus.OK);
    }

    @Log("发表回复")
    @ApiOperation("发表回复")
    @PostMapping
    public ResponseEntity addOneReply(@RequestBody T_reply item) {
        item.setStuName(SecurityUtils.getUsername());
        replyService.addOne(item);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("删除回复")
    @ApiOperation("删除回复")
    @DeleteMapping("/{id}")
    public ResponseEntity delOneReply(@PathVariable("id") Integer id) {
        if (!replyService.getOne(id).getStuName().equals(SecurityUtils.getUsername())) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        replyService.delOne(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}