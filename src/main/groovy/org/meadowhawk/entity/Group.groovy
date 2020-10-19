package org.meadowhawk.entity

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.meadowhawk.datastore.sqllite.dao.GroupDao

@ToString
@EqualsAndHashCode
@DatabaseTable(tableName = "GROUPS", daoClass = GroupDao)
class Group {
    @DatabaseField(generatedId = true)
    Integer id

    @DatabaseField(columnName = "NAME", unique = true, canBeNull = false)
    String name

    @DatabaseField(columnName = "DESCRIPTION", canBeNull = true, index = false)
    String description

    List<Feed> feeds
}
