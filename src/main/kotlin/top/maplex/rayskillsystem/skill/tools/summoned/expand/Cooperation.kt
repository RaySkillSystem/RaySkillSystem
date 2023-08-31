package top.maplex.rayskillsystem.skill.tools.summoned.expand

import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityDamageByEntityEvent
import taboolib.common.platform.event.SubscribeEvent
import java.util.*

/**
 * 协战 跟随玩家战斗
 */
interface Cooperation {

    var target: LivingEntity?

    var master: UUID

}

object CooperationManager {

    val data = ArrayList<Cooperation>()

    @SubscribeEvent
    fun onDamage(event: EntityDamageByEntityEvent) {
        val damager = event.damager
        val entity = event.entity as? LivingEntity ?: return
        data.forEach {
            if (it.master == damager.uniqueId) {
                it.target = entity
            }
        }
    }

}
