package org.nocraft.loperd.datasync.spigot.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.nocraft.loperd.datasync.spigot.DataSyncListenerBukkit;
import org.nocraft.loperd.datasync.spigot.DataSyncPluginBukkit;
import org.nocraft.loperd.datasync.spigot.manager.LockedPlayerManager;

import java.util.UUID;

public class LockedPlayerListener extends DataSyncListenerBukkit {

    private LockedPlayerManager locked;

    public LockedPlayerListener(DataSyncPluginBukkit plugin) {
        super(plugin);
        this.locked = plugin.getLockedPlayerManager();
    }

    @Override
    public void shutdown() {
        this.locked.clear();
        this.locked = null;

        super.shutdown();
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event) {
        handlePlayer(event, event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInventoryInteract(InventoryInteractEvent event) {
        handlePlayer(event, event.getWhoClicked().getUniqueId());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        handlePlayer(event, event.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        handlePlayer(event, event.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerPickupItemEvent(PlayerAttemptPickupItemEvent event) {
        handlePlayer(event, event.getPlayer());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerDamagedEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        handlePlayer(event, (Player) event.getEntity());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerDamagedEvent(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        handlePlayer(event, (Player) event.getDamager());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        handlePlayer(event, event.getPlayer());
    }

    private void handlePlayer(Cancellable event, Player player) {
        if (this.locked.isLocked(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    private void handlePlayer(Cancellable event, UUID playerId) {
        if (this.locked.isLocked(playerId)) {
            event.setCancelled(true);
        }
    }
}
