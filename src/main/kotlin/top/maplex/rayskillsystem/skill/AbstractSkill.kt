package top.maplex.rayskillsystem.skill

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.platform.util.buildItem
import top.maplex.rayskillsystem.utils.set
import top.maplex.rayskillsystem.utils.toConsole

interface AbstractSkill {

    val name: String

    val type: String

    val cooldown: Long

    fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${this@AbstractSkill.name}"
            colored()
        }.apply {
            set("RaySkill.type", this@AbstractSkill.name)
        }
    }

    //施法条件
    fun onCondition(player: Player, level: Int): Boolean = true

    //运行前执行
    fun onPreRun(player: Player, level: Int): Boolean = true

    //运行代码
    fun onRun(player: Player, level: Int): Boolean = true

    //运行后执行
    fun onOver(player: Player, level: Int): Boolean = true

    //注册
    fun register() {
        SkillManager.skills[name] = this
        toConsole("注册技能:&f $name")
    }

}
