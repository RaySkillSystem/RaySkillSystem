package top.maplex.rayskillsystem.skill.impl.dan.def

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
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.target.TargetRange
import java.sql.Time

object SkillTu: AbstractSkill, YuanSu, AbstractDanCast {
    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }
    override val name: String = "土元素"

    override val type: String = "炼丹师"

    override val cooldown: Long = 50

    override val itemId: String = "panling:earth"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }
    override fun onCondition(player: Player, level: Int): Boolean {
        val attribute = PlayerManager.getPlayerData(player).attribute
        val value = attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH)
        val target = TargetRange.get(player,5.0,true)
        val lvl = when (value) {
            in 9.0..29.0 -> 1
            in 30.0..34.0 -> 2
            else -> 3
        }
        target.forEach {
                it.addPotionEffect(PotionEffect(PotionEffectType.ABSORPTION, 1800*20, lvl))
        }
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {
        
        return true
    }

}