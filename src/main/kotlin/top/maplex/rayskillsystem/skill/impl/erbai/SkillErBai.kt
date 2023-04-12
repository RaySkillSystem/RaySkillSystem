package top.maplex.rayskillsystem.skill.impl.erbai

import ink.ptms.adyeshach.core.Adyeshach
import ink.ptms.adyeshach.core.entity.EntityTypes
import ink.ptms.adyeshach.core.entity.manager.ManagerType
import ink.ptms.adyeshach.impl.entity.trait.impl.setTraitTitle
import org.bukkit.DyeColor
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Cat
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.chat.colored
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.utils.info
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap


object SkillErBai : AbstractSkill {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "追忆唤灵"

    override val type: String = "任意"

    override val cooldown: Long = 20 * 30

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    val data = ConcurrentHashMap<UUID, SummonedErBai>()

    override fun onRun(player: Player, level: Int): Boolean {
        if (data[player.uniqueId] != null) {
            data[player.uniqueId]!!.delete()
            player.info("二百跑去找毛球了")
        } else {
            val entity = player.location.world!!.spawn(player.location, Cat::class.java) {
                it.setAI(true)
                it.isInvulnerable = true
                it.collarColor = DyeColor.ORANGE
                it.catType = Cat.Type.RED
                it.customName = "&6&l自由的二百".colored()
                it.isCustomNameVisible = true
                it.isCollidable = false
                it.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)?.baseValue = 0.3
            }
            data[player.uniqueId] = SummonedErBai(
                player.uniqueId,
                entity,
                -1.0, 1.0, 1.0,
            )
            player.info("二百来到了你的身边")
        }
        return true
    }

}