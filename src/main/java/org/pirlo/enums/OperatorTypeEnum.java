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
public enum OperatorTypeEnum
{

    REGULAR_USER("regular_user"),
    QUALITY_MANAGER("quality_manager"),
    CEO("ceo"),
    PATIENT_SAFETY("patient_safety"),
    OCCUPATIONAL_HEALTH("occupational_health"),
    NURSE_DIRECTOR("nurse_director");

    private String bundleKey;

    private OperatorTypeEnum(String bundleKey)
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
