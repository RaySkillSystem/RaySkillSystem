package top.maplex.rayskillsystem.skill.impl.tiegujue

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.math.Matrixs
import taboolib.module.effect.shape.Arc
import taboolib.platform.util.buildItem
import taboolib.platform.util.toProxyLocation
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffPoZhenTieJia
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.damage
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.taunt
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetCone
import java.util.*

object SkillPoZhenShi : AbstractSkill {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "破阵式"

    override val type: String = "铁骨诀"

    override val cooldown: Long = 3 * 20

    override fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${SkillPoZhenShi.name}"
            colored()
        }
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val targets = TargetCone.get(player, 160.0, 2.25)
        if (targets.isEmpty()) {
            return false
        }

        Arc(player.location.toProxyLocation(), 180.0, 1.5, 2.0, object : ParticleSpawner {
            override fun spawn(location: Location) {
                spawnColor(0, location, Particle.DAMAGE_INDICATOR)
            }
        }).apply {
            val rot = 5 + Math.random() * (30 - 5 + 1)
            addMatrix(Matrixs.rotateAroundZAxis(if (Random().nextBoolean()) rot else -rot))
            addMatrix(Matrixs.rotateAroundZAxis(5.0))
            addMatrix(Matrixs.rotateAroundYAxis((-player.location.yaw).toDouble()))
        }.show()
        val attr = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)?.value ?: 1.0
        damage(player, targets, attr * (0.4 + level * 0.1))
        taunt(player, targets, attr * (1.25 + level * 0.1))
        BuffManager.plus(player, BuffPoZhenTieJia.id, 20 * 20, 5, player.uniqueId)
        return true
    }


}