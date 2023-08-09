package net.okocraft.nophantom;

import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    private void onPhantomPreSpawn(@NotNull PhantomPreSpawnEvent event) {
        if (event.getSpawningEntity() instanceof Player target
                && (target.hasPermission("nophantom.denyspawn") || isMultiDenyPlayerNear(target))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onEntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent event) {
        if (event.getEntity() instanceof Phantom phantom && event.getTarget() instanceof Player target
                && isMultiDenyPlayerNear(target)) {
            phantom.remove();
        }
    }

    private static boolean isMultiDenyPlayerNear(Player origin) {
        return !origin.getWorld().getNearbyPlayers(
                origin.getLocation(), 50, p -> p.hasPermission("nophantom.denyspawn.multi")
        ).isEmpty();
    }
}
