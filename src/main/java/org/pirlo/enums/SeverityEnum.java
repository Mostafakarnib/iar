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
public enum SeverityEnum
{

    LOW("low"),
    MODERATE("moderate"),
    SEVERE("severe");

    private String bundleKey;

    private SeverityEnum(String bundleKey)
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
