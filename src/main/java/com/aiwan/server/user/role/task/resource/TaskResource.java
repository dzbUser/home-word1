package com.aiwan.server.user.role.task.resource;

import com.aiwan.server.publicsystem.annotation.CellMapping;
import com.aiwan.server.publicsystem.annotation.Resource;
import com.aiwan.server.reward.model.RewardBean;
import com.aiwan.server.user.role.task.process.TaskProgressType;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务静态资源
 *
 * @author dengzebiao
 * @since 2019.7.22
 */
@Resource("staticresource/taskResource.xls")
public class TaskResource {

    @CellMapping(name = "taskId")
    private int taskId;

    @CellMapping(name = "nextTaskId")
    private int nextTaskId;

    @CellMapping(name = "reward")
    private String reward;

    @CellMapping(name = "condition")
    private String condition;

    @CellMapping(name = "origin")
    private int origin;

    /**
     * 任务完成条件列表
     */
    private List<CompleteCondition> completeConditions = new ArrayList<>();

    /**
     * 任务奖励
     */
    private RewardBean rewardBean;

    public void init() {
        initCondition();
        this.rewardBean = new RewardBean();
        rewardBean.doParse(reward);
    }

    /**
     * 初始任务完成条件
     */
    private void initCondition() {
        String[] conditions = condition.split(" ");
        for (String conditionUnits : conditions) {
            String[] conditionUnit = conditionUnits.split(":");
            CompleteCondition completeCondition = CompleteCondition.valueOf(TaskProgressType.getType(Integer.parseInt(conditionUnit[0])), conditionUnit[1]);
            this.completeConditions.add(completeCondition);
        }
    }

    /**
     * 初始化奖励
     *
     * @return
     */

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getNextTaskId() {
        return nextTaskId;
    }

    public void setNextTaskId(int nextTaskId) {
        this.nextTaskId = nextTaskId;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public List<CompleteCondition> getCompleteConditions() {
        return completeConditions;
    }

    public void setCompleteConditions(List<CompleteCondition> completeConditions) {
        this.completeConditions = completeConditions;
    }

    public RewardBean getRewardBean() {
        return rewardBean;
    }

    public void setRewardBean(RewardBean rewardBean) {
        this.rewardBean = rewardBean;
    }
}
