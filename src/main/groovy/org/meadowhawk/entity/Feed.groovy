package org.meadowhawk.entity

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.meadowhawk.datastore.sqllite.dao.FeedDao

@ToString
@EqualsAndHashCode
@DatabaseTable(tableName = "FEEDS", daoClass = FeedDao)
class Feed {
    @DatabaseField(generatedId = true)
    Integer id

    @DatabaseField(columnName = "NAME", unique = true, canBeNull = false)
    String name

    @DatabaseField(columnName = "DESCRIPTION", canBeNull = true, index = false)
    String description

    @DatabaseField(columnName = "KEY", canBeNull = true, index = true)
    String key

    Group group
}
