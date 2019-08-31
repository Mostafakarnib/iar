/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.dashboard;

import java.util.List;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Graph
{

    String id;
    String type;
    String title;
    List<AreaDataset> areaDatasets;
    List<DonutDataset> donutDatasets;
    List<String> labels;

    public Graph()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<AreaDataset> getAreaDatasets()
    {
        return areaDatasets;
    }

    public void setAreaDatasets(List<AreaDataset> areaDatasets)
    {
        this.areaDatasets = areaDatasets;
    }

    public List<DonutDataset> getDonutDatasets()
    {
        return donutDatasets;
    }

    public void setDonutDatasets(List<DonutDataset> donutDatasets)
    {
        this.donutDatasets = donutDatasets;
    }

    public List<String> getLabels()
    {
        return labels;
    }

    public void setLabels(List<String> labels)
    {
        this.labels = labels;
    }

}
