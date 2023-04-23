package top.maplex.rayskillsystem.skill.impl.bow.yuling

import ink.ptms.adyeshach.core.Adyeshach
import ink.ptms.adyeshach.core.entity.EntityTypes
import ink.ptms.adyeshach.core.entity.manager.ManagerType
import ink.ptms.adyeshach.impl.entity.trait.impl.setTraitTitle
import org.bukkit.entity.Arrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.chat.colored
import top.maplex.rayskillsystem.skill.AbstractSkill


object SkillYuLing : AbstractSkill {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "御灵术"

    override val type: String = "弓箭手"

    override val cooldown: Long = 20 * 60

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    val data = ArrayList<SummonedYuLingCat>()

    override fun onRun(player: Player, level: Int): Boolean {
        val api = Adyeshach.api().getPublicEntityManager(ManagerType.TEMPORARY)
        val entity = api.create(EntityTypes.CAT, player.location).apply {
            setCustomName("初始化")
            setCustomNameVisible(true)
            setNoGravity(true)
            setTraitTitle(listOf("[${player.name}]", "御灵·猫").colored())
            moveSpeed = 0.6
        }
        data.add(
            SummonedYuLingCat(
                player.uniqueId,
                entity,
                1.0, 1.0, 1.0,
                System.currentTimeMillis() + (1000 * 120),
                damageDistanc = 2.0
            )
        )
        return true
    }

    @SubscribeEvent
    fun damage(event: EntityDamageByEntityEvent) {
        val arrow = event.damager
        val livingEntity = event.entity
        if (livingEntity !is LivingEntity || livingEntity is Player) {
            return
        }
        if (arrow is Arrow) {
            val player = arrow.shooter as? Player ?: return
            data.filter { it.master == player.uniqueId }.forEach {
                it.target = livingEntity
            }
            return
        }
        val player = event.damager as? Player ?: return
        data.filter { it.master == player.uniqueId }.forEach {
            it.target = livingEntity
        }
    }

    fun getPlayerYuLing(player: Player): List<SummonedYuLingCat> {
        return data.filter { it.master == player.uniqueId }
    }
}