package org.meadowhawk.datastore.sqllite.dao

import com.j256.ormlite.dao.BaseDaoImpl
import com.j256.ormlite.support.ConnectionSource
import org.meadowhawk.entity.Group

import java.sql.SQLException

class GroupDao extends BaseDaoImpl<Group, Integer> {
    GroupDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Group)
    }

}
