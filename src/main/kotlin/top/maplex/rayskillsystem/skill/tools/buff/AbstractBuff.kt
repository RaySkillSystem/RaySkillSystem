package top.maplex.rayskillsystem.skill.tools.buff

import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import taboolib.module.chat.colored
import taboolib.module.nms.sendToast
import taboolib.module.nms.type.ToastBackground
import taboolib.module.nms.type.ToastFrame
import top.maplex.rayskillsystem.api.event.RayBuffToastShowEvent
import top.maplex.rayskillsystem.api.event.RayBuffToastUnShowEvent
import top.maplex.rayskillsystem.utils.buildHologramNear
import top.maplex.rayskillsystem.utils.info
import top.maplex.rayskillsystem.utils.intToRoman
import top.maplex.rayskillsystem.utils.toConsole
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

interface AbstractBuff {

    val id: String

    val name: String

    val info: String

    val icon: Material

    fun toast(target: LivingEntity, level: Int, time: Long) {
        if (target !is Player) {
            return
        }
        val mi = time / 20
        val call = RayBuffToastShowEvent(target, this,time, level, true).callEvent<RayBuffToastShowEvent>()
        if (call.show) {
            target.sendToast(
                icon,
                "${name}${intToRoman(level)}&f (${mi}s)\n${info}".colored(),
                ToastFrame.TASK, ToastBackground.ADVENTURE
            )
            return
        }
        target.info("&a+ ${name}${intToRoman(level)}&f (${mi}s)")
    }

    fun toastLevel(target: LivingEntity, level: Int) {
        if (target !is Player) {
            return
        }
        val call = RayBuffToastUnShowEvent(target, this, level, true).callEvent<RayBuffToastUnShowEvent>()
        if (call.show) {
            target.sendToast(
                icon,
                "&c失去了 &f${name} ${intToRoman(level)}&f\n${info}".colored(),
                ToastFrame.TASK, ToastBackground.ADVENTURE
            )
        }
        target.info("&c- &f${name}${intToRoman(level)}")
    }

    fun onJoin(target: LivingEntity, level: Int, time: Long, from: UUID): Boolean = true

    fun onTick(target: LivingEntity, level: Int, time: Long, from: UUID): Boolean {
        val last = (time - System.currentTimeMillis()) / 1000.0
        val show = String.format("%.2f", (last))
        buildHologramNear(target, listOf("&f${name}${intToRoman(level)} &f(${show}s)".colored()), 15)
        return true
    }

    fun onOver(target: LivingEntity, level: Int, time: Long): Boolean = true


    fun register() {
        BuffManager.buffs[id] = this
        toConsole("注册效果:&f $name", true)
    }
}
