package com.salmon.web.websocket;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ServerEndpoint(value = "/socket",encoders = RealMessageEncoder.class, decoders = RealMessageDecoder.class)
public class RealMessageEndpoint {

    private static ConcurrentMap<String, Session> sessions = new ConcurrentHashMap<String, Session>();

    @OnMessage
    public void onMessage(Session session, RealMessage msg){
        try {
            //session.getBasicRemote().sendObject(msg);
            for (Map.Entry<String,Session> map : sessions.entrySet()) {
                map.getValue().getAsyncRemote().sendObject(msg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        if(session != null && !sessions.containsKey(session.getId()))
            sessions.put(session.getId(),session);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        if(session != null && sessions.containsKey(session.getId()))
            sessions.remove(session.getId());
    }

    @OnClose
    public void onClose(Session session, CloseReason reason){
        if(session != null && sessions.containsKey(session.getId()))
            sessions.remove(session.getId());
    }

}
