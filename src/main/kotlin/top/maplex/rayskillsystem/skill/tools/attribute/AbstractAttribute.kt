package top.maplex.rayskillsystem.skill.tools.attribute

import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.utils.toConsole

interface AbstractAttribute {

    val name: String

    fun getCooldown(livingEntity: LivingEntity): Double {
        return 1.0
    }

    fun getAttribute(livingEntity: LivingEntity, attribute: String, default: Double = 0.0): Double {
        return 1.0
    }

    fun addAttribute(livingEntity: LivingEntity, attribute: String, value: Double, source: String) {
        return
    }

    fun tempAttribute(
        livingEntity: LivingEntity,
        attribute: String,
        value: Double,
        source: String,
        tick: Long,
        force: Boolean = false,
    ) {
        return
    }

    fun removeAttribute(livingEntity: LivingEntity, source: String) {
        return
    }

    fun register() {
        AttributeManager.attributes = this
        toConsole("属性系统由 $name 进行接管！", true)
    }

}
