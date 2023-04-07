package top.maplex.rayskillsystem.skill

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import top.maplex.rayskillsystem.utils.toConsole

interface AbstractSkill {

    val name: String

    val type: String

    val cooldown: Long

    fun showItem(player: Player, level: Int): ItemStack

    fun onCondition(player: Player, level: Int): Boolean = true

    fun onPreRun(player: Player, level: Int): Boolean = true

    fun onRun(player: Player, level: Int): Boolean = true

    fun onOver(player: Player, level: Int): Boolean = true

    fun register() {
        SkillManager.skills[name] = this
        toConsole("注册技能:&f $name")
    }

}