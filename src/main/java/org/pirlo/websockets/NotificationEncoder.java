package org.pirlo.websockets;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import org.pirlo.entities.Notification;

public class NotificationEncoder implements Encoder.Text<Notification>
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
    public String encode(Notification notification) throws EncodeException
    {
        // TODO Auto-generated method stub
        return gson.toJson(notification);
    }

}
