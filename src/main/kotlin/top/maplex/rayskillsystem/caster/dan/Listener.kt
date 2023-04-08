package top.maplex.rayskillsystem.caster.dan

import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.isAir
import top.maplex.panlingitem.api.PanLingAPI
import top.maplex.panlingitem.api.PanLingStatic
import top.maplex.rayskillsystem.utils.getString

object Listener {

    @SubscribeEvent
    fun lunch(event: PlayerInteractEvent) {
        if (event.hand == EquipmentSlot.OFF_HAND) {
            return
        }
        if (event.player.inventory.itemInMainHand.isAir || event.player.inventory.itemInOffHand.isAir) {
            return
        }
        if (PanLingAPI.getPlayerData(event.player, PanLingStatic.JOB) != "2") {
            return
        }
        val value: ItemStack
        val furnace = if (event.player.inventory.itemInMainHand.type == Material.CARROT_ON_A_STICK) {
            value = event.player.inventory.itemInOffHand
            event.player.inventory.itemInMainHand
        } else {
            value = event.player.inventory.itemInMainHand
            event.player.inventory.itemInOffHand
        }
        if (!furnace.getString("id", "null").startsWith("panling:furnace")) {
            return
        }
        val key = value.getString("Dan.type")
        DanFurnaceLunchEvent(event.player, furnace, value, key, event).call()

    }

}