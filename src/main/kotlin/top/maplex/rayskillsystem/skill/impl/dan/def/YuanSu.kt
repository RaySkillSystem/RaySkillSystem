package top.maplex.rayskillsystem.skill.impl.dan.def

import org.bukkit.entity.Player
import ray.mintcat.aboleth.api.AbolethAPI
import ray.mintcat.aboleth.api.AbolethAction
import taboolib.platform.util.countItem
import taboolib.platform.util.takeItem
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.utils.getString

interface YuanSu {

    fun take(player: Player, type: String, amount: Int = 1): Boolean {
        val has = AbolethAPI.get(player.uniqueId, "元素-panling:${type}", "0").toDoubleOrNull() ?: 0.0
        val attribute = PlayerManager.getPlayerData(player).attribute
        val value = attribute.getAttribute(AttributeEnum.ARRAY_SAVE)
        if (value >= (1..100).random()){
            player.sendMessage("§c触发了元素保护，减免了本次元素的消耗")
            return true
        }
        if (has < amount.toDouble()) {
            val num = player.inventory.countItem {
                it.getString("id") == "panling:${type}"
            }
            if (num >= amount) {
                player.inventory.takeItem(amount) {
                    it.getString("id") == "panling:${type}"
                }
                return true
            }
            player.sendMessage("§c元素不足 (${has})")
            return false
        }
        AbolethAPI.edit(player.uniqueId, "元素-panling:${type}", AbolethAction.SUBTRACT, amount.toDouble())
        return true
    }

}