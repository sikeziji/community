package com.wangj.community.service;

import com.wangj.community.enums.CommentTypeEnum;
import com.wangj.community.exception.CustomizeErrorCode;
import com.wangj.community.exception.CustomizeException;
import com.wangj.community.mapper.CommentMapper;
import com.wangj.community.mapper.QuestionExtMapper;
import com.wangj.community.mapper.QuestionMapper;
import com.wangj.community.model.Comment;
import com.wangj.community.model.Question;
import com.wangj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;


    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }


        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            try {
                commentMapper.insert(comment);
            } catch (Exception e) {
                e.printStackTrace();
            }
//
//            // 回复问题
//            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
//            if (question == null) {
//                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
//            }
//
//            commentMapper.insert(comment);

//            // 增加评论数
//            Comment parentComment = new Comment();
//            parentComment.setId(comment.getParentId());
//            parentComment.setCommentCount(1);
//            commentExtMapper.incCommentCount(parentComment);
//
//            // 创建通知
//            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
//        } else {
//            // 回复问题
//            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
//            if (question == null) {
//                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
//            }
//            comment.setCommentCount(0);
//            commentMapper.insert(comment);
//            question.setCommentCount(1);
//            questionExtMapper.incCommentCount(question);
//
//            // 创建通知
//            createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        } else {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionExtMapper.incCommentCount(question);
        }
    }
}
