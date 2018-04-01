package com.xmy.service.service;

import com.xmy.service.dao.CommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xmy on 2018/4/1.
 */
@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;


    public int deleteById(int id) {
        return commentDao.deleteById(id);
    }
}
