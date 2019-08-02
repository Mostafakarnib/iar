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
public enum TicketStatusEnum
{

    NEW("new"),
    OPENNED("openned"),
    IN_PROGRESS("in_progress"),
    CLOSED("closed");

    private String bundleKey;

    private TicketStatusEnum(String bundleKey)
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
