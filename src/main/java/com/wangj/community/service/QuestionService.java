package com.wangj.community.service;

import com.wangj.community.dto.PaginationDTO;
import com.wangj.community.dto.QuestionDTO;
import com.wangj.community.mapper.QuestionMapper;
import com.wangj.community.mapper.UserMapper;
import com.wangj.community.module.Question;
import com.wangj.community.module.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        Integer totalCount = questionMapper.count();

        if (totalCount % size == 0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount / size + 1;
        }
        if (page<1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page - 1);

        List<Question> questionList = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
            User user = userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //设置Questions
        paginationDTO.setQuestions(questionDTOList);
        //获取总数

        return paginationDTO;
    }

    public PaginationDTO list(Integer id, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(id);

        if (totalCount % size == 0){
           totalPage = totalCount/size;
        }else{
            totalPage = totalCount / size + 1;
        }
        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage,page);


        Integer offset = size * (page - 1);

        List<Question> questionList = questionMapper.listByUserId(id,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questionList) {
            User user = userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //设置Questions
        paginationDTO.setQuestions(questionDTOList);
        //获取总数

        return paginationDTO;
    }

    /**
     * 根据ID 获取QuestionDTO
     * @param id
     * @return
     */
    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.findById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        User user = userMapper.findByID(question.getCreator());
        questionDTO.setUser(user);
        BeanUtils.copyProperties(question,questionDTO);
        return questionDTO;
    }

    /**
     *  判断question的ID，是否创建或更新
     * @param question
     */
    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
