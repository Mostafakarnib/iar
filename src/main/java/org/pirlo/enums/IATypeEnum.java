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
public enum IATypeEnum
{

    NEEDLE_PRICK("needle_prick"),
    SHARP_INJURY("sharp_injury"),
    BIOLOGICAL_EXPOSURE("biological_exposure"),
    BLOOD_SPILL("blood_spill"),
    CHEMICAL_SPILL("chemical_spill"),
    RADIOACTIVE_EXPOSURE("radioactive_exposure"),
    FIRE("fire"),
    BURN("burn"),
    THEFT("theft"),
    ADVERSE_DRUG_REACTION("adverse_drug_reaction"),
    PHYSICAL_TRAUMA("physical_trauma"),
    LACERATION("laceration"),
    PRESSURE_ULCER("pressure_ulcer"),
    EXTRAVASATION("extravasation"),
    PER_OP_COMPLICATION("per_op_complication"),
    UNINTENTIONAL_EVENT("unintentional_event"),
    MEDICATION_ERRORS("medical_errors"),
    VERBAL_ABUSE("verbal_abuse"),
    PHYSICAL_ABUSE("physical_abuse"),
    FALL("fall"),
    MIS_IDENTIFICATION("mis_identification"),
    WRONG_SITE_SURGERY("wrong_site_surgery"),
    INFANT_DISCHARGE("infant_discharge"),
    POOR_PATIENT_PREPARATION("poor_patient_preparation"),
    BREACH_OF_SAFETY("breach_of_safety"),
    BREACH_IN_CONFIDENTIALITY("breach_in_confidentiality"),
    PATIENT_POSITIONING("patient_positioning"),
    ERROR_IN_DOCUMENTATION("error_in_documentation"),
    UTILITY_SYSTEMS("utility_systems"),
    POST_OP_COMPLICATION("post_op_complication"),
    INFECTION_RELATED_EVENT("infection_related_event"),
    ANESTHESIA_RELATED_EVENT("anesthesia_related_event"),
    TRANSFUSION_RELATED_EVENT("transfusion_related_event"),
    MEDICAL_EQUIPMENT_RELATED_EVENT("medical_equipment_related_event");

    private String bundleKey;

    private IATypeEnum(String bundleKey)
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
