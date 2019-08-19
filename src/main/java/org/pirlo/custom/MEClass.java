/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.custom;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class MEClass
{

    SelectItem me;
    List<METypeClass> meTypeClassList = new ArrayList<>();

    public SelectItem getMe()
    {
        return me;
    }

    public void setMe(SelectItem me)
    {
        this.me = me;
    }

    public List<METypeClass> getMeTypeClassList()
    {
        return meTypeClassList;
    }

    public void setMeTypeClassList(List<METypeClass> meTypeClassList)
    {
        this.meTypeClassList = meTypeClassList;
    }

}
