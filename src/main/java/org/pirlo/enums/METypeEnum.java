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
public enum METypeEnum
{

    WRONG_TIME("wrong_time"),
    WRONG_ROUTE("wrong_route"),
    WRONG_DRUG("wrong_drug"),
    WRONG_PATIENT("wrong_patient"),
    WRONG_DOSE("wrong_dose"),
    OMISSION_OF_DOSE("omission_of_dose"),
    EXTRA_OVERDOSE("extra_overdose"),
    WRONG_RATE("wrong_rate"),
    DRUG_INTERACTION("drug_inter")
    ;

    private String bundleKey;

    private METypeEnum(String bundleKey)
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
