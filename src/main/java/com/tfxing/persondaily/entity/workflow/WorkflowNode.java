package com.tfxing.persondaily.entity.workflow;

import lombok.Data;

import java.util.List;

/**
 * @author :tanfuxing
 * @date :2023/2/6
 * @description : 流程节点
 */
@Data
public class WorkflowNode {
    
    private List<String> workflowStatusList;
    
    private List<Long> userIdList;

    /**
     * 处理
     * @param workflow
     * @return
     */
    private boolean handle(Workflow workflow) {
        if(hasRole(workflow)) {
            return true;
        } else {
            return getNext().handle(workflow);
        }
    }

    /**
     * 是否有审批权限
     * @param workflow
     * @return
     */
    private boolean hasRole(Workflow workflow) {
        
        return true;
    }

    /**
     * 获取下一审批节点
     * @return
     */
    private WorkflowNode getNext() {
        return new WorkflowNode();
    }
    
}
