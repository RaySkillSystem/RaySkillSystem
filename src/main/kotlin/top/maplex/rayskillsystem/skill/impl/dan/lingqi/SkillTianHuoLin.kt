package top.maplex.rayskillsystem.skill.impl.dan.lingqi

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ray.mintcat.aboleth.api.AbolethAPI
import ray.mintcat.aboleth.api.AbolethAction
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.platform.util.buildItem
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.impl.dan.def.YuanSu
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffFire
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffHanXiao
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.damage
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetRange
import top.maplex.rayskillsystem.skill.tools.target.TargetSingle

object SkillTianHuoLin : AbstractSkill, YuanSu {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "天火临"

    override val type: String = "炼丹师"

    override val cooldown: Long = 1 * 20

    override fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${SkillTianHuoLin.name}"
            colored()
        }
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "fire", 3)
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val target = TargetSingle.getLivingTargets(player, 8.0, 1.5).firstOrNull {
            it !is Player && Team.canAttack(player, it)
        } ?: return false
        Circle(target.location.toProxyLocation(), 4.5, 10.0, 1,
            object : ParticleSpawner {
                override fun spawn(location: Location) {
                    spawnColor(5, location.add(0.0, 1.0, 0.0), 193, 44, 31, 2F)
                }
            }).play()
        val attribute = PlayerManager.getPlayerData(player).attribute
        val value = attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH) + 5
        val damage = value * 5
        TargetRange.get(target, 4.5, false).forEach {
            if (Team.canAttack(player, it)) {
                damage(player, it, damage)
                BuffManager.add(it, BuffFire.id, 2, 20L * 3L, player.uniqueId)
            }
        }
        Team.canAttack(player, target).let {
            damage(player, target, damage)
            BuffManager.add(target, BuffHanXiao.id, 1, 20L * 3L, player.uniqueId)
        }

        return true
    }


}