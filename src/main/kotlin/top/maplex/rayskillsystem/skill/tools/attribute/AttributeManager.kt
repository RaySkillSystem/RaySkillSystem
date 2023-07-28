package top.maplex.rayskillsystem.skill.tools.attribute

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import top.maplex.rayskillsystem.api.script.auto.InputEngine

@InputEngine("AttributeManager")
object AttributeManager {

    var attributes = object : AbstractAttribute {

        override val name: String = "默认属性"

        override fun getCooldown(livingEntity: LivingEntity): Double {
            return 1.0
        }
    }

    fun getCooldown(livingEntity: LivingEntity): Double {
        return attributes.getCooldown(livingEntity)
    }

    fun getAttribute(livingEntity: LivingEntity, attribute: String, default: Double = 0.0): Double {
        return attributes.getAttribute(livingEntity, attribute, default)
    }

    fun addAttribute(livingEntity: LivingEntity, attribute: String, value: Double, source: String) {
        attributes.addAttribute(livingEntity, attribute, value, source)
    }

    fun tempAttribute(
        livingEntity: LivingEntity,
        attribute: String,
        value: Double,
        source: String,
        tick: Long,
        force: Boolean = false,
    ) {
        attributes.tempAttribute(livingEntity, attribute, value, source, tick, force)
    }

    fun removeAttribute(livingEntity: LivingEntity, source: String) {
        attributes.removeAttribute(livingEntity, source)
    }

}
