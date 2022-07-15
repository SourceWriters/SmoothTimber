package net.sourcewriters.smoothtimber.core.listener;

import java.util.UUID;

import com.syntaxphoenix.avinity.module.ModuleWrapper;
import com.syntaxphoenix.avinity.module.extension.Extension;

import net.sourcewriters.smoothtimber.api.module.SmoothTimberModule;
import net.sourcewriters.smoothtimber.api.platform.ISmoothTimberPlatform;
import net.sourcewriters.smoothtimber.api.platform.event.PlatformEntityChangeBlockEvent;
import net.sourcewriters.smoothtimber.api.platform.event.manager.IPlatformEventListener;
import net.sourcewriters.smoothtimber.api.platform.event.manager.PlatformEventListener;
import net.sourcewriters.smoothtimber.api.platform.world.IPlatformWorld;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformEntity;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformFallingBlock;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformInventory;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformItem;
import net.sourcewriters.smoothtimber.api.util.math.Vector3d;
import net.sourcewriters.smoothtimber.core.CoreConstant;

@Extension
public final class BlockFallListener implements IPlatformEventListener {

    private final ISmoothTimberPlatform platform;

    public BlockFallListener(ModuleWrapper<SmoothTimberModule> module) {
        this.platform = module.getModule().getApi().getPlatform();
    }

    @PlatformEventListener
    public void onBlockFall(PlatformEntityChangeBlockEvent event) {
        IPlatformEntity rawEntity = event.getEntity();
        if (!(rawEntity instanceof IPlatformFallingBlock)) {
            return;
        }
        IPlatformFallingBlock entity = (IPlatformFallingBlock) rawEntity;
        int itemAmount = entity.getData().getMetaData().getOrDefault(CoreConstant.FALL_DATA_KEY, -1);
        if (itemAmount < 0) {
            return;
        }
        if (itemAmount != 0) {
            UUID target = entity.getData().getMetaData().getUUID(CoreConstant.FALL_COLLECT_KEY);
            if (target != null) {
                IPlatformPlayer player = platform.getPlayer(target);
                if (player != null) {
                    dropItem(entity.getWorld(), entity.getPosition(), entity.getItem().clone(), itemAmount, player.getInventory());
                    return;
                }
            }
            dropItem(entity.getWorld(), entity.getPosition(), entity.getItem().clone(), itemAmount, null);
        }
        event.setCancelled(true);
    }

    private void dropItem(IPlatformWorld world, Vector3d position, IPlatformItem sample, int amount, IPlatformInventory inventory) {
        int maxAmount = sample.getMaxAmount();
        if (amount < maxAmount) {
            sample.setAmount(amount);
            if (inventory != null) {
                inventory.add(sample);
            } else {
                world.dropItem(position, sample, true);
            }
            return;
        }
        while (amount > 0) {
            int current = amount;
            if (current > maxAmount) {
                current = maxAmount;
            }
            amount -= current;
            IPlatformItem item = sample.clone();
            item.setAmount(current);
            if (inventory != null) {
                inventory.add(item);
            } else {
                world.dropItem(position, item, true);
            }
        }
    }

}
