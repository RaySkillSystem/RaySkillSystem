package top.maplex.rayskillsystem.utils

import org.bukkit.entity.EntityType

infix fun EntityType.isIn(string: String): Boolean {
    return when (string) {
        "亡灵" -> {
            this == EntityType.ZOMBIE || this == EntityType.ZOMBIE_HORSE || this == EntityType.ZOMBIE_VILLAGER ||
                    this == EntityType.SKELETON || this == EntityType.SKELETON_HORSE || this == EntityType.WITHER_SKELETON
        }

        else -> {
            true
        }
    }
}

//Int - > 罗马 IV XXI
fun intToRoman(numb: Int): String {
    var num = numb
    val nums = intArrayOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    val romans = arrayOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    val stringBuilder = StringBuilder()
    var index = 0
    while (index < 13) {
        while (num >= nums[index]) {
            stringBuilder.append(romans[index])
            num -= nums[index]
        }
        index++
    }
    return stringBuilder.toString()
}