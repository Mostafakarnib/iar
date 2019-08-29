package org.pirlo.websockets;

public class Notification
{

    private String text;
    private String date;
    private boolean showToastr;

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public boolean isShowToastr()
    {
        return showToastr;
    }

    public void setShowToastr(boolean showToastr)
    {
        this.showToastr = showToastr;
    }

}
