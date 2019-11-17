package org.pirlo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.omnifaces.util.Ajax;
import org.pirlo.enums.IACategoryEnum;
import org.pirlo.enums.IATypeEnum;
import org.pirlo.enums.LangEnum;
import org.pirlo.enums.MEEnum;
import org.pirlo.enums.METypeEnum;
import org.pirlo.enums.OccupationStatusEnum;
import org.pirlo.enums.OperatorTypeEnum;
import org.pirlo.enums.TaskStatusEnum;
import org.pirlo.enums.TicketStatusEnum;
import org.pirlo.utils.Utils;

@ManagedBean
@ApplicationScoped
public class UtilityBean implements Serializable {

    @Resource(name = "java:jboss/mail/gmail")
    Session session;

    final int toastrTimeout = 2000;
    List<String> tmpList = new ArrayList<>();

    List<SelectItem> occupationalStatusList = new ArrayList<>();
    List<SelectItem> iaTypeList = new ArrayList<>();
    List<SelectItem> iaCategoryList = new ArrayList<>();
    List<SelectItem> meEnumList = new ArrayList<>();
    List<SelectItem> meTypeEnumList = new ArrayList<>();
    List<SelectItem> operatorTypeEnumList = new ArrayList<>();
    List<SelectItem> langList = new ArrayList<>();
    List<SelectItem> taskStatusList = new ArrayList<>();
    List<SelectItem> ticketStatusEnum = new ArrayList<>();

    @PostConstruct
    public void init() {
        refreshEnumsList();

        tmpList.add("testing_ticket1");
        tmpList.add("testing_ticket2");
        tmpList.add("testing_ticket3");
    }

    public void refreshEnumsList() {
        occupationalStatusList.clear();
        iaTypeList.clear();
        iaCategoryList.clear();
        meEnumList.clear();
        meTypeEnumList.clear();
        operatorTypeEnumList.clear();
        langList.clear();
        taskStatusList.clear();
        ticketStatusEnum.clear();

        for (OccupationStatusEnum type : OccupationStatusEnum.values()) {
            occupationalStatusList.add(new SelectItem(type, Utils.getMessageFromBundle(type.name())));
        }

        for (IATypeEnum type : IATypeEnum.values()) {
            iaTypeList.add(new SelectItem(type, Utils.getMessageFromBundle(type.name())));
        }

        for (IACategoryEnum type : IACategoryEnum.values()) {
            iaCategoryList.add(new SelectItem(type, Utils.getMessageFromBundle(type.name())));
        }
        for (MEEnum type : MEEnum.values()) {
            meEnumList.add(new SelectItem(type, Utils.getMessageFromBundle(type.name())));
        }
        for (METypeEnum type : METypeEnum.values()) {
            meTypeEnumList.add(new SelectItem(type, Utils.getMessageFromBundle(type.name())));
        }
        for (OperatorTypeEnum type : OperatorTypeEnum.values()) {
            operatorTypeEnumList.add(new SelectItem(type, Utils.getMessageFromBundle(type.name())));
        }

        for (LangEnum item : LangEnum.values()) {
            langList.add(new SelectItem(item, Utils.getMessageFromBundle(item.name())));
        }
        for (TaskStatusEnum status : TaskStatusEnum.values()) {
            taskStatusList.add(new SelectItem(status, Utils.getMessageFromBundle(status.name())));
        }
        for (TicketStatusEnum status : TicketStatusEnum.values()) {
            ticketStatusEnum.add(new SelectItem(status, Utils.getMessageFromBundle(status.name())));
        }
    }

    public String getColorClass(int index) {
        String classString = "";

        index++;

        if (index % 3 == 0) {
            classString = "default";
        } else if (index % 4 == 0) {
            classString = "warning";
        } else if (index % 5 == 0) {
            classString = "success";
        } else if (index % 2 == 0) {
            classString = "danger";
        } else if (index % 1 == 0) {
            classString = "primary";
        }

        return classString;
    }

    public void sendEmail(final String messageSubject, final String messageText, final String recieverEmail) {
        Thread msgThread = new Thread(
                new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Sending messageSubject " + messageSubject);
                    System.out.println("Sending messageText " + messageText);
                    System.out.println("Sending recieverEmail " + recieverEmail);
                    Message message = new MimeMessage(session);
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recieverEmail));
                    message.setSubject(messageSubject);
                    message.setText(messageText);
                    Transport.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        msgThread.start();
    }

    public void showModal(String modalid) {
        Ajax.oncomplete("$('." + modalid + "').modal('show');");
    }

    public void hideModal(String modalid) {
        Ajax.oncomplete("$('." + modalid + "').modal('hide');");
    }

    public void showToastr(String description, String type) {
        String toast = "showToastr('" + description + "','" + type + "','" + toastrTimeout + "');";
        System.out.println(toast);
        Ajax.oncomplete(toast);
    }

    public List<SelectItem> getOperatorTypeEnumList() {
        return operatorTypeEnumList;
    }

    public void setOperatorTypeEnumList(List<SelectItem> operatorTypeEnumList) {
        this.operatorTypeEnumList = operatorTypeEnumList;
    }

    public List<SelectItem> getOccupationalStatusList() {
        return occupationalStatusList;
    }

    public void setOccupationalStatusList(List<SelectItem> occupationalStatusList) {
        this.occupationalStatusList = occupationalStatusList;
    }

    public List<SelectItem> getIaTypeList() {
        return iaTypeList;
    }

    public void setIaTypeList(List<SelectItem> iaTypeList) {
        this.iaTypeList = iaTypeList;
    }

    public List<SelectItem> getIaCategoryList() {
        return iaCategoryList;
    }

    public void setIaCategoryList(List<SelectItem> iaCategoryList) {
        this.iaCategoryList = iaCategoryList;
    }

    public List<SelectItem> getMeEnumList() {
        return meEnumList;
    }

    public void setMeEnumList(List<SelectItem> meEnumList) {
        this.meEnumList = meEnumList;
    }

    public List<SelectItem> getMeTypeEnumList() {
        return meTypeEnumList;
    }

    public void setMeTypeEnumList(List<SelectItem> meTypeEnumList) {
        this.meTypeEnumList = meTypeEnumList;
    }

    public List<String> getTmpList() {
        return tmpList;
    }

    public void setTmpList(List<String> tmpList) {
        this.tmpList = tmpList;
    }

    public List<SelectItem> getLangList() {
        return langList;
    }

    public void setLangList(List<SelectItem> langList) {
        this.langList = langList;
    }

    public List<SelectItem> getTaskStatusList() {
        return taskStatusList;
    }

    public void setTaskStatusList(List<SelectItem> taskStatusList) {
        this.taskStatusList = taskStatusList;
    }

    public List<SelectItem> getTicketStatusEnum() {
        return ticketStatusEnum;
    }

    public void setTicketStatusEnum(List<SelectItem> ticketStatusEnum) {
        this.ticketStatusEnum = ticketStatusEnum;
    }

}
