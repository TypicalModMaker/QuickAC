package dev.isnow.quick.data;

import dev.isnow.quick.check.Check;
import dev.isnow.quick.check.manager.CheckManager;
import dev.isnow.quick.processor.ActionProcessor;
import dev.isnow.quick.processor.PositionProcessor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * This is our PlayerData class, as the name suggests we store data about the player here.
 * Every player should have this in order for the anticheat to function as the entire plugin is based on this.
 */
@Getter
@Setter
public final class PlayerData {
    private final Player player;
    private final UUID uuid;

    private final List<Check> checks = CheckManager.loadChecks(this);

    // Processors
    private final PositionProcessor positionProcessor = new PositionProcessor(this);
    private final ActionProcessor actionProcessor = new ActionProcessor(this);

    public PlayerData(final Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }
}
