package top.maplex.rayskillsystem.skill.tools.attribute

import org.bukkit.entity.Player
import top.maplex.rayskillsystem.skill.tools.team.TeamManager
import top.maplex.rayskillsystem.utils.toConsole

interface AbstractAttribute {

    val name: String

    fun getCooldown(player: Player): Double {
        return 1.0
    }

    fun register() {
        AttributeManager.attributes = this
        toConsole("属性系统由 $name 进行接管！")
    }

}
