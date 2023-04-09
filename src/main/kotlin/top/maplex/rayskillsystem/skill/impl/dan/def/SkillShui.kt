package top.maplex.rayskillsystem.skill.impl.dan.def

import io.lumine.mythic.bukkit.entities.BukkitAreaEffectCloud
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import taboolib.common.platform.event.SubscribeEvent
import taboolib.library.xseries.XPotion
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.panlingcore.taboolib.common.LifeCycle
import top.maplex.panlingcore.taboolib.common.platform.Awake
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.caster.dan.DanFurnaceLunchEvent
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.target.TargetRange
import top.maplex.rayskillsystem.skill.tools.target.TargetSingle

object SkillShui:AbstractSkill,YuanSu, AbstractDanCast {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "水元素"

    override val type: String = "炼丹师"

    override val cooldown: Long = 50

    override val itemId: String = "panling:water"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }
    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "water")
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val target = TargetRange.get(player,5.0,true)
        target.forEach{
            it.removePotionEffect(PotionEffectType.POISON)
            it.removePotionEffect(PotionEffectType.WITHER)
        }
        return true
    }
}