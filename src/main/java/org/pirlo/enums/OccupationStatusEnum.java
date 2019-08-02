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
public enum OccupationStatusEnum
{
    HOSPITAL_STAFF("hospital_staff"),
    MEDICAL_STAFF("medical_staff"),
    RESIDENT_STAFF("resident_staff"),
    CASUAL_WORKER("casual_worker"),
    STUDENT("student"),
    VISITOR("visitor"),
    INPATIENT("inpatient"),
    OUTPATIENT("outpatient");

    private String bundleKey;

    private OccupationStatusEnum(String bundleKey)
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
