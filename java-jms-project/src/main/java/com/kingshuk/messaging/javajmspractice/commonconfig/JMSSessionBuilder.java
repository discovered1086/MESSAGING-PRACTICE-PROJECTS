package com.kingshuk.messaging.javajmspractice.commonconfig;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.NamingException;

public class JMSSessionBuilder {
    private QueueConnectionFactory connectionFactory=null;
    private QueueConnection queueConnection=null;
    private QueueSession session=null;
    private Queue queue=null;
    private Context context=null;

    public JMSSessionBuilder() {
        setJMSConfiguration();
    }

    private void setJMSConfiguration(){
        context=new ContextConfiguration().getJMSContextDetails();
        try{
            connectionFactory=(QueueConnectionFactory) context.lookup("jms/JMSPracticeConnectionFactory");
            queueConnection = connectionFactory.createQueueConnection();
            session = queueConnection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
            queue = (Queue) context.lookup("jms/JMSPracticeQueue");
        }catch(NamingException | JMSException ex){
            ex.printStackTrace();
        }
    }


    public QueueConnection getQueueConnection() {
        return queueConnection;
    }

    public QueueSession getSession() {
        return session;
    }

    public Queue getQueue() {
        return queue;
    }
}
