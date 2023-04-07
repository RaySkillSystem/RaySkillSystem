package top.maplex.rayskillsystem.caster.dan

import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.isRightClick
import top.maplex.rayskillsystem.skill.SkillManager
import top.maplex.rayskillsystem.utils.getString

object Listener {

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        val player = event.player
        if (!event.interEvent.isRightClick()) {
            return
        }
        when (event.value.getString("id", "null")) {

            //普通金
            "panling:metal" -> {
                SkillManager.eval(player, "金元素", 1)
            }

            "panling:refined_metal" -> {
                SkillManager.eval(player, "精炼金元素", 1)
            }

            "null" -> {
                return
            }
        }
    }
}