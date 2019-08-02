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
public enum MEEnum
{

    ERROR_IN_TRANSCRIBING("error_in_transcribing"),
    DISPENSING_ERROR("dispensing_error"),
    ERROR_IN_PRESCRIBING("error_in_prescribing"),
    ERROR_IN_MONITORING_OF_DRUG_EFFECTS("error_in_monitoring_of_drug_effects"),
    ERROR_IN_ADMINISTRATION("error_in_administration");

    private String bundleKey;

    private MEEnum(String bundleKey)
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
