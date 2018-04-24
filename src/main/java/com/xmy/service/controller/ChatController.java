package com.xmy.service.controller;

import com.xmy.bean.vo.ChatTableVo;
import com.xmy.bean.vo.ChatlogVo;
import com.xmy.service.dao.ChatDao;
import com.xmy.service.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: xumengyang
 * @Date: Created in 9:47 2018/4/12
 */
@RestController
public class ChatController {
    @Autowired
    private ChatDao chatDao;

    @CrossOrigin(origins = "*")
    @RequestMapping("/getChatList")
    public JsonResponse getListGroupByFromId(@RequestParam("userId") Integer userId){
        List<ChatTableVo> list = chatDao.getListGroupByFromId(userId);
        return new JsonResponse(list);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/getChatLog")
    public JsonResponse getChatLog(@RequestParam("toId") Integer toId, @RequestParam("fromId")Integer fromId){
        List<ChatlogVo> list = chatDao.getChatLog(toId, fromId);
        //改成已读
        chatDao.updateState(toId,fromId);
        return new JsonResponse(list);
    }
    @CrossOrigin(origins = "*")
    @RequestMapping("/saveChatLog")
    public JsonResponse saveChatLog(@RequestBody Map<String,String> map){
        String fromId = map.get("fromId");
        String toId = map.get("toId");
        String content = map.get("content");
        String state = map.get("state");
        chatDao.save(Integer.valueOf(fromId),Integer.valueOf(toId),content,Integer.valueOf(state));
        return new JsonResponse("");
    }

}
