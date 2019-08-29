package org.pirlo.websockets;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import com.google.gson.Gson;
import org.pirlo.entities.Notification;

public class NotificationDecoder implements Decoder.Text<Notification>
{

    Gson gson = new Gson();

    @Override
    public void init(EndpointConfig config)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public Notification decode(String s) throws DecodeException
    {
        // TODO Auto-generated method stub
        return gson.fromJson(s, Notification.class);
    }

    @Override
    public boolean willDecode(String s)
    {
        // TODO Auto-generated method stub
        return true;
    }

}
