package com.tfxing.rpgGame.entity.biont;

import com.tfxing.rpgGame.entity.skill.Skill;
import lombok.Data;

import java.util.List;

/**
 * 技能：
 *
 */
@Data
public class Link extends Biont{

    private List<Skill> skillList;



}
