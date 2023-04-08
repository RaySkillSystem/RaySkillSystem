package top.maplex.rayskillsystem.skill.impl

import ink.ptms.adyeshach.core.Adyeshach
import ink.ptms.adyeshach.core.entity.EntityTypes
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
import top.maplex.rayskillsystem.skill.tools.summoned.impl.SummonedAdyeshach
import top.maplex.rayskillsystem.skill.tools.target.TargetCone
import java.util.*

object SkillTest : AbstractSkill {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "测试"

    override val type: String = "无"

    override val cooldown: Long = 3 * 20

    override fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${SkillTest.name}"
            colored()
        }
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val entity = Adyeshach.api().getPublicEntityManager().create(
            EntityTypes.CAT, player.location
        ) {
            it.isCollision = false
        }
        SummonedAdyeshach(
            player.uniqueId,
            entity,
            1.0, 1.0, 1.0
        )
        return true
    }


}