package org.meadowhawk.datastore.sqllite.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import org.meadowhawk.entity.Feed;

import java.sql.SQLException

class FeedDao extends BaseDaoImpl<Feed, Integer> {
    FeedDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Feed)
    }
}
