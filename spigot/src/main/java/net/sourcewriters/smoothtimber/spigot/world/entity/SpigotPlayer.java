package net.sourcewriters.smoothtimber.spigot.world.entity;

import java.util.Objects;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.sourcewriters.smoothtimber.api.platform.util.STGameMode;
import net.sourcewriters.smoothtimber.api.platform.world.entity.IPlatformPlayer;
import net.sourcewriters.smoothtimber.api.platform.world.inventory.IPlatformInventory;
import net.sourcewriters.smoothtimber.spigot.world.inventory.SpigotImmutableInventory;

public final class SpigotPlayer extends SpigotLivingEntity<IPlatformPlayer, Player> implements IPlatformPlayer {

    public SpigotPlayer(Player entity) {
        super(entity);
    }

    @Override
    public IPlatformInventory getInventory() {
        return new SpigotImmutableInventory(entity.getInventory());
    }

    @Override
    public int getFoodLevel() {
        return entity.getFoodLevel();
    }

    @Override
    public void setFoodLevel(int value) {
        entity.setFoodLevel(value);
    }

    @Override
    public float getSaturation() {
        return entity.getSaturation();
    }

    @Override
    public void setSaturation(float value) {
        entity.setSaturation(value);
    }

    @Override
    public STGameMode getGameMode() {
        return STGameMode.valueOf(entity.getGameMode().name());
    }

    @Override
    public void setGameMode(STGameMode gameMode) {
        entity.setGameMode(GameMode.valueOf(Objects.requireNonNull(gameMode, "GameMode can't be null!").name()));
    }

    @Override
    public void openInventory(IPlatformInventory inventory) {
        entity.openInventory((Inventory) inventory.getHandle());
    }

}
