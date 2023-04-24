package net.okocraft.nophantom;

import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    private void onPhantomPreSpawn(PhantomPreSpawnEvent event) {
        if (!(event.getSpawningEntity() instanceof Player target)) {
            return;
        }

        if (target.hasPermission("nophantom.denyspawn")) {
            event.setCancelled(true);
            return;
        }

        if (target.hasPermission("nophantom.denyspawn.multi") || isMultiDenyPlayerNear(target)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onEntityTargetLivingEntityEvent(EntityTargetLivingEntityEvent event) {
        if (event.getEntity() instanceof Phantom && event.getTarget() instanceof Player target) {
            if (target.hasPermission("nophantom.denyspawn.multi") || isMultiDenyPlayerNear(target)) {
                event.getEntity().remove();
            }
        }
    }

    private static boolean isMultiDenyPlayerNear(Player origin) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getWorld().equals(origin.getWorld())) {
                continue;
            }
            if (!player.hasPermission("nophantom.denyspawn.multi")) {
                continue;
            }
            if (player.getLocation().distanceSquared(origin.getLocation()) < 50 * 50) {
                return true;
            }
        }

        return false;
    }
}
