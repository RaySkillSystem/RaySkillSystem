package top.maplex.rayskillsystem.skill.tools.summoned.module

import ink.ptms.adyeshach.core.bukkit.BukkitPose
import ink.ptms.adyeshach.core.entity.type.AdyMob
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.api.script.auto.InputEngine
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.Damage
import top.maplex.rayskillsystem.skill.tools.summoned.expand.Follow
import top.maplex.rayskillsystem.skill.tools.summoned.impl.SummonedAdyeshach
import top.maplex.rayskillsystem.utils.cooldown.CooldownAPI

@InputEngine("SummonedAttackModule")
object SummonedAttackModule {

    fun run(summoned: SummonedAdyeshach, damageDistance: Double, target: LivingEntity, value: Double): Boolean {
        val entity = summoned.entity
        val player = summoned.player ?: return false
        summoned.move(getPos(target.location))
        summoned.entity.controllerLookAt(target)
        (entity as? AdyMob)?.setAgressive(true)
        if (CooldownAPI.check(player, "Summoned_${entity.uniqueId}")) {
            return false
        }
        if (entity is Follow) {
            summoned.follow = false
        }
        if (summoned.getLocation().distance(target.location) < damageDistance) {
            Damage.damage(player, target, value)
            if (target.isDead) {
                if (entity is Follow) {
                    summoned.follow = true
                    summoned.followEval(summoned, player.location)
                }
            }
            (entity as? AdyMob)?.setAgressive(false)
            entity.setPose(BukkitPose.SPIN_ATTACK)
            CooldownAPI.set(player, "Summoned_${entity.uniqueId}", 20)
        }
        return true
    }

    private fun getPos(source: Location): Location {
        return source.clone()
            .add(source.clone().direction.normalize().setY(0).multiply((-1..1).random().toDouble()))
            .add(source.clone().apply { yaw += 90 }.direction.normalize().setY(0).multiply((-1..1).random().toDouble()))
    }

}
