package com.tfxing.persondaily.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * label=报价;fk=price_id
 * @TableName uni_pms_price
 */
@TableName(value ="uni_pms_price")
@Data
public class UniPmsPrice implements Serializable {
    /**
     * label=PKID
     */
    @TableId
    private Long id;

    /**
     * label=合约id;pktable=uni_pms_price_contract
     */
    @ExtendsField
    private Long pricectrId;

    /**
     * label=船公司id;pktable=uni_csm_client
     */
    @ExtendsField
    private Long clientIdCarrier;

    /**
     * label=订舱代理公司id;pktable=uni_csm_client
     */
    @ExtendsField
    private Long clientIdAgent;

    /**
     * label=装港;pktable=uni_acv_unloco
     */
    @ExtendsField
    private Long portIdLoading;

    /**
     * label=卸港;pktable=uni_acv_unloco
     */
    @ExtendsField
    private Long portIdDischarge;

    /**
     * label=最小出柜量;desc=整数类型
     */
    @ExtendsField
    private Integer mqc;

    /**
     * label=有效期开始日期
     */
    @ExtendsField
    private Date ctrStartDate;

    /**
     * label=有效期截止日期
     */
    @ExtendsField
    private Date ctrEndDate;

    /**
     * label=币制
     */
    @ExtendsField
    private String cycode;

    /**
     * label=是否是推荐;bizrule=1:是/0:否
     */
    @ExtendsField
    private Integer isRecommended;

    /**
     * label=来源;bizrule=10:系统录入/20:网上来源
     */
    @ExtendsField
    private Integer priceSource;

    /**
     * label=报价状态;bizrule=10:未发布/20:已发布/30:已过期
     */
    @ExtendsField
    private Integer priceStatus;

    /**
     * label=报价类型;bizrule=10:海运报价/20:驳船报价/30:附加费/40:拖车报价/50:其他报价/60:空运报价
     */
    @ExtendsField
    private Integer priceType;

    /**
     * label=备注
     */
    @ExtendsField
    private String remarks;

    /**
     * label=所属公司id
     */
    @ExtendsField
    private Long roworgid;

    /**
     * label=录入人
     */
    @ExtendsField
    private String insertedBy;

    /**
     * label=录入时间;desc=数据录入的时间，该时间带有时区，数据类型为 TIMESTAMP WITH TIME ZONE
     */
    @ExtendsField
    private Date insertTime;

    /**
     * label=修改人
     */
    @ExtendsField
    private String updatedBy;

    /**
     * label=修改时间;desc=数据修改的时间，该时间带有时区，数据类型为 TIMESTAMP WITH TIME ZONE
     */
    @ExtendsField
    private Date updateTime;

    /**
     * label=已删除;行数据是否删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}