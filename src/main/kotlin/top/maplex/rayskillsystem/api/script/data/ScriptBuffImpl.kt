package top.maplex.rayskillsystem.api.script.data

import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.api.script.reader.ScriptReaderManager
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff
import java.util.*

data class ScriptBuffImpl(
    override val name: String,
    override val id: String,
    override val info: String,
    override val icon: Material
) : AbstractBuff {

    init {
        register()
    }

    override fun onJoin(target: LivingEntity, level: Int, time: Long, from: UUID): Boolean {
        return runCatching {
            ScriptReaderManager.scriptManager.callFunction(name, "onJoin", target, level, time, from)
        }.getOrNull() as? Boolean ?: true
    }

    override fun onTick(target: LivingEntity, level: Int, time: Long, from: UUID): Boolean {
        val show = ScriptReaderManager.scriptManager.getVariable(name, "show") as? Boolean ?: false
        if (show) {
            super.onTick(target, level, time, from)
        }
        return runCatching {
            ScriptReaderManager.scriptManager.callFunction(name, "onTick", target, level, time, from)
        }.getOrNull() as? Boolean ?: true
    }

    override fun onOver(target: LivingEntity, level: Int, time: Long): Boolean {
        return runCatching {
            ScriptReaderManager.scriptManager.callFunction(name, "onOver", target, level, time)
        }.getOrNull() as? Boolean ?: true
    }

    override fun toast(target: LivingEntity, level: Int, time: Long) {
        runCatching {
            ScriptReaderManager.scriptManager.callFunction(name, "toast", target, level, time)
        }.getOrNull() ?: super.toast(target, level, time)
    }

    override fun toastLevel(target: LivingEntity, level: Int) {
        runCatching {
            ScriptReaderManager.scriptManager.callFunction(name, "toastLevel", target, level)
        }.getOrNull() ?: super.toastLevel(target, level)
    }


}
