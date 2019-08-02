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
public enum OutcomeEnum
{

    NEW_TICKET("new-ticket"),
    EDIT_TICKET("edit-ticket"),
    NOTIFICATIONS("notifications"),
    TICKET("ticket"),
    TICKETS("tickets"),
    DEPARTMENTS("departments"),
    OPERATORS("operators"),
    HOSPITALS("hospitals"),
    DASHBOARD("dashboard"),
    TASKS("tasks"),
    ROOT_CAUSE_ANALYSIS("root-cause-analysis");

    private String outcomeValue;

    private OutcomeEnum(String outcomeValue)
    {
        this.setOutcomeValue(outcomeValue);
    }

    public String getOutcomeValue()
    {
        return outcomeValue;
    }

    public void setOutcomeValue(String outcomeValue)
    {
        this.outcomeValue = outcomeValue;
    }

}
