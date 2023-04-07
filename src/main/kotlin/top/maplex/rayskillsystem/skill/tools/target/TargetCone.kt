package top.maplex.rayskillsystem.skill.tools.target

import org.bukkit.entity.LivingEntity
import kotlin.math.cos
import kotlin.math.pow

object TargetCone {

    /**
     * 获取扇形范围内的实体
     * @param source 触发者
     * @param arc 角度
     * @param range 范围
     */
    fun get(source: LivingEntity, arc: Double, range: Double): List<LivingEntity> {
        val targets = mutableListOf<LivingEntity>()
        val list = source.getNearbyEntities(range, range, range)
        if (arc <= 0) return targets
        val sourceLocation = source.eyeLocation
        val dir = sourceLocation.direction
        val cosSq = cos(arc * Math.PI / 180).pow(2.0)
        list.asSequence().forEach { entity ->
            if (entity is LivingEntity) {
                if (arc >= 360) {
                    targets.add(entity)
                } else {
                    val relative =
                        entity.getLocation().clone().add(0.0, entity.height * 0.5, 0.0)
                            .subtract(sourceLocation)
                            .toVector()
                    relative.setY(0)
                    val dot = relative.dot(dir)
                    val value = dot * dot / relative.lengthSquared()
                    if (arc < 180 && dot > 0 && value >= cosSq) {
                        targets.add(entity)
                    } else if (arc >= 180 && (dot > 0 || dot <= cosSq)) {
                        targets.add(entity)
                    }
                }
            }
        }
        return targets
    }

}