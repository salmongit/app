package com.salmon.web.websocket;


import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class RealMessageDecoder implements Decoder.Text<RealMessage> {

    @Override
    public void destroy() {

    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public RealMessage decode(String s) throws DecodeException {
        return new RealMessage(s);
    }
}
