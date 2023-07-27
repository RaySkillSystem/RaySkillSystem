package top.maplex.rayskillsystem.test;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.maplex.rayskillsystem.skill.AbstractSkill;
import top.maplex.rayskillsystem.utils.auto.RaySkillSystem;

@RaySkillSystem
public class TestJavaSkill implements AbstractSkill {
    @NotNull
    @Override
    public String getName() {
        return "测试Java技能";
    }

    @NotNull
    @Override
    public String getType() {
        return "无";
    }

    @Override
    public long getCooldown(@NotNull LivingEntity livingEntity, int level) {
        return 100;
    }

    @Override
    public boolean onCondition(@NotNull LivingEntity livingEntity, int level) {
        if (livingEntity instanceof Player && ((Player) livingEntity).getLevel() >= 20) {
            return true;
        } else {
            livingEntity.sendMessage("你的等级无法施展如此高深的技能");
            return false;
        }
    }

    @Override
    public boolean onRun(@NotNull LivingEntity livingEntity, int level) {
        System.out.println("技能运行了！");
        return true;
    }

    @NotNull
    @Override
    public ItemStack showItem(@NotNull LivingEntity livingEntity, int level) {
        return AbstractSkill.DefaultImpls.showItem(this, livingEntity, level);
    }

    @Override
    public boolean onPreRun(@NotNull LivingEntity livingEntity, int level) {
        return AbstractSkill.DefaultImpls.onPreRun(this, livingEntity, level);
    }

    @Override
    public boolean onOver(@NotNull LivingEntity livingEntity, int level) {
        return AbstractSkill.DefaultImpls.onOver(this, livingEntity, level);
    }

    @Override
    public void register() {
        AbstractSkill.DefaultImpls.register(this);
    }
}
