package top.maplex.rayskillsystem.skill.tools.summoned

import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import java.util.UUID

open class SummonedEntity(
    open val type: String,
    open val master: UUID,
    open var delete: Boolean = false,
) {

    init {
        init()
    }

    private fun init() {
        SummonedManager.data.add(this)
    }

    open fun move(destination: Location): Boolean {
        return true
    }

    open fun teleport(destination: Location): Boolean {
        return true
    }

    open fun onUpdate(): Boolean {
        if (delete) {
            SummonedManager.data.remove(this)
        }
        return true
    }

    open fun attack(target: LivingEntity, value: Double): Boolean {
        return true
    }

    open fun injury(source: LivingEntity, value: Double): Boolean {
        return true
    }

    open fun getLocation(): Location? {
        return null
    }

    open fun delete() {
        return
    }


}