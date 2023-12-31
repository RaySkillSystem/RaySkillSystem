package top.maplex.rayskillsystem.command

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.*
import taboolib.expansion.createHelper
import taboolib.platform.util.giveItem
import top.maplex.rayskillsystem.RaySkillSystem
import top.maplex.rayskillsystem.api.script.reader.ScriptReaderManager
import top.maplex.rayskillsystem.skill.SkillManager
import top.maplex.rayskillsystem.utils.info

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
                execute<CommandSender> { sender, context, argument ->
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
                execute<CommandSender> { sender, context, argument ->
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
        execute<CommandSender> { sender, context, argument ->

        }
    }

    @CommandBody(permission = "rayskill.admin")
    val reload = subCommand {
        execute<CommandSender> { sender, context, argument ->
            RaySkillSystem.reload()
            sender.info("重载完成")
        }
    }

}
