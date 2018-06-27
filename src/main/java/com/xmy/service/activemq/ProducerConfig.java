package com.xmy.service.activemq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.annotation.Resource;

/**
 * 消息生产者配置类
 * @author ZhangCheng on 2017-07-22
 *
 */
@Configuration
public class ProducerConfig {
	
	@Resource(name = "connectionFactory")
	private SingleConnectionFactory singleConnectionFactory;
	
	/**
	 * 功能：配置Spring提供JmsTemplate，用于发送消息
	 */
	@Bean(name = "myjmsTemplate")
	public JmsTemplate jmsTemplate(){
		JmsTemplate jmsTemplate= new JmsTemplate();
		jmsTemplate.setConnectionFactory(singleConnectionFactory);
		jmsTemplate.setSessionAcknowledgeMode(0);
		return jmsTemplate;
	}
}
