/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.dashboard;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class GraphDTO
{

    List<DonutDataset> donutDatasetList = new ArrayList<>();
    List<AreaDataset> areaDatasetList = new ArrayList<>();

    public List<DonutDataset> getDonutDatasetList()
    {
        return donutDatasetList;
    }

    public void setDonutDatasetList(List<DonutDataset> donutDatasetList)
    {
        this.donutDatasetList = donutDatasetList;
    }

    public List<AreaDataset> getAreaDatasetList()
    {
        return areaDatasetList;
    }

    public void setAreaDatasetList(List<AreaDataset> areaDatasetList)
    {
        this.areaDatasetList = areaDatasetList;
    }

}
