package top.maplex.rayskillsystem.skill.tools.mechanism.flag

import top.maplex.rayskillsystem.skill.tools.mechanism.flag.event.FlagAddEvent
import top.maplex.rayskillsystem.skill.tools.mechanism.flag.event.FlagEndEvent
import taboolib.common.platform.Schedule
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

object Flag {

    val datas = ConcurrentHashMap<UUID, MutableMap<String, Long>>()

    @Schedule(period = 10)
    fun update() {
        datas.asSequence().forEach { (t, u) ->
            u.forEach { (k, v) ->
                if (v <= System.currentTimeMillis()) {
                    if (!FlagEndEvent(t, k).run().isCancelled) {
                        datas[t]!!.remove(k)
                    }
                }
            }
        }
    }

    //有返回true 无返回false
    fun hasFlag(uuid: UUID, key: String): Boolean {
        return datas.getOrDefault(uuid, mutableMapOf())[key] != null
    }

    fun addFlag(uuid: UUID, key: String, tick: Int) {
        if (!FlagAddEvent(uuid, key).run().isCancelled) {
            datas.getOrPut(uuid) { mutableMapOf() }[key] = (tick * 1000 / 20).toLong() + System.currentTimeMillis()
        }
    }

    fun removeFlag(uuid: UUID, key: String) {
        if (!FlagEndEvent(uuid, key).run().isCancelled) {
            datas[uuid]?.remove(key)
        }
    }

}
