package com.salmon.jms.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ConsumerMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMsg = (TextMessage) message;
        try {
            System.out.println(textMsg.getText() + " ppppppppppppppppppppppppppp");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
