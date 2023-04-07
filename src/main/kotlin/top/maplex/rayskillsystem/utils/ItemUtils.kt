package top.maplex.rayskillsystem.utils

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import taboolib.common.reflect.Reflex.Companion.setProperty
import taboolib.module.nms.ItemTagData
import taboolib.module.nms.ItemTagList
import taboolib.module.nms.ItemTagType
import taboolib.module.nms.getItemTag
import taboolib.platform.util.isAir


fun ItemStack?.ifAir(): ItemStack? {
    if (this == null) {
        return null
    }
    if (this.isAir) {
        return null
    }
    if (this.type == Material.AIR) {
        return null
    }
    return this
}

fun ItemStack.getString(key: String, def: String = "null"): String {
    if (this.isAir) {
        return def
    }
    if (key.contains(".")) {
        return this.getItemTag().getDeepOrElse(key, ItemTagData(def)).asString()
    }
    return this.getItemTag().getOrElse(key, ItemTagData(def)).asString()
}

fun ItemStack.getInt(key: String, def: Int = -1): Int {
    if (this.isAir) {
        return def
    }
    if (key.contains(".")) {
        return this.getItemTag().getDeepOrElse(key, ItemTagData(def)).asInt()
    }
    return this.getItemTag().getOrElse(key, ItemTagData(def)).asInt()
}

fun ItemStack.getDouble(key: String, def: Double = -1.0): Double {
    if (this.isAir) {
        return def
    }
    if (key.contains(".")) {
        return this.getItemTag().getDeepOrElse(key, ItemTagData(def)).asDouble()
    }
    return this.getItemTag().getOrElse(key, ItemTagData(def)).asDouble()
}

fun ItemStack.getStringList(key: String): List<String> {
    if (this.isAir) {
        return listOf()
    }
    if (key.contains(".")) {
        return this.getItemTag().getDeep(key)?.asList()?.map { it.asString() } ?: listOf()
    }
    return this.getItemTag()[key]?.asList()?.map { it.asString() } ?: listOf()
}

fun ItemStack.getDoubleList(key: String): List<Double> {
    if (this.isAir) {
        return listOf()
    }
    if (key.contains(".")) {
        return this.getItemTag().getDeep(key)?.asList()?.map { it.asDouble() } ?: listOf()
    }
    return this.getItemTag()[key]?.asList()?.map { it.asDouble() } ?: listOf()
}

fun ItemStack.getIntList(key: String): List<Int> {
    if (this.isAir) {
        return listOf()
    }
    if (key.contains(".")) {
        return this.getItemTag().getDeep(key)?.asList()?.map { it.asInt() } ?: listOf()
    }
    return this.getItemTag()[key]?.asList()?.map { it.asInt() } ?: listOf()
}

fun ItemStack.set(key: String, value: Any?) {
    val tag = getItemTag()
    if (key.contains(".")) {
        if (value == null) {
            tag.removeDeep(key)
        } else {
            tag.putDeep(key, value)
        }
    } else {
        if (value == null) {
            tag.remove(key)
        } else {
            tag.put(key, value)
        }
    }
    tag.saveTo(this)
}

fun <T> ItemStack.setList(key: String, value: T?, type: ItemTagType) {
    val tag = getItemTag()
    val temp = ItemTagList()
    if (value !is List<*>? || value !is ArrayList<*>?) {
        set(key, value)
        return
    }
    if (value.isNullOrEmpty()) {
        if (key.contains(".")) {
            tag.removeDeep(key)
        } else {
            tag.remove(key)
        }
        return
    }
    value!!.forEach {
        val data = ItemTagData("")
        data.setProperty("data", it)
        data.setProperty("type", type)
        temp.add(data)
    }
    if (key.contains(".")) {
        tag.putDeep(key, temp)
    } else {
        tag[key] = temp
    }
    tag.saveTo(this)
}