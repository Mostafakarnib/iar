/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.custom;

import javax.faces.model.SelectItem;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class METypeClass
{

    SelectItem meType;
    boolean selected;

    public METypeClass(SelectItem meType, boolean selected)
    {
        this.meType = meType;
        this.selected = selected;
    }

    public SelectItem getMeType()
    {
        return meType;
    }

    public void setMeType(SelectItem meType)
    {
        this.meType = meType;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

}
