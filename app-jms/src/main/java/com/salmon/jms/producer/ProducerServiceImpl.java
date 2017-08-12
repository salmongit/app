package com.salmon.jms.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


public class ProducerServiceImpl implements MessageCreator, ProducerService {

    private String msg = "";

    private Destination destination;

    private JmsTemplate jmsTemplate;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage(msg);
    }

    @Override
    public void sendMessage(){
        jmsTemplate.send(destination,this);
    }
}
