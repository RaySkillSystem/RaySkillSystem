package top.maplex.rayskillsystem.skill.tools.mechanism.effect.impl

import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import taboolib.common.platform.function.submit
import taboolib.common.util.Location
import taboolib.module.effect.shape.Ray
import taboolib.platform.util.toBukkitLocation
import java.util.*


fun Ray.backShow(
    //是否可以穿墙 true 则不可以
    wall: Boolean = true,
    //动画播放速度
    period: Long = 0L,
    //每一帧的步长
    stepTick: Int = 1,
    //播放时执行
    action: Location.() -> Unit = {},
    //碰到实体后执行
    near: LivingEntity.() -> Unit = {},
    //结束时执行
    over: () -> Unit = {},
): Ray {
    var i = 0.0
    submit(period = period) {
        if (i > maxLength) {
            over.invoke()
            cancel()
        }
        var a = stepTick
        val list = mutableListOf<UUID>()
        while (a <= 0) {
            a--
            val vectorTemp = direction.clone().multiply(i)
            val spawnLocation = origin.clone().add(vectorTemp)
            spawnParticle(spawnLocation)
            action.invoke(spawnLocation)
            //每一帧只会给一个目标造成一次伤害
            if (wall && spawnLocation.toBukkitLocation().block.type != Material.AIR) {
                over.invoke()
                return@submit
            }
            spawnLocation.toBukkitLocation().world?.getNearbyEntities(spawnLocation.toBukkitLocation(), 0.2, 0.2, 0.2) {
                it is LivingEntity
            }?.forEach {
                (it as? LivingEntity)?.let { player ->
                    if (!list.contains(player.uniqueId)) {
                        near.invoke(player)
                        list.add(player.uniqueId)
                    }
                }
            }
            i += step
        }
    }


    return this
}

fun Ray.getEntity(tolerance: Double): MutableList<LivingEntity> {
    var i = 0.0
    val list = mutableListOf<LivingEntity>()
    while (i < maxLength) {
        val vectorTemp = direction.clone().multiply(i)
        val spawnLocation = origin.clone().add(vectorTemp)
        spawnParticle(spawnLocation)
        spawnLocation.toBukkitLocation().world
            ?.getNearbyEntities(spawnLocation.toBukkitLocation(), tolerance, 3.0, tolerance)?.forEach {
                (it as? LivingEntity)?.let { player ->
                    if (!list.contains(player)) {
                        list.add(player)
                    }
                }
            }
        i += step
    }
    return list
}