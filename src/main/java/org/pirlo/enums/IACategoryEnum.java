/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.enums;

/**
 *
 * @author Toshiba
 */
public enum IACategoryEnum
{

    NEAR_MISS("near_miss"),
    SENTINEL_EVENT("sentinel_event"),
    PATIENT_DEATH("patient_death");

    private String bundleKey;

    private IACategoryEnum(String bundleKey)
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
