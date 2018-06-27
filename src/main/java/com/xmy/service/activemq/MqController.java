package com.xmy.service.activemq;

import com.xmy.service.dao.UserDao;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.*;

/**
 * @Description:
 * @Author: xumengyang
 * @Date: Created in 11:48 2018/6/27
 */
@Controller
public class MqController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private JmsTemplate myjmsTemplate;

    @RequestMapping("/test")
    @ResponseBody
    @Transactional
    public String test(Integer sex, String text){
        userDao.update(1,"大哥哥",sex,null,"3435345");
        //myjmsTemplate.convertAndSend("queue",text);
        delaySend(text,"queue",0L);
        return "ok";
    }



    public void delaySend(String text, String queueName, Long time) {
        //获取连接工厂
        ConnectionFactory connectionFactory = this.myjmsTemplate.getConnectionFactory();
        try {
            //获取连接
            Connection connection = connectionFactory.createConnection();
            connection.start();
            //获取session true为事务提交
            //Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            // 创建一个消息队列
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage message = session.createTextMessage(text);
            //设置延迟时间
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
            //发送
            producer.send(message);
            session.commit();

            producer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }


}
