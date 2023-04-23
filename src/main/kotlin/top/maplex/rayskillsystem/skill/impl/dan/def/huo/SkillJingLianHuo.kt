package top.maplex.rayskillsystem.skill.impl.dan.def.huo

import ink.ptms.adyeshach.core.Adyeshach
import ink.ptms.adyeshach.core.entity.EntityTypes
import ink.ptms.adyeshach.core.entity.manager.ManagerType
import ink.ptms.adyeshach.core.entity.type.AdyFox
import ink.ptms.adyeshach.impl.entity.trait.impl.setTraitTitle
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.chat.colored
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.platform.util.toProxyLocation
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.caster.dan.DanFurnaceLunchEvent
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.impl.bow.yuling.SummonedYuLingCat
import top.maplex.rayskillsystem.skill.impl.dan.def.YuanSu
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import java.util.*


object SkillJingLianHuo : AbstractSkill, YuanSu, AbstractDanCast {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "精炼火元素"

    override val type: String = "炼丹师"

    override val cooldown: Long = 20 * 30

    override val itemId: String = "panling:refined_fire"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "refined_fire")
    }

    val data = mutableMapOf<UUID, SummonedHuoLing>()

    override fun onRun(player: Player, level: Int): Boolean {
        val api = Adyeshach.api().getPublicEntityManager(ManagerType.TEMPORARY)
        val entity = api.create(EntityTypes.FOX, player.location).apply {
            if (this is AdyFox) {
                setCustomName("初始化")
                setCustomNameVisible(true)
                setNoGravity(true)
                setBaby(true)
                setSleeping(true)
                setTraitTitle(listOf("[${player.name}]", "&4火灵·幽").colored())
                moveSpeed = 0.0
            }
        }
        data[player.uniqueId] = SummonedHuoLing(
            player.uniqueId,
            entity,
            0.0, 0.0, 0.0,
            System.currentTimeMillis() + (1000 * 20),
            damageDistanc = 8.0,
            follow = false
        )
        Circle(player.location.toProxyLocation(), 8.0, 1.0, 1,
            object : ParticleSpawner {
                override fun spawn(location: taboolib.common.util.Location) {
                    spawnColor(1, location.add(0.0, 1.0, 0.0), 158, 42, 34, 1F)
                }
            }).show()
        return true
    }


}