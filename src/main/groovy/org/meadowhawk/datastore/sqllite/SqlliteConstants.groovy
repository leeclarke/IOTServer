package org.meadowhawk.datastore.sqllite

class SqlliteConstants {
    static final String SQLLITE_DRIVER = 'org.sqlite.JDBC'
    static final String DB_PREFIX = 'jdbc:sqlite:'
    static final String CONFIG_SQLLITE_TABLE = 'sql-lite'
    static final String DATE_TIME_FORMAT = 'yyyy-MM-dd HH:mm:ss'

    static final String CREATE_FEEDS_SQL = 'CREATE TABLE FEEDS (' +
            '  ID INTEGER PRIMARY KEY AUTOINCREMENT,' +
            '  NAME           TEXT    NOT NULL,' +
            '  DESCRIPTION    TEXT,' +
            '  GROUP_ID INTEGER)'

    static final String CREATE_GROUPS_SQL = 'CREATE TABLE  GROUPS (' +
            '  ID INTEGER PRIMARY KEY AUTOINCREMENT,' +
            '  NAME           TEXT    NOT NULL,' +
            '  DESCRIPTION    TEXT)'

    static final String CREATE_DATA_SQL = 'CREATE TABLE THINGS_DATA (' +
            '  ID INTEGER PRIMARY KEY AUTOINCREMENT,' +
            '  FEED_ID INTEGER NOT NULL,' +
            '  DATA_VALUE     TEXT    NOT NULL,' +
            '  LAT    REAL,' +
            '  LON    REAL,' +
            '  ELE    REAL,' +
            '  CREATE_DATE DATETIME DEFAULT CURRENT_TIMESTAMP)'

}
