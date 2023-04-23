package top.maplex.rayskillsystem.skill.impl.tiegujue

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.submit
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Astroid
import taboolib.platform.util.buildItem
import taboolib.platform.util.toProxyLocation
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffHanXiao
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetSingle
import java.util.*

object SkillHanXiaoQianJun : AbstractSkill {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "寒啸千军"

    override val type: String = "铁骨诀"

    override val cooldown: Long = 1 * 20

    override fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${SkillHanXiaoQianJun.name}"
            colored()
        }
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val target = TargetSingle.getLivingTargets(player, 3.0, 2.0).firstOrNull {
            it !is Player && Team.canAttack(player, it)
        } ?: return false
        Astroid(0.5, target.location.toProxyLocation(), object : ParticleSpawner {
            override fun spawn(location: Location) {
                spawnColor(0, location, Particle.FIREWORKS_SPARK)
            }
        }).show()
        submit(delay = 3) {
            Astroid(1.0, target.location.toProxyLocation().clone().add(0.0, 1.0, 0.0), object : ParticleSpawner {
                override fun spawn(location: Location) {
                    spawnColor(0, location, Particle.FIREWORKS_SPARK)
                }
            }).show()
            BuffManager.add(target, BuffHanXiao.id, 1, 20 * 20, player.uniqueId)
        }
        return true
    }


}