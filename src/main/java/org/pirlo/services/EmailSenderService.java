/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author mostafa
 */
@Stateless
public class EmailSenderService
{

    @Resource(name = "java:jboss/mail/gmail")
    Session session;

    public void sendEmail(String recieverEmail, String messageSubject, String messageText)
    {
        File rootPath = new File(System.getProperty("UPLOAD_PATH"));
        try
        {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recieverEmail));
            message.setSubject(messageSubject);
            message.setText(messageText);

            if (session == null)
            {
                Paths.get(rootPath.getAbsolutePath() + File.separator + "sessionisnull").toFile().createNewFile();
            } else
            {
                Paths.get(rootPath.getAbsolutePath() + File.separator + "sessionisnotnull").toFile().createNewFile();
            }
            Transport transport = session.getTransport();
            transport.sendMessage(message, InternetAddress.parse(recieverEmail));
            Paths.get(rootPath.getAbsolutePath() + File.separator + "mailsent").toFile().createNewFile();
        } catch (Exception e)
        {
            try
            {
                Paths.get(rootPath.getAbsolutePath() + File.separator + "mailnotsent").toFile().createNewFile();
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public Session getSession()
    {
        return session;
    }

    public void setSession(Session session)
    {
        this.session = session;
    }

}
