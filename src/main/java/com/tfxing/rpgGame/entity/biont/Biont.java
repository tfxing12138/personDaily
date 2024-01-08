package com.tfxing.rpgGame.entity.biont;

import lombok.Data;

/**
 * 生物抽象类
 * 所有的生命体的基本类
 * 生命值
 * 魔法值
 * 防御值
 * 攻击力
 *
 * 弱点因素：力，火，冰，光，暗，雷，风 : 0：弱，1：普通，2：耐：3：无，4：反
 *
 * 伤害值 = 基础伤害 * 攻击修正 * 弱点修正 * 抵抗修正 * 随机因素
 * 其中，各个修正因素的含义如下：
 * 基础伤害（Base Damage）：表示技能或攻击的基本伤害值，取决于技能或攻击本身的威力。
 * 攻击修正（Attack Modifier）：表示攻击者的攻击能力修正，包括攻击力、技能等因素。
 * 弱点修正（Weakness Modifier）：表示攻击者对目标的弱点进行攻击时的伤害增益。如果目标有弱点属性，并且攻击者的技能或攻击对该属性有加倍伤害效果，那么弱点修正会增加伤害值。
 * 抵抗修正（Resistance Modifier）：表示目标对攻击者的攻击属性有抵抗或免疫时的伤害减免。如果目标对攻击者的技能或攻击有免疫或抗性，抵抗修正会减少伤害值。
 * 随机因素（Random Factor）：表示一定的随机性因素，用于增加战斗的变化性和不确定性。通常通过引入一个随机数来实现，使得同样的攻击在不同的战斗中产生不同的伤害值。
 * 需要注意的是，真女神转生系列中不同作品和具体技能可能会有略微不同的计算方式和修正因素。因此，具体的伤害计算公式可能会有所变化。如果你有特定的真女神转生游戏作品或技能需要了解，可以提供更具体的信息，我将尽力为你提供更准确的答案。
 *
 */
@Data
public abstract class Biont {

    private Integer healthPoint;

    private Integer manaPoint;

    /**
     * 防御力
     */
    private Integer defensePoint;

    /**
     * 攻击力
     */
    private Integer attackPoint;

    private Integer fire;

    private Integer physics;

    private Integer ice;

    private Integer light;

    private Integer dark;

    private Integer thunder;

    private Integer wind;

}
