package org.nocraft.loperd.datasync.spigot;

import org.nocraft.loperd.datasync.common.scheduler.AbstractJavaScheduler;
import org.nocraft.loperd.datasync.common.scheduler.SchedulerAdapter;

import java.util.concurrent.Executor;

public class BukkitSchedulerAdapter extends AbstractJavaScheduler implements SchedulerAdapter {

    protected Executor sync;

    public BukkitSchedulerAdapter(DataSyncBootstrapBukkit plugin) {
        this.sync = r -> plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, r);
    }

    @Override
    public Executor sync() {
        return this.sync;
    }
}
