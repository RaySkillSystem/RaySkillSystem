package top.maplex.rayskillsystem.skill.impl.dan.def.huo

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.caster.dan.DanFurnaceLunchEvent
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.impl.dan.def.YuanSu
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffJieHuoLing
import top.maplex.rayskillsystem.skill.tools.target.TargetRange

object SkillNongSuoHuo : AbstractSkill, YuanSu, AbstractDanCast {
    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "浓缩火元素"

    override val type: String = "炼丹师"

    override val cooldown: Long = 20 * 60

    override val itemId: String = "panling:again_refined_fire"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "again_refined_fire")
    }

    override fun onRun(player: Player, level: Int): Boolean {

        val old = SkillJingLianHuo.data[player.uniqueId]
        if (old != null) {
            //炸掉加buff
            val target = TargetRange.get(player, 6.0, false)
            target.forEach {
                if (Team.canAttack(player, it)) {
                    //爆炸
                    player.world.createExplosion(it.location, 0.0f, false, false)
                    //加buff
                    BuffManager.add(it, BuffJieHuoLing.id, 1, 20 * 30, player.uniqueId)
                }
            }

            return true
        }

        return true
    }


}