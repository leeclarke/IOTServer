package org.meadowhawk.datastore.sqllite
import groovy.sql.Sql
import org.tomlj.TomlParseResult
import org.tomlj.TomlTable

import java.text.SimpleDateFormat
import static org.meadowhawk.datastore.sqllite.SqlliteConstants.*

class SqlLiteService {
    TomlTable config
    Sql sql


    SqlLiteService(TomlParseResult config){
        this.config = config.getTable(CONFIG_SQLLITE_TABLE)
        this.sql = getSqlConection()
    }

    def setUpDatabase(){
        def metadata = sql.connection.getMetaData()
        def groupsTable = metadata.getTables(null, null, "GROUPS", null)
        if(!groupsTable.next()){
            sql.execute(CREATE_GROUPS_SQL)
        }
        def feedsTable = metadata.getTables(null, null, "FEEDS", null)
        if(!feedsTable.next()){
            sql.execute(CREATE_FEEDS_SQL)
        }
        def dataTable = metadata.getTables(null, null, "THINGS_DATA", null)
        if (!dataTable.next()) {
            sql.execute(CREATE_DATA_SQL)
        }
    }

    Sql getSqlConection(){
        def dbPath = config.getString("dbPath")
        if(!dbPath || dbPath.isEmpty()) {
            dbPath = "things.db"
            println("DB not configured, setting db to things.db at app root.")
        }
        Sql.newInstance( DB_PREFIX+dbPath, SQLLITE_DRIVER )
    }

    def validateDBState(){

    }

    //TODO: Convert to DateTime
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        Date date = new Date()
        return dateFormat.format(date)
    }
}
