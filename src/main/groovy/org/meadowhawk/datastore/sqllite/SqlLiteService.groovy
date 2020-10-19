package org.meadowhawk.datastore.sqllite

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.db.DatabaseType
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import groovy.sql.Sql
import org.meadowhawk.entity.Feed
import org.meadowhawk.entity.Group
import org.meadowhawk.entity.ThingData
import org.tomlj.TomlParseResult
import org.tomlj.TomlTable

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.meadowhawk.datastore.sqllite.SqlliteConstants.*

class SqlLiteService {
    TomlTable config
    Sql sql
    String dbUrl
    JdbcPooledConnectionSource connPool

    SqlLiteService(TomlParseResult config){
        this.config = config.getTable(CONFIG_SQLLITE_TABLE)
        this.dbUrl = this.config.getString("dbPath")
        if(!dbUrl || dbUrl.isEmpty()) {
            dbUrl = "things.db"
            println("DB not configured, setting db to things.db at app root.")
        }
        //this.sql = getSqlConection(this.dbUrl )

        connPool = new JdbcPooledConnectionSource(DB_PREFIX+dbUrl)

        TableUtils.createTableIfNotExists(connPool, Group)
        TableUtils.createTableIfNotExists(connPool, Feed)
        TableUtils.createTableIfNotExists(connPool, ThingData)
    }

//    Group saveGroup(Group group){
//        setUpDatabase()
//        Dao<Group, Integer> groupDao = DaoManager.createDao(connPool, Group)
//        groupDao.createOrUpdate(group)
//        group
//    }



//    def setUpDatabase(){
//        def metadata = sql.connection.getMetaData()
//        def groupsTable = metadata.getTables(null, null, "GROUPS", null)
//        if(!groupsTable.next()){
//            sql.execute(CREATE_GROUPS_SQL)
//        }
//        def feedsTable = metadata.getTables(null, null, "FEEDS", null)
//        if(!feedsTable.next()){
//            sql.execute(CREATE_FEEDS_SQL)
//        }
//        def dataTable = metadata.getTables(null, null, "THINGS_DATA", null)
//        if (!dataTable.next()) {
//            sql.execute(CREATE_DATA_SQL)
//        }
//    }

    Sql getSqlConection(String dbUrl){
        Sql.newInstance( DB_PREFIX+dbUrl, SQLLITE_DRIVER )
    }

}
