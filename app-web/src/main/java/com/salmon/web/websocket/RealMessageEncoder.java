package com.salmon.web.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


public class RealMessageEncoder implements Encoder.Text<RealMessage> {

    @Override
    public String encode(RealMessage message) throws EncodeException {
        return message.getName() + " 我的测试name";
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
