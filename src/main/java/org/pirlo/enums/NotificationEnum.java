/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.enums;

/**
 *
 * @author pirlo
 */
public enum NotificationEnum
{
    TICKET_UPDATE("ticket_update"),
    NEW_TICKET("new_ticket"),
    COMMENTS("comments"),
    ROOT_CAUSE_ANALYSIS("root_cause_analysis"),
    ASSIGN_TASKS("assign_tasks"),
    CHANGE_OWNER("change_owner");

    private String bundleKey;

    private NotificationEnum(String bundleKey)
    {
        this.setBundleKey(bundleKey);
    }

    public String getBundleKey()
    {
        return bundleKey;
    }

    public void setBundleKey(String bundleKey)
    {
        this.bundleKey = bundleKey;
    }

}
