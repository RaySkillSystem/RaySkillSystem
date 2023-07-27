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

}
