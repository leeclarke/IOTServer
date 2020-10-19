package org.meadowhawk.datastore.sqllite.dao

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import org.meadowhawk.entity.ThingData

import java.sql.SQLException

class ThingDataDao extends BaseDaoImpl<ThingData, Integer> {
    ThingDataDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ThingData)
    }
}
