package top.maplex.rayskillsystem.skill.impl.dan.def

import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.caster.dan.DanFurnaceLunchEvent
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.target.TargetRange

object SkillJinglianMu: AbstractSkill, YuanSu, AbstractDanCast {
    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "精炼木元素"

    override val type: String = "炼丹师"

    //浓缩600
    override val cooldown: Long = 160

    override val itemId: String = "panling:refined_wood"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }
    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "refined_wood")
    }

    override fun onRun(player: Player, level: Int): Boolean{
        val attribute = PlayerManager.getPlayerData(player).attribute
        val value = attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH)+1
        val target = TargetRange.get(player,8.0,true)

        return true
    }
}