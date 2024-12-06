package com.sk89q.worldedit.fabric.items;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

import java.util.Random;

public class ThunderboltBlade extends SwordItem {

    public ThunderboltBlade(Item.Properties properties) {
        super(Tiers.NETHERITE, properties);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int tickCount) {
        if (!level.isClientSide && livingEntity instanceof Player) {
            ServerLevel serverLevel = (ServerLevel) level;
            Player player = (Player) livingEntity;
            Random random = new Random();

            for (int i = 0; i < 3; i++) {
                int xOffset = random.nextInt(10) - 5;
                int zOffset = random.nextInt(10) - 5;
                BlockPos lightningPos = player.blockPosition().offset(xOffset, 0, zOffset);

                LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create(serverLevel);
                if (lightningBolt != null) {
                    lightningBolt.moveTo(lightningPos.getX(), lightningPos.getY(), lightningPos.getZ());
                    serverLevel.addFreshEntity(lightningBolt);
                }
            }
        }
        super.onUseTick(level, livingEntity, itemStack, tickCount);
    }
}
