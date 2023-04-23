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
import top.maplex.rayskillsystem.skill.tools.target.TargetRange

object SkillHuo : AbstractSkill, YuanSu, AbstractDanCast {
    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "火元素"

    override val type: String = "炼丹师"

    override val cooldown: Long = 50

    override val itemId: String = "panling:fire"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "fire")
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val attribute = PlayerManager.getPlayerData(player).attribute
        val value = attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH)
        val target = TargetRange.get(player, 5.0, true)
        val time = when (value) {
            in 9.0..29.0 -> 10 * 20
            in 30.0..34.0 -> 20 * 20
            else -> 30 * 20
        }
        target.forEach {
            it.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, time, 0))
        }
        return true
    }


}