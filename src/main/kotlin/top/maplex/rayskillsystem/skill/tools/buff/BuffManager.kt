package top.maplex.rayskillsystem.skill.tools.buff

import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import taboolib.common.platform.Schedule
import top.maplex.rayskillsystem.skill.tools.buff.event.BuffAddEvent
import top.maplex.rayskillsystem.skill.tools.buff.event.BuffRemoveEvent
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object BuffManager {


    val buffs = ConcurrentHashMap<String, AbstractBuff>()

    val data = ConcurrentHashMap<UUID, ConcurrentHashMap<String, BuffData>>()

    fun getData(target: LivingEntity, name: String): BuffData? {
        return data[target.uniqueId]?.get(name)
    }

    fun clearBuff(target: LivingEntity, name: String) {
        getData(target, name)?.overtime = -1
    }

    fun getBuff(target: LivingEntity, name: String): Int {
        val old = data.getOrDefault(target.uniqueId, hashMapOf())[name]
        return old?.level ?: 0
    }

    fun getBuff(target: Entity, name: String): Int {
        val old = data.getOrDefault(target.uniqueId, hashMapOf())[name]
        return old?.level ?: 0
    }

    fun plus(target: LivingEntity, name: String, tick: Long, max: Int, from: UUID) {
        val get = getBuff(target, name)
        val level = if (get < max) {
            get + 1
        } else {
            get
        }
        add(target, name, level, tick, from)
    }

    fun plus(target: LivingEntity, name: String, tick: Long, from: UUID) {
        val level = getBuff(target, name) + 1
        add(target, name, level, tick, from)
    }

    /**
     * @param target 目标
     * @param name Buff的ID
     * @param level 等级
     * @param from Buff来源的UUID（施法者UUID）
     */
    fun add(target: LivingEntity, name: String, level: Int, tick: Long, from: UUID) {
        val get = buffs[name] ?: return
        val old = data.getOrDefault(target.uniqueId, hashMapOf())[name]
        if (old != null) {
            if (old.level <= level) {
                old.level = level
                val maxTime = System.currentTimeMillis() + (tick * 1000 / 20)
                old.overtime += ((tick * 1000 / 20) * (0.5 / level)).toLong()
                if (old.overtime >= maxTime) {
                    old.overtime = maxTime
                }
                val overtime = (old.overtime - System.currentTimeMillis())
                get.toast(target, level, (overtime / 1000) * 20)
            }
            return
        } else {
            val overtime = System.currentTimeMillis() + (tick * 1000 / 20)
            data.getOrPut(target.uniqueId) {
                ConcurrentHashMap()
            }[name] = BuffData(name, level, overtime, from)
        }
        val event = BuffAddEvent(target, get, level, tick)
        event.call()
        get.onJoin(event.target, event.level, event.tick, from)
        get.toast(event.target, event.level, event.tick)
    }

    @Schedule(period = 20)
    fun tick() {
        data.toMap().forEach z@{ (t, u) ->
            val target = Bukkit.getEntity(t)
            if (target is LivingEntity) {
                u.forEach { (_, v) ->
                    onTick(target, v)
                }
            }
        }
    }

    fun onTick(target: LivingEntity, buff: BuffData) {
        val get = buffs[buff.name] ?: return
        if (buff.overtime <= System.currentTimeMillis()) {
            get.onOver(target, buff.level, 0)
            get.toastLevel(target, buff.level)
            BuffRemoveEvent(target, get, buff.level).call()
            data[target.uniqueId]?.let {
                if (it.isNotEmpty()) {
                    it.remove(buff.name)
                }
            }
            return
        }
        get.onTick(target, buff.level, buff.overtime, buff.from)
    }


}