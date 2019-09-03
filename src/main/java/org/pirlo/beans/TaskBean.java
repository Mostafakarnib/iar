package org.pirlo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.pirlo.entities.Operator;
import org.pirlo.entities.Task;
import org.pirlo.enums.TaskStatusEnum;
import org.pirlo.facades.OperatorFacade;
import org.pirlo.facades.TaskFacade;

@ManagedBean
@ViewScoped
public class TaskBean implements Serializable
{

    @ManagedProperty(value = "#{utilityBean}")
    UtilityBean utilityBean;
    @ManagedProperty(value = "#{loginBean}")
    LoginBean loginBean;

    @EJB
    TaskFacade taskFacade;
    @EJB
    OperatorFacade operatorFacade;

    Task task = new Task();
    List<Task> taskList = new ArrayList<>();

    List<Operator> operatorList = new ArrayList<>();

    @PostConstruct
    public void init()
    {
        try
        {
            operatorList = operatorFacade.findByHospital(loginBean.operator.getHospital());
            if ((loginBean.operator.getType().getBundleKey()).equalsIgnoreCase("QUALITY_MANAGER"))
            {
                taskList = taskFacade.findAll();
            } else
            {
                taskList = taskFacade.findTaskByOperator(loginBean.operator);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void saveTask()
    {
        boolean check = false;
        if (task.getId() == null)
        {
            check = true;
        }
        task.setStatus(TaskStatusEnum.FINISHED);
        task = taskFacade.save(task);
        if (check)
        {
            taskList.add(task);
        }
        utilityBean.hideModal("result-task-dlg");
    }

    public void selectTask(Task tmpTask)
    {
        task = tmpTask;
        if (task == null)
        {
            task = new Task();
        }
    }

    public void toggleTaskStatus(Task tmpTask)
    {
        switch (tmpTask.getStatus())
        {
            case NEW:
                tmpTask.setStatus(TaskStatusEnum.IN_PROGRESS);
                break;
            case IN_PROGRESS:
                tmpTask.setStatus(TaskStatusEnum.FINISHED);
                break;
            case FINISHED:
                break;
        }
        tmpTask = taskFacade.save(tmpTask);
    }

    public void deleteTask()
    {
        taskList.remove(task);
        taskFacade.remove(task);
    }

    public UtilityBean getUtilityBean()
    {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean)
    {
        this.utilityBean = utilityBean;
    }

    public LoginBean getLoginBean()
    {
        return loginBean;
    }

    public void setLoginBean(LoginBean loginBean)
    {
        this.loginBean = loginBean;
    }

    public TaskFacade getTaskFacade()
    {
        return taskFacade;
    }

    public void setTaskFacade(TaskFacade taskFacade)
    {
        this.taskFacade = taskFacade;
    }

    public Task getTask()
    {
        return task;
    }

    public void setTask(Task task)
    {
        this.task = task;
    }

    public List<Task> getTaskList()
    {
        return taskList;
    }

    public void setTaskList(List<Task> taskList)
    {
        this.taskList = taskList;
    }

    public OperatorFacade getOperatorFacade()
    {
        return operatorFacade;
    }

    public void setOperatorFacade(OperatorFacade operatorFacade)
    {
        this.operatorFacade = operatorFacade;
    }

    public List<Operator> getOperatorList()
    {
        return operatorList;
    }

    public void setOperatorList(List<Operator> operatorList)
    {
        this.operatorList = operatorList;
    }

}
