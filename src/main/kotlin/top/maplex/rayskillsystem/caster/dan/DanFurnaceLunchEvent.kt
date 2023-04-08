package top.maplex.rayskillsystem.caster.dan

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.type.BukkitProxyEvent
import taboolib.platform.util.isAir
import top.maplex.panlingitem.api.PanLingAPI
import top.maplex.panlingitem.api.PanLingStatic
import top.maplex.rayskillsystem.utils.getString

class DanFurnaceLunchEvent(
    val player: Player,
    val danFurnace: ItemStack,
    val value: ItemStack,
    val type: String,
    val interEvent: PlayerInteractEvent,
    //CARROT_ON_A_STICK
) : BukkitProxyEvent()