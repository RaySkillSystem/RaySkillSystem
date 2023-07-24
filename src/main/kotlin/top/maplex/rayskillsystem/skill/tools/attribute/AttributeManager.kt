package top.maplex.rayskillsystem.skill.tools.attribute

import org.bukkit.entity.Player

object AttributeManager {

    var attributes = object : AbstractAttribute {

        override val name: String = "默认属性"

        override fun getCooldown(player: Player): Double {
            return 1.0
        }
    }

}
