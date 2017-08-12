package com.salmon.web.websocket;

import com.salmon.jpa.core.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor.HTTP_SESSION_ID_ATTR_NAME;

public class MessageSocketHandler extends TextWebSocketHandler {

    private final static Logger logger = LoggerFactory.getLogger(MessageSocketHandler.class);

    //private final static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<WebSocketSession>());

    private final static ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        RealMessage realMessage = JacksonUtils.json2pojo(message.getPayload(),RealMessage.class);

        session.sendMessage(message);
    }

    /**
     * 连接建立后处理
     * @param session WebSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String key = String.valueOf(session.getAttributes().get(HTTP_SESSION_ID_ATTR_NAME));
        if(!sessions.containsKey(key))
            sessions.put(key, session);
        logger.debug("connect to the websocket success... client count is {}...", sessions.size());
    }

    /**
     * 抛出异常时处理
     * @param session 连接的session
     * @param exception 异常原因
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        try {
            if(session.isOpen())
                session.close();
        } catch (IOException e) {
            //e.printStackTrace();
            logger.debug("websocket connection error message is {}......",e.getMessage());
        }
        sessions.remove(String.valueOf(session.getAttributes().get(HTTP_SESSION_ID_ATTR_NAME)));
    }
    /**
     *  连接关闭后处理
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        sessions.remove(String.valueOf(session.getAttributes().get(HTTP_SESSION_ID_ATTR_NAME)));
    }
}
