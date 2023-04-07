package top.maplex.rayskillsystem.utils

import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common5.Baffle
import taboolib.module.chat.colored

/**
 * LoggerUtils
 * @author Ray_Hughes
 * @Time 2022/1/26
 * @since 1.0
 */



/**
 * 给目标玩家发送一些消息 (提示)
 */
fun Player.info(vararg block: String) {
    block.forEach {
        toInfo(this, it)
    }
}

/**
 * 发送中中央屏幕提示
 */
fun Player.infoTitle(info: String, sub: String) {
    this.sendTitle(info.colored(), sub.colored(), 10, 25, 10)
}

/**
 * 给目标玩家发送一些消息 (警告)
 */
fun Player.error(vararg block: String) {
    block.forEach {
        toError(this, it)
    }
}
fun CommandSender.error(vararg block: String) {
    block.forEach {
        toError(this, it)
    }
}

fun CommandSender.info(vararg block: String) {
    block.forEach {
        toInfo(this, it)
    }
}

/**
 * 给管理者发送DEBUG信息
 */
fun debug(vararg block: String) {
    if (Bukkit.getPlayerExact("Ray_Hughes") != null) {
        block.forEach {
            toError(Bukkit.getPlayerExact("Ray_Hughes")!!, it)
        }
    }
}

/**
 * 发送信息
 */
fun toInfo(sender: CommandSender, message: String) {
    sender.sendMessage("§8[§a 一隅烂柯 §8] §7${message.colored()}")
    if (sender is Player && !cooldown.hasNext(sender.name)) {
        sender.playSound(sender.location, Sound.UI_BUTTON_CLICK, 1f, (1..2).random().toFloat())
    }
}

/**
 * 发送信息
 */
fun toError(sender: CommandSender, message: String) {
    sender.sendMessage("§8[§4 一隅烂柯 §8] §7${message.colored()}")
    if (sender is Player && !cooldown.hasNext(sender.name)) {
        sender.playSound(sender.location, Sound.ENTITY_VILLAGER_NO, 1f, (1..2).random().toFloat())
    }
}

/**
 * 发送信息
 */
fun toDone(sender: CommandSender, message: String) {
    sender.sendMessage("§8[§6 一隅烂柯 §8] §7${message.colored()}")
    if (sender is Player && !cooldown.hasNext(sender.name)) {
        sender.playSound(sender.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, (1..2).random().toFloat())
    }
}

/**
 * 发送信息到后台
 */
fun toConsole(message: String) {
    Bukkit.getConsoleSender().sendMessage("§8[§e 一隅烂柯 §8] §7${message.colored()}")
}

/**
 * 音效的一个CD 防止噪音
 */
val cooldown = Baffle.of(100)