package top.maplex.rayskillsystem.command

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.module.chat.colored
import taboolib.module.nms.sendToast
import taboolib.module.nms.type.ToastBackground
import taboolib.module.nms.type.ToastFrame
import taboolib.platform.util.giveItem
import top.maplex.rayskillsystem.caster.SkillBarManager
import top.maplex.rayskillsystem.skill.SkillManager

@CommandHeader("RaySkillSystem", aliases = ["rss"], permission = "rayskill.use")
object Command {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }


    //rss cast Skill Target
    @CommandBody(permission = "rayskill.admin")
    val cast = subCommand {
        dynamic("skill") {
            suggestion<CommandSender> { sender, context ->
                SkillManager.skills.keys.toList()
            }
            player("target") {
                execute<Player> { sender, context, argument ->
                    val player = Bukkit.getPlayer(context.player("target").uniqueId) ?: return@execute
                    SkillManager.eval(player, context["skill"], 1)
                }
            }
            execute<Player> { sender, context, argument ->
                SkillManager.eval(sender, context["skill"], 1)
            }
        }
    }

    @CommandBody(permission = "rayskill.admin")
    val castItem = subCommand {
        dynamic("skill") {
            suggestion<CommandSender> { sender, context ->
                SkillManager.skills.keys.toList()
            }
            player("target") {
                execute<Player> { sender, context, argument ->
                    val player = Bukkit.getPlayer(context.player("target").uniqueId) ?: return@execute
                    SkillManager.getSkill(context["skill"])?.showItem(player, 1)?.let {
                        player.giveItem(it)
                    }
                }
            }
            execute<Player> { player, context, argument ->
                SkillManager.getSkill(context["skill"])?.showItem(player, 1)?.let {
                    player.giveItem(it)
                }
            }
        }
    }

    @CommandBody(permission = "rayskill.admin")
    val test = subCommand {
        execute<Player> { sender, context, argument ->
            sender.sendToast(
                Material.APPLE,
                "&a乱撒清荷&f (8s)\n&e持续期间炼化元素无冷却".colored(),
                ToastFrame.TASK, ToastBackground.END
            )
        }
    }

}
