package top.maplex.rayskillsystem.utils.cooldown

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import java.util.concurrent.ConcurrentHashMap

object CooldownAPI {

    val map = ConcurrentHashMap<String, Long>()

    //获取剩余时间 s
    fun getTime(livingEntity: LivingEntity, key: String): Int {
        val mapKey = "${livingEntity.uniqueId}__${key}"
        val get = map.getOrDefault(mapKey, 0L)
        if (get <= 0) {
            return 0
        }
        val nowTime = System.currentTimeMillis()
        return ((get - nowTime) / 1000).toInt()
    }

    // 剩余时间 ms
    fun getTimeLong(livingEntity: LivingEntity, key: String): Long {
        val mapKey = "${livingEntity.uniqueId}__${key}"
        val get = map.getOrDefault(mapKey, 0L)
        if (get <= 0) {
            return 0
        }
        val nowTime = System.currentTimeMillis()
        return get - nowTime
    }

    //true 可以执行
    fun check(livingEntity: LivingEntity, key: String, tick: Long): Boolean {
        return check(livingEntity, key)
    }

    fun check(livingEntity: LivingEntity, key: String): Boolean {
        val mapKey = "${livingEntity.uniqueId}__${key}"
        val get = map.getOrDefault(mapKey, -1L)
        val nowTime = System.currentTimeMillis()
        return nowTime <= get
    }

    fun set(livingEntity: LivingEntity, key: String, tick: Long): Boolean {
        val mapKey = "${livingEntity.uniqueId}__${key}"
        val nowTime = System.currentTimeMillis()
        map[mapKey] = nowTime + (tick * 1000 / 20)
        return true
    }

}
