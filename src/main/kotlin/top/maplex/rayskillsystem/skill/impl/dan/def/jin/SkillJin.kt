package top.maplex.rayskillsystem.skill.impl.dan.def.jin

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.caster.dan.DanFurnaceLunchEvent
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.impl.dan.def.YuanSu
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.damage
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetSingle


object SkillJin : AbstractSkill, YuanSu, AbstractDanCast {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "金元素"

    override val type: String = "炼丹师"

    override val cooldown: Long = 50

    override val itemId: String = "panling:metal"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }


    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "metal")
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val target = TargetSingle.get(player, 6.0, 1.5) {
            Team.canAttack(player, this) && this !is Player
        } ?: return false
        val attribute = PlayerManager.getPlayerData(player).attribute
        val value = attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH) + 1
        damage(player, target, value * 3.5)
        spawnColor(1, target.location.toProxyLocation(), 255, 238, 111, 3F)
        spawn(target.location)
        return true
    }

    fun spawn(loc: Location) {
        loc.world?.strikeLightningEffect(loc)
    }


}