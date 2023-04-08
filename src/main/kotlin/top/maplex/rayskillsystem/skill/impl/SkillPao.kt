package top.maplex.rayskillsystem.skill.impl

import ink.ptms.adyeshach.core.Adyeshach
import ink.ptms.adyeshach.core.entity.EntityTypes
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Ray
import taboolib.platform.util.buildItem
import taboolib.platform.util.toBukkitLocation
import taboolib.platform.util.toProxyLocation
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.impl.backShow
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.impl.getEntity
import top.maplex.rayskillsystem.utils.set
import java.util.*

object SkillPao : AbstractSkill {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "测试弹道"

    override val type: String = "无"

    override val cooldown: Long = 3 * 20

    override fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${SkillPao.name}"
            colored()
        }.apply {
            set("RaySkill.type", this@SkillPao.name)
        }
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val entity = Adyeshach.api().getPublicEntityManager().create(
            EntityTypes.ARMOR_STAND, player.location
        ) {
            it.isCollision = false
            it.setNoGravity(false)
            it.setCustomName("测试弹道")
            it.setCustomNameVisible(true)
        }
        Ray(
            player.eyeLocation.toProxyLocation(),
            player.eyeLocation.toProxyLocation().direction,
            20.0,
            object : ParticleSpawner {
                override fun spawn(location: Location) {
                    entity.teleport(location.toBukkitLocation())
                }
            }).backShow(
            period = 2,
            near = {
                damage(20.0)
                //entity.remove()
            }, over = {
                entity.remove()
            })
        return true
    }


}