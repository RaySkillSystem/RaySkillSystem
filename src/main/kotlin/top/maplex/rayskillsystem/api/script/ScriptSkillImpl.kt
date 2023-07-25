package top.maplex.rayskillsystem.api.script

import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.api.reader.ScriptReaderManager
import top.maplex.rayskillsystem.skill.AbstractSkill

data class ScriptSkillImpl(
    override val name: String,
    override val type: String
) : AbstractSkill {

    init {
        register()
    }

    override fun getCooldown(livingEntity: LivingEntity, level: Int): Long {
        return runCatching {
            ScriptReaderManager.scriptManager.callFunction(name, "getCooldown", livingEntity, level)
        }.getOrNull() as? Long ?: 0
    }

    override fun onPreRun(livingEntity: LivingEntity, level: Int): Boolean {
        return runCatching {
            ScriptReaderManager.scriptManager.callFunction(name, "onPreRun", livingEntity, level)
        }.getOrNull() as? Boolean ?: true
    }

    override fun onRun(livingEntity: LivingEntity, level: Int): Boolean {
        return runCatching {
            ScriptReaderManager.scriptManager.callFunction(name, "onRun", livingEntity, level)
        }.getOrNull() as? Boolean ?: true
    }

    override fun onOver(livingEntity: LivingEntity, level: Int): Boolean {
        return runCatching {
            ScriptReaderManager.scriptManager.callFunction(name, "onOver", livingEntity, level)
        }.getOrNull() as? Boolean ?: true
    }

    override fun onCondition(livingEntity: LivingEntity, level: Int): Boolean {
        return runCatching {
            ScriptReaderManager.scriptManager.callFunction(name, "onCondition", livingEntity, level)
        }.getOrNull() as? Boolean ?: true
    }


}
