package org.meadowhawk.entity

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.meadowhawk.datastore.sqllite.dao.ThingDataDao

import java.sql.Timestamp

@ToString
@EqualsAndHashCode
@DatabaseTable(tableName = "THINGS_DATA", daoClass = ThingDataDao)
class ThingData {
    @DatabaseField(generatedId = true)
    Integer id

    @DatabaseField(columnName = "VALUE", canBeNull = false, index = false)
    String value

    @DatabaseField(columnName = "FEED_ID", canBeNull = false, index = false)
    Integer feedId

    @DatabaseField(columnName = "CREATE_DATE", canBeNull = false, index = false)
    Timestamp createDate = new Timestamp(Calendar.getInstance().getTime().getTime());

    @DatabaseField(columnName = "LAT", canBeNull = true, index = false)
    BigDecimal lat

    @DatabaseField(columnName = "LON", canBeNull = true, index = false)
    BigDecimal lon

    @DatabaseField(columnName = "ELE", canBeNull = true, index = false)
    BigDecimal ele
}
