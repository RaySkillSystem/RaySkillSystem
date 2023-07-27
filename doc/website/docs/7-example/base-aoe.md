---
sidebar_position: 1
---

# AOE伤害技能-友军判断

### 对范围的所有人造成伤害 自己除外

```javascript
var script_type = "Skill";

function getCooldown(livingEntity, level) {
    return 20;
}

function onRun(livingEntity, level) {
    var array = TargetRange.get(livingEntity, 10.0, false);
    array.forEach(function (target) {
        if (target != null) {
            Damage.damage(livingEntity, target, 10);
        }
    });

    return true;
}

```

### 对范围内的敌人造成伤害

使用Team 增加队友判断

```javascript
function onRun(livingEntity, level) {
    var array = TargetRange.get(livingEntity, 10.0, false);
    array.forEach(function (target) {
        if (target != null && Team.canAttack(livingEntity, target)) {
            Damage.damage(livingEntity, target, 10);
        }
    });

    return true;
}
```

### 对范围内的所有其他人造成伤害 没有人就无法释放技能

```javascript
function onRun(livingEntity, level) {
    var array = TargetRange.get(livingEntity, 10.0, false);
    if (array.size() <= 0) {
        return false;
    }
    array.forEach(function (target) {
        if (target != null) {
            Damage.damage(livingEntity, target, 10);
        }
    });

    return true;
}

```
