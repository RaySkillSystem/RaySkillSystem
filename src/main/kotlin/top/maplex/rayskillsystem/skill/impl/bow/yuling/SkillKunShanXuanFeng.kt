package top.maplex.rayskillsystem.skill.impl.bow.yuling

import ink.ptms.adyeshach.core.Adyeshach
import ink.ptms.adyeshach.core.bukkit.BukkitPose
import ink.ptms.adyeshach.core.entity.EntityTypes
import ink.ptms.adyeshach.core.entity.manager.ManagerType
import ink.ptms.adyeshach.core.entity.type.AdyPanda
import ink.ptms.adyeshach.core.entity.type.AdyParrot
import ink.ptms.adyeshach.impl.entity.trait.impl.setTraitTitle
import org.bukkit.entity.Arrow
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Parrot
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.chat.colored
import top.maplex.rayskillsystem.skill.AbstractSkill


object SkillKunShanXuanFeng : AbstractSkill {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "昆山玄凤"

    override val type: String = "弓箭手"

    override val cooldown: Long = 3 * 60 * 20

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {

        val api = Adyeshach.api().getPublicEntityManager(ManagerType.TEMPORARY)
        val entity = api.create(EntityTypes.PARROT, player.location).apply {
            setCustomName("初始化")
            setCustomNameVisible(true)
            setNoGravity(true)
            if (this is AdyParrot) {
                setColor(Parrot.Variant.GRAY)
                setNoGravity(true)
            }
            setTraitTitle(listOf("[${player.name}]", "&e御灵·玄凤").colored())
        }
        SkillYuLing.data.add(
            SummonedYuLingXuanFeng(
                player.uniqueId,
                entity,
                0.0, 1.5, 2.0,
                System.currentTimeMillis() + (120000)
            ).apply {
                damageDistanc = 30.0
            }
        )
        return true
    }


    @SubscribeEvent
    fun damage(event: EntityDamageByEntityEvent) {
        val damager = event.damager as? LivingEntity ?: return
        val player = event.entity as? Player ?: return
        SkillYuLing.data.filter { it.master == player.uniqueId && it is SummonedYuLingXuanFeng }.forEach {
            it.injury(damager, event.damage)
            event.damage -= (event.damage * 0.5)
        }
    }

}