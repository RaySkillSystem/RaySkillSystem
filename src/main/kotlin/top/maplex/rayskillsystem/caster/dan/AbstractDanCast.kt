package top.maplex.rayskillsystem.caster.dan

import taboolib.platform.util.isRightClick
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.SkillManager
import top.maplex.rayskillsystem.utils.getString

interface AbstractDanCast {

    val itemId: String

    fun lunch(skill: AbstractSkill, event: DanFurnaceLunchEvent) {
        val player = event.player
        if (!event.interEvent.isRightClick()) {
            return
        }
        if (event.value.getString("id", "null") == itemId) {
            SkillManager.eval(player, skill.name, 1)
        }
    }

}