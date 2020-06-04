package org.nocraft.loperd.datasync.spigot.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.nocraft.loperd.datasync.common.DataSyncPlayer;
import org.nocraft.loperd.datasync.common.storage.Storage;
import org.nocraft.loperd.datasync.common.storage.StorageAdapter;
import org.nocraft.loperd.datasync.spigot.BukkitStorageAdapter;
import org.nocraft.loperd.datasync.spigot.DataSyncPluginBukkit;
import org.nocraft.loperd.datasync.spigot.PlayerData;
import org.nocraft.loperd.datasync.spigot.PlayerDataApply;
import org.nocraft.loperd.datasync.spigot.event.PlayerLoadedEvent;
import org.nocraft.loperd.datasync.spigot.manager.LockedPlayerManager;
import org.nocraft.loperd.datasync.spigot.serialization.BukkitSerializer;

import java.util.Optional;
import java.util.UUID;

public class PlayerLoadListener extends DataSyncListenerBukkit {

    private final LockedPlayerManager lockedPlayerManager;
    private final DataSyncPluginBukkit plugin;

    public PlayerLoadListener(DataSyncPluginBukkit plugin) {
        super(plugin);
        this.plugin = plugin;
        this.lockedPlayerManager = plugin.getLockedPlayerManager();
    }

    @EventHandler
    public void onPlayerLoaded(PlayerLoadedEvent e) {
        Player p = e.getPlayer();

        this.lockedPlayerManager.remove(p.getUniqueId());

        plugin.getLogger().info(
                String.format("Successfully apply PlayerData for player %s:%s",
                        p.getName(),
                        p.getUniqueId())
        );
    }
}
