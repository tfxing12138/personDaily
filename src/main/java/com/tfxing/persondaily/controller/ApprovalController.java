package com.tfxing.persondaily.controller;

import com.tfxing.persondaily.entity.ResponseInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :tanfuxing
 * @date :2023/2/6
 * @description : 责任链模式实现审批流
 */
@RestController
@RequestMapping("/workflow")
public class ApprovalController {

    /**
     * 审批
     * @return
     */
    @PostMapping("/approval")
    public ResponseInfo<Boolean> approval() {
        
        return ResponseInfo.<Boolean>success(null);
    }

    /**
     * 添加流程
     * @return
     */
    @PostMapping("/addWorkflow")
    public ResponseInfo<Boolean> addWorkflow() {
        return ResponseInfo.<Boolean>success(null);
    }
}
