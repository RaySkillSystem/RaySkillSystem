package top.maplex.rayskillsystem.api.script

import ink.ptms.adyeshach.taboolib.common.reflect.Reflex.Companion.getProperty
import ink.ptms.adyeshach.taboolib.common.reflect.Reflex.Companion.invokeMethod
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.function.getUsableEvent
import taboolib.platform.BukkitListener
import top.maplex.rayskillsystem.RaySkillSystem
import top.maplex.rayskillsystem.api.script.auto.InputEngine
import java.util.function.Consumer

@InputEngine("Listener")
object ScriptEventListener {

    val manager by lazy {
        Bukkit.getServer().pluginManager
    }

    private val listener = ArrayList<Pair<Class<Event>, BukkitListener.BukkitListener>>()

    fun register(
        event: String,
        priority: String = "NORMAL",
        ignoreCancelled: Boolean = true,
        func: Consumer<Any>,
    ) {
        try {
            val clazz = Class.forName(event)
            val eventClass = clazz.getUsableEvent() as Class<Event>
            val priorityObject = EventPriority.valueOf(priority)
            val data = BukkitListener.BukkitListener(clazz) { func.accept(it) }
            manager.registerEvent(
                eventClass,
                data,
                priorityObject,
                data,
                RaySkillSystem.plugin,
                ignoreCancelled
            )
            listener.add(Pair(eventClass, data))
        } catch (e: Exception) {
            error("注册监听器 $event 时出现错误: ${e.message}")
        }
    }

    fun unRegisterAll() {
        listener.forEach {
            val clazz = it.first
            val data = it.second
            val declaredMethod = clazz.getDeclaredMethod("getHandlerList")
            declaredMethod.isAccessible = true
            val handlerList = declaredMethod.invoke(null) as HandlerList
            handlerList.unregister(data)
        }
        listener.clear()
    }


}
