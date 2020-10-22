package org.meadowhawk.datastore.sqllite

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.db.DatabaseType
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource
import com.j256.ormlite.table.TableUtils
import groovy.sql.Sql
import org.meadowhawk.entity.Feed
import org.meadowhawk.entity.Group
import org.meadowhawk.entity.ThingData
import org.tomlj.TomlParseResult
import org.tomlj.TomlTable

import static org.meadowhawk.datastore.sqllite.SqlliteConstants.*

class SqlLiteService {
    TomlTable config
    String dbUrl
    JdbcPooledConnectionSource connPool

    SqlLiteService(TomlParseResult config){
        this.config = config.getTable(CONFIG_SQLLITE_TABLE)
        this.dbUrl = this.config.getString("dbPath")
        if(!dbUrl || dbUrl.isEmpty()) {
            dbUrl = "things.db"
            println("DB not configured, setting db to things.db at app root.")
        }

        connPool = new JdbcPooledConnectionSource(DB_PREFIX+dbUrl)

        TableUtils.createTableIfNotExists(connPool, Group)
        TableUtils.createTableIfNotExists(connPool, Feed)
        TableUtils.createTableIfNotExists(connPool, ThingData)
    }

    Sql getSqlConection(String dbUrl){
        Sql.newInstance( DB_PREFIX+dbUrl, SQLLITE_DRIVER )
    }
}
