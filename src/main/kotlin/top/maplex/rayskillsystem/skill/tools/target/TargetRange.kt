package top.maplex.rayskillsystem.skill.tools.target

import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.api.script.auto.InputEngine

@InputEngine("TargetRange")
object TargetRange {

    /**
     * 获取范围内的实体
     * @param source 触发者
     * @param range 范围
     */
    fun get(source: LivingEntity, range: Double, self: Boolean): List<LivingEntity> {
        return source.getNearbyEntities(range, range, range).mapNotNull {
            if (!it.isDead) {
                it as? LivingEntity
            } else {
                null
            }
        }.toMutableList().apply {
            if (self) {
                add(source)
            }
        }
    }

    fun get(source: Location, range: Double): List<LivingEntity> {
        source.world?.getNearbyEntities(source, range, range, range)?.mapNotNull {
            if (!it.isDead) {
                it as? LivingEntity
            } else {
                null
            }
        }?.toMutableList()?.let {
            return it
        }
        return emptyList()
    }

}
