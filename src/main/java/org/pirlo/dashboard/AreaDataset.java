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
public class AreaDataset
{

    String label;
    String fillColor;
    String strokeColor;
    String pointColor;
    String pointStrokeColor;
    String pointHighlightFill;
    String pointHighlightStroke;
    List<Double> data = new ArrayList<>();

    public AreaDataset(String label, String fillColor, String strokeColor, String pointColor,
            String pointStrokeColor, String pointHighlightFill, String pointHighlightStroke, List<Double> data)
    {
        this.label = label;
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.pointColor = pointColor;
        this.pointStrokeColor = pointStrokeColor;
        this.pointHighlightFill = pointHighlightFill;
        this.pointHighlightStroke = pointHighlightStroke;
        this.data = data;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getFillColor()
    {
        return fillColor;
    }

    public void setFillColor(String fillColor)
    {
        this.fillColor = fillColor;
    }

    public String getStrokeColor()
    {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor)
    {
        this.strokeColor = strokeColor;
    }

    public String getPointColor()
    {
        return pointColor;
    }

    public void setPointColor(String pointColor)
    {
        this.pointColor = pointColor;
    }

    public String getPointStrokeColor()
    {
        return pointStrokeColor;
    }

    public void setPointStrokeColor(String pointStrokeColor)
    {
        this.pointStrokeColor = pointStrokeColor;
    }

    public String getPointHighlightFill()
    {
        return pointHighlightFill;
    }

    public void setPointHighlightFill(String pointHighlightFill)
    {
        this.pointHighlightFill = pointHighlightFill;
    }

    public String getPointHighlightStroke()
    {
        return pointHighlightStroke;
    }

    public void setPointHighlightStroke(String pointHighlightStroke)
    {
        this.pointHighlightStroke = pointHighlightStroke;
    }

    public List<Double> getData()
    {
        return data;
    }

    public void setData(List<Double> data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "AreaDataset{" + "label=" + label + ", fillColor=" + fillColor + ", strokeColor=" + strokeColor + ", pointColor=" + pointColor + ", pointStrokeColor=" + pointStrokeColor + ", pointHighlightFill=" + pointHighlightFill + ", pointHighlightStroke=" + pointHighlightStroke + ", data=" + data + '}';
    }

}
