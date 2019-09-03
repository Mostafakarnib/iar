/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pirlo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Ajax;
import org.pirlo.dashboard.AreaDataset;
import org.pirlo.dashboard.DonutDataset;
import org.pirlo.dashboard.Graph;
import org.pirlo.entities.Department;
import org.pirlo.enums.IACategoryEnum;
import org.pirlo.enums.IATypeEnum;
import org.pirlo.enums.MEEnum;
import org.pirlo.enums.MonthesEnum;
import org.pirlo.enums.SeverityEnum;
import org.pirlo.enums.TicketStatusEnum;
import org.pirlo.facades.DepartmentFacade;
import org.pirlo.facades.TicketFacade;
import org.pirlo.utils.Utils;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@ManagedBean
@ViewScoped
public class DashboardBean implements Serializable
{

    @ManagedProperty(value = "#{loginBean}")
    LoginBean loginBean;

    @EJB
    DepartmentFacade departmentFacade;
    @EJB
    TicketFacade ticketFacade;

    List<Graph> graphList = new ArrayList<>();

    @PostConstruct
    public void init()
    {

    }

    public void drawCharts()
    {

        List<Department> departmentsList = departmentFacade.findByHospital(loginBean.operator.getHospital());
        for (Department department : departmentsList)
        {
            //graphList.add(prepareDepartmentCategoryPieChart(department));
            graphList.add(prepareDepartmentCategoryBarChart(department));
            graphList.add(prepareDepartmentTypePieChart(department));
            graphList.add(prepareDepartmentMedicationErrorsBarChart(department));
            graphList.add(prepareDepartmentTicketStatusBarChart(department));

        }
        for (IATypeEnum aTypeEnum : IATypeEnum.values())
        {
            graphList.add(prepareTypeCategoryBarChart(aTypeEnum));
        }

        for (MEEnum mEEnum : MEEnum.values())
        {
            graphList.add(prepareMedicationErrorsCategoryBarChart(mEEnum));
            graphList.add(prepareMedicationErrorsTypePieChart(mEEnum));
        }

        for (TicketStatusEnum ticketStatusEnum : TicketStatusEnum.values())
        {
            graphList.add(prepareSeverityTicketStatusBarChart(ticketStatusEnum));
        }

        Ajax.data("graph_list", graphList);
        Ajax.oncomplete("drawAllCharts();");

    }

    public String generateRandomColor()
    {
        Random random = new Random();
        int nextInt = random.nextInt(256 * 256 * 256);
        String colorCode = String.format("#%06x", nextInt);

        return colorCode;
    }

    public Graph prepareDepartmentCategoryBarChart()
    {
        List<AreaDataset> dataSet = new ArrayList<>();
        List<Department> departmentsList = departmentFacade.findByHospital(loginBean.operator.getHospital());
        List<String> labels = new ArrayList<>();
        Date starDate = new Date("2017/01/01");
        Date endDate = new Date();
        for (Department department : departmentsList)
        {
            labels.add(department.getName());
        }

        for (IACategoryEnum aCategoryEnum : IACategoryEnum.values())
        {
            String label = "";
            List<Double> countListForDepartment = new ArrayList<>();
            for (Department department : departmentsList)
            {
                label = Utils.getMessageFromBundle(aCategoryEnum.name());
                int aCategoryEnumCount = ticketFacade.findCategoryCountByDepartment(department, aCategoryEnum, starDate, endDate).size();
                countListForDepartment.add(Double.parseDouble(String.valueOf(aCategoryEnumCount)));
            }
            String color = generateRandomColor();
            AreaDataset dset = new AreaDataset(label, color, color, color, color, color, color, countListForDepartment);
            dataSet.add(dset);
        }

        Graph graph = new Graph();
        graph.setType("chartjs_bar");
        graph.setId("graph_" + graphList.size());
        graph.setTitle("Department by Category");
        graph.setLabels(labels);
        graph.setAreaDatasets(dataSet);

        return graph;
    }

    public Graph prepareDepartmentCategoryBarChart(Department department)
    {
        List<AreaDataset> dataSet = new ArrayList<>();

        List<String> labels = new ArrayList<>();
        Date starDate = new Date("2017/01/01");
        Date endDate = new Date();
        labels.add(department.getName());

        for (IACategoryEnum aCategoryEnum : IACategoryEnum.values())
        {
            String label = "";
            List<Double> countListForDepartment = new ArrayList<>();
            label = Utils.getMessageFromBundle(aCategoryEnum.name());
            int aCategoryEnumCount = ticketFacade.findCategoryCountByDepartment(department, aCategoryEnum, starDate, endDate).size();
            countListForDepartment.add(Double.parseDouble(String.valueOf(aCategoryEnumCount)));
            String color = generateRandomColor();
            AreaDataset dset = new AreaDataset(label, color, color, color, color, color, color, countListForDepartment);
            dataSet.add(dset);
        }

        Graph graph = new Graph();
        graph.setType("chartjs_bar");
        graph.setId("graph_" + graphList.size());
        graph.setTitle(department.getName() + " By " + Utils.getMessageFromBundle("CATEGORY"));
        graph.setLabels(labels);
        graph.setAreaDatasets(dataSet);

        return graph;
    }

    public Graph prepareDepartmentCategoryPieChart(Department department)
    {
        List<DonutDataset> dataSet = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Date starDate = new Date("2017/01/01");
        Date endDate = new Date();
        labels.add(department.getName());

        for (IACategoryEnum aCategoryEnum : IACategoryEnum.values())
        {
            String label = Utils.getMessageFromBundle(aCategoryEnum.name());
            int aCategoryEnumCount = ticketFacade.findCategoryCountByDepartment(department, aCategoryEnum, starDate, endDate).size();
            String color = generateRandomColor();
            DonutDataset dset = new DonutDataset(aCategoryEnumCount, color, color, label);
            dataSet.add(dset);
        }

        Graph graph = new Graph();
        graph.setType("chartjs_donut");
        graph.setId("graph_" + graphList.size());
        graph.setTitle(department.getName() + " By " + Utils.getMessageFromBundle("CATEGORY"));
        graph.setLabels(labels);
        graph.setDonutDatasets(dataSet);

        return graph;
    }

    public Graph prepareTypeCategoryBarChart(IATypeEnum aTypeEnum)

    {
        List<AreaDataset> dataSet = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Date starDate = new Date("2017/01/01");
        Date endDate = new Date();
        labels.add(Utils.getMessageFromBundle(aTypeEnum.name()));

        for (IACategoryEnum aCategoryEnum : IACategoryEnum.values())
        {
            String label = "";
            List<Double> countListForType = new ArrayList<>();
            label = Utils.getMessageFromBundle(aCategoryEnum.name());
            int aCategoryEnumCount = ticketFacade.findCategoryCountByType(aTypeEnum, aCategoryEnum).size();
            countListForType.add(Double.parseDouble(String.valueOf(aCategoryEnumCount)));

            String color = generateRandomColor();
            AreaDataset dset = new AreaDataset(label, color, color, color, color, color, color, countListForType);
            dataSet.add(dset);
        }
        Graph graph = new Graph();
        graph.setType("chartjs_bar");
        graph.setId("graph_" + graphList.size());
        graph.setTitle(Utils.getMessageFromBundle(aTypeEnum.name()) + " By " + Utils.getMessageFromBundle("CATEGORY"));
        graph.setLabels(labels);
        graph.setAreaDatasets(dataSet);

        return graph;
    }

    public Graph prepareDepartmentTypePieChart(Department department)
    {
        List<DonutDataset> dataSet = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Date starDate = new Date("2017/01/01");
        Date endDate = new Date();
        labels.add(department.getName());

        for (IATypeEnum aTypeEnum : IATypeEnum.values())
        {
            String label = "";
            label = Utils.getMessageFromBundle(aTypeEnum.name());
            int aTypeEnumCount = ticketFacade.findTypeCountByDepartment(department, aTypeEnum, starDate, endDate).size();
            String color = generateRandomColor();
            DonutDataset dset = new DonutDataset(aTypeEnumCount, color, color, label);
            dataSet.add(dset);
        }
        Graph graph = new Graph();
        graph.setType("chartjs_donut");
        graph.setId("graph_" + graphList.size());
        graph.setTitle(department.getName() + " By " + Utils.getMessageFromBundle("TYPE"));
        graph.setLabels(labels);
        graph.setDonutDatasets(dataSet);

        return graph;
    }

    public Graph prepareMedicationErrorsCategoryBarChart(MEEnum mEEnum)
    {
        List<AreaDataset> dataSet = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Date starDate = new Date("2017/01/01");
        Date endDate = new Date();
        labels.add(Utils.getMessageFromBundle(mEEnum.name()));

        for (IACategoryEnum aCategoryEnum : IACategoryEnum.values())
        {
            String label = "";
            List<Double> countListForMedicationErrors = new ArrayList<>();
            label = Utils.getMessageFromBundle(aCategoryEnum.name());
            int aCategoryEnumCount = ticketFacade.findCategoryCountByMedicationErrors(mEEnum, aCategoryEnum, starDate, endDate).size();
            countListForMedicationErrors.add(Double.parseDouble(String.valueOf(aCategoryEnumCount)));
            String color = generateRandomColor();
            AreaDataset dset = new AreaDataset(label, color, color, color, color, color, color, countListForMedicationErrors);
            dataSet.add(dset);
        }
        Graph graph = new Graph();
        graph.setType("chartjs_bar");
        graph.setId("graph_" + graphList.size());
        graph.setTitle(Utils.getMessageFromBundle(mEEnum.name()) + " By " + Utils.getMessageFromBundle("CATEGORY"));
        graph.setLabels(labels);
        graph.setAreaDatasets(dataSet);

        return graph;
    }

    public Graph prepareDepartmentMedicationErrorsBarChart(Department department)
    {
        List<AreaDataset> dataSet = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Date starDate = new Date("2017/01/01");
        Date endDate = new Date();
        labels.add(department.getName());

        for (MEEnum mEEnum : MEEnum.values())
        {
            String label = "";
            List<Double> countListForDepartment = new ArrayList<>();
            label = Utils.getMessageFromBundle(mEEnum.name());
            int mEEnumCount = ticketFacade.findMedicationErrorsCountByDepartment(department, mEEnum, starDate, endDate).size();
            countListForDepartment.add(Double.parseDouble(String.valueOf(mEEnumCount)));
            String color = generateRandomColor();
            AreaDataset dset = new AreaDataset(label, color, color, color, color, color, color, countListForDepartment);
            dataSet.add(dset);
        }
        Graph graph = new Graph();
        graph.setType("chartjs_bar");
        graph.setId("graph_" + graphList.size());
        graph.setTitle(department.getName() + " By " + Utils.getMessageFromBundle("MEDICATION_ERRORS"));
        graph.setLabels(labels);
        graph.setAreaDatasets(dataSet);

        return graph;
    }

    public Graph prepareMedicationErrorsTypePieChart(MEEnum mEEnum)
    {
        List<DonutDataset> dataSet = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Date starDate = new Date("2017/01/01");
        Date endDate = new Date();
        labels.add(Utils.getMessageFromBundle(mEEnum.name()));

        for (IATypeEnum aTypeEnum : IATypeEnum.values())
        {
            String label = "";
            label = Utils.getMessageFromBundle(mEEnum.name());
            int mEEnumCount = ticketFacade.findMedicationErrorsCountByType(mEEnum, aTypeEnum, starDate, endDate).size();
            String color = generateRandomColor();
            DonutDataset dset = new DonutDataset(mEEnumCount, color, color, label);
            dataSet.add(dset);
        }
        Graph graph = new Graph();
        graph.setType("chartjs_donut");
        graph.setId("graph_" + graphList.size());
        graph.setTitle(Utils.getMessageFromBundle(mEEnum.name()) + " By " + Utils.getMessageFromBundle("TYPE"));
        graph.setLabels(labels);
        graph.setDonutDatasets(dataSet);

        return graph;
    }

    public Graph prepareDepartmentTicketStatusBarChart(Department department)
    {
        List<AreaDataset> dataSet = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Date starDate = new Date("2017/01/01");
        Date endDate = new Date();
        labels.add(department.getName());

        for (TicketStatusEnum ticketStatusEnum : TicketStatusEnum.values())
        {
            String label = "";
            List<Double> countListForDepartment = new ArrayList<>();
            label = Utils.getMessageFromBundle(ticketStatusEnum.name());
            int ticketStatusEnumCount = ticketFacade.findTicketStatusCountByDepartment(department, ticketStatusEnum, starDate, endDate).size();
            countListForDepartment.add(Double.parseDouble(String.valueOf(ticketStatusEnumCount)));
            String color = generateRandomColor();
            AreaDataset dset = new AreaDataset(label, color, color, color, color, color, color, countListForDepartment);
            dataSet.add(dset);
        }
        Graph graph = new Graph();
        graph.setType("chartjs_bar");
        graph.setId("graph_" + graphList.size());
        graph.setTitle(department.getName() + " By " + Utils.getMessageFromBundle("TICKET") + " " + Utils.getMessageFromBundle("STATUS"));
        graph.setLabels(labels);
        graph.setAreaDatasets(dataSet);

        return graph;
    }

    public Graph prepareSeverityTicketStatusBarChart(TicketStatusEnum ticketStatusEnum)
    {
        List<AreaDataset> dataSet = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Date starDate = new Date("2017/01/01");
        Date endDate = new Date();
        labels.add(Utils.getMessageFromBundle(ticketStatusEnum.name()));

        for (SeverityEnum severityEnum : SeverityEnum.values())
        {
            String label = "";
            List<Double> countListForTicketStatus = new ArrayList<>();
            label = Utils.getMessageFromBundle(severityEnum.name());
            int severityEnumCount = ticketFacade.findTicketStatusCountBySeverity(ticketStatusEnum, severityEnum, starDate, endDate).size();
            countListForTicketStatus.add(Double.parseDouble(String.valueOf(severityEnumCount)));
            String color = generateRandomColor();
            AreaDataset dset = new AreaDataset(label, color, color, color, color, color, color, countListForTicketStatus);
            dataSet.add(dset);
        }
        Graph graph = new Graph();
        graph.setType("chartjs_bar");
        graph.setId("graph_" + graphList.size());
        graph.setTitle(Utils.getMessageFromBundle(ticketStatusEnum.name()) + " " + Utils.getMessageFromBundle("TICKET") + " By " + Utils.getMessageFromBundle("SEVERITY"));
        graph.setLabels(labels);
        graph.setAreaDatasets(dataSet);

        return graph;
    }

    public Object preparCategoryMonthBarChart()
    {
        List<AreaDataset> dataSet = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        Date starDate = new Date("2017/01/01");
        Date endDate = new Date();
        for (MonthesEnum monthesEnum : MonthesEnum.values())
        {
            labels.add(Utils.getMessageFromBundle(monthesEnum.name()));
        }

        for (IACategoryEnum aCategoryEnum : IACategoryEnum.values())
        {
            String label = "";
            List<Double> countListForMonth = new ArrayList<>();
            for (MonthesEnum monthesEnum : MonthesEnum.values())
            {
                label = Utils.getMessageFromBundle(aCategoryEnum.name());
                int aCategoryEnumCount = ticketFacade.findCategoryCountByMonth(monthesEnum, aCategoryEnum, starDate, endDate).size();
                countListForMonth.add(Double.parseDouble(String.valueOf(aCategoryEnumCount)));
            }
            String color = generateRandomColor();
            AreaDataset dset = new AreaDataset(label, color, color, color, color, color, color, countListForMonth);
            dataSet.add(dset);
        }

        //Object dataStruct = new Object(dataSet, labels);
        return null;
    }

    public LoginBean getLoginBean()
    {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean)
    {
        this.loginBean = loginBean;
    }

    public DepartmentFacade getDepartmentFacade()
    {
        return departmentFacade;
    }

    public void setDepartmentFacade(DepartmentFacade departmentFacade)
    {
        this.departmentFacade = departmentFacade;
    }

    public TicketFacade getTicketFacade()
    {
        return ticketFacade;
    }

    public void setTicketFacade(TicketFacade ticketFacade)
    {
        this.ticketFacade = ticketFacade;
    }

    public List<Graph> getGraphList()
    {
        return graphList;
    }

    public void setGraphList(List<Graph> graphList)
    {
        this.graphList = graphList;
    }

}
