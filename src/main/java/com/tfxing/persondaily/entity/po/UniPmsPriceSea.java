package com.tfxing.persondaily.entity.po;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * label=海运报价;fk=pricesea_id
 * @TableName uni_pms_price_sea
 */
@TableName(value ="uni_pms_price_sea")
@Data
public class UniPmsPriceSea extends UniPmsPrice implements Serializable {
    /**
     * label=PKID
     */
    @TableId
    private Long id;

    /**
     * label=报价主表id;pktable=uni_pms_price
     */
    private Long priceId;

    /**
     * label=收货地;pktable=uni_acv_unloco
     */
    private Long portIdRecieve;

    /**
     * label=目的港;pktable=uni_acv_unloco
     */
    private Long portIdDestination;

    /**
     * label=航线;pktable=uni_acv_line
     */
    private Long lineId;

    /**
     * label=开船;desc=开船时间，可以有多个，用/分隔;bizrule=取值范围为周一到周日数字
     */
    private String sailingWeekdays;

    /**
     * label=截关;desc=截关时间;bizrule=取值范围为周一到周日数字
     */
    private String customsCutoffWeekdays;

    /**
     * label=运输条款;pktable=uni_acv_data;bizrule=typecode:TRANSPORTATION_CLAUSE，内容为uni_acv_data表中的typecode为 TRANSPORTATION_CLAUSE 的数据
     */
    private String datacodeTransportationClause;

    /**
     * label=航程;desc=天数,可能会有多个用/分隔
     */
    private String routeRange;

    /**
     * label=免堆期(天);desc=天数
     */
    private Integer freeStorage;

    /**
     * label=航线代码
     */
    private String routeCode;

    /**
     * label=箱属;bizrule=10:COC/20:SOC。SOC，全称为shipper's own container，指托运人拥有集装箱，也称为自备箱或货主箱。SOC舱位就是指，船公司只提供一个舱位，不提供集装箱，托运人需要自己为托运货物找到合适的集装箱，并将相关信息提交承运人然后安排装箱出货。COC，全称为Carrier's Own Container，指承运人拥有的集装箱。COC舱位就是指船公司除了要给托运人放舱位以外，还要给托运人配备好集装箱。托运人拿着运货单Shipping Order去相应码头堆场提空箱回去然后就可以安排装柜了。
     */
    private String contanierOwner;

    /**
     * label=航位情况;bizrule=10:充足/20:爆仓
     */
    private Integer routeStorageStatus;

    /**
     * label=品名
     */
    private String goodsName;

    /**
     * label=放货方式;desc=内容为uni_acv_data表中的typecode为MBL_TYPE;bizrule=MBL_TYPE
     */
    private String datacodeReleaseType;

    /**
     * label=路线;bizrule=DIR:直航/VIA:中转
     */
    private String routeType;

    /**
     * label=订舱备注
     */
    private String remarks;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Long getSuperId() {
        return super.getId();
    }

    public void setSuperField(UniPmsPrice uniPmsPrice) throws IllegalAccessException {
        this.setPricectrId(uniPmsPrice.getPricectrId());

        Class<UniPmsPrice> clazz = UniPmsPrice.class;
        Field[] allField = clazz.getDeclaredFields();

        for (Field field : allField) {
            field.setAccessible(true);

            if(!field.isAnnotationPresent(ExtendsField.class)) {
                continue;
            }

            Object value = field.get(uniPmsPrice);
            field.set(this, value);
        }
    }

    public static void main(String[] args) throws IllegalAccessException {
        UniPmsPrice uniPmsPrice = new UniPmsPrice();
        uniPmsPrice.setCycode("CNY");

        UniPmsPriceSea uniPmsPriceSea = new UniPmsPriceSea();
        uniPmsPriceSea.setGoodsName("hallo");

        BeanUtil.copyProperties(uniPmsPrice,uniPmsPriceSea);

        System.out.println(uniPmsPriceSea.getCycode());
        System.out.println(uniPmsPriceSea.getGoodsName());
    }
}