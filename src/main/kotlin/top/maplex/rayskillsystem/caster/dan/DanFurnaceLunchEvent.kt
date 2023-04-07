package top.maplex.rayskillsystem.caster.dan

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import taboolib.platform.type.BukkitProxyEvent

class DanFurnaceLunchEvent(
    val player: Player,
    val danFurnace: ItemStack,
    val value: ItemStack,
    val type: String,
    val interEvent: PlayerInteractEvent,
    //CARROT_ON_A_STICK
) : BukkitProxyEvent()