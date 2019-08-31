/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.dashboard;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class DonutDataset
{

    float value;
    String color;
    String highlight;
    String label;

    public DonutDataset(float value, String color, String highlight, String label)
    {
        this.value = value;
        this.color = color;
        this.highlight = highlight;
        this.label = label;
    }

    public float getValue()
    {
        return value;
    }

    public void setValue(float value)
    {
        this.value = value;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getHighlight()
    {
        return highlight;
    }

    public void setHighlight(String highlight)
    {
        this.highlight = highlight;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

}
