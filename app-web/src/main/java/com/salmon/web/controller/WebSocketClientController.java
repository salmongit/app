package com.salmon.web.controller;

import com.salmon.web.websocket.WebsocketClientEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class WebSocketClientController {

    private WebsocketClientEndpoint clientEndPoint = null;

    @RequestMapping("/send")
    public void message(HttpServletRequest request){
        try {
            if (clientEndPoint == null) {
                String url = "ws://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/socket/message";
                clientEndPoint = new WebsocketClientEndpoint(new URI(url));
                clientEndPoint.addMessageHandler((message) -> System.out.println(message));
            }
            // 发送消息
            clientEndPoint.sendMessage("{'name':'addChannel'}");
        } catch (URISyntaxException ex) {
            clientEndPoint =  null;
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }

}
