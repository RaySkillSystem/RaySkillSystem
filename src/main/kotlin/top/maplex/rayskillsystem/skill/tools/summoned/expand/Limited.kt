package top.maplex.rayskillsystem.skill.tools.summoned.expand

import top.maplex.rayskillsystem.skill.tools.summoned.SummonedEntity

/**
 * 限时召唤框架
 */
interface Limited {

    val overTime: Long

    fun checkTime(data: SummonedEntity) {
        if (overTime <= System.currentTimeMillis()) {
            data.delete()
            return
        }
    }

}