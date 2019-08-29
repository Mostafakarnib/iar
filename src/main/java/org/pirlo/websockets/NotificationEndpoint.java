package org.pirlo.websockets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.pirlo.entities.Notification;

@ServerEndpoint(value = "/notificationws", encoders =
{
    NotificationEncoder.class
}, decoders =
{
    NotificationDecoder.class
})
public class NotificationEndpoint
{

    public static Map<String, Session> map = new HashMap<String, Session>();

    @OnOpen
    public void onOpen(Session session)
    {
        String username = session.getUserPrincipal().getName();
        if (map.containsKey(username))
        {
            map.remove(username);
        }
        map.put(username, session);
    }

    @OnClose
    public void onClose(Session session, CloseReason c)
    {
    }

    @OnMessage
    public void onMessage(Notification notification, Session session)
            throws IOException, EncodeException
    {

        // Echo the received message back to the client
        session.getBasicRemote().sendObject(notification);

    }

    public static void send(String username, Notification notification)
    {
        try
        {
            if (map.containsKey(username) && map.get(username).isOpen())
            {
                map.get(username).getBasicRemote().sendObject(notification);
            }
        } catch (Exception e)
        {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
