package org.nocraft.loperd.datasync.common.storage.implementation.nosql;

import com.lambdaworks.redis.api.StatefulRedisConnection;
import org.nocraft.loperd.datasync.common.plugin.DataSyncPlugin;

public interface ConnectionFactory <DT> {
    String getImplementationName();

    void init(DataSyncPlugin plugin);

    void shutdown();

    StatefulRedisConnection<String, String> getConnection();
}
