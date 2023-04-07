package top.maplex.rayskillsystem.skill.tools.target

import org.bukkit.entity.LivingEntity

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

}