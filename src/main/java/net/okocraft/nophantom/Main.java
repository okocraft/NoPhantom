package net.okocraft.nophantom;

import com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    private void onPhantomPreSpawn(PhantomPreSpawnEvent event) {
        if (event.getSpawningEntity() instanceof Player && event.getSpawningEntity().hasPermission("nophantom.denyspawn")) {
            event.setCancelled(true);
        }
    }
}
