package org.meadowhawk.datastore.sqllite

import org.meadowhawk.datastore.sqllite.dao.FeedDao
import org.meadowhawk.datastore.sqllite.dao.GroupDao
import org.meadowhawk.datastore.sqllite.dao.ThingDataDao
import org.meadowhawk.entity.Feed
import org.meadowhawk.entity.Group
import org.meadowhawk.entity.ThingData
import org.tomlj.Toml
import org.tomlj.TomlParseResult
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class SqlLiteServiceSpec extends Specification{
    static final String  tempDbPath = './src/test/resources/temp.db'
    static Path source = Paths.get('./src/test/resources/test.toml')
    static SqlLiteService sqlliteService

    def setupSpec(){
        TomlParseResult config = Toml.parse(source)
        sqlliteService = new SqlLiteService(config)
    }

    def cleanupSpec(){
        File dbFile = new File(tempDbPath)
        dbFile.delete()
    }

    def "Can add, select and update Groups"(){
        given: "We have a new group"
        def newGroup = new Group(name: "TestGroup" , description: "This is a test")
        GroupDao groupDao = new GroupDao(sqlliteService.connPool)

        when:
        groupDao.createOrUpdate(newGroup)

        then:
        def qb = groupDao.queryBuilder()
        qb.where().eq("name","TestGroup")
        def results = groupDao.queryRaw(qb.prepareStatementString())
        results.size() == 1
    }

    def "Can add, select and update Feeds"(){
        given: "We have a new feed"
        def newFeed = new Feed(name: "TestFeed" , description: "This is a test", key: "key")
        FeedDao feedDao = new FeedDao(sqlliteService.connPool)

        when:
        feedDao.createOrUpdate(newFeed)

        then:
        def qb = feedDao.queryBuilder()
        qb.where().eq("name","TestFeed")
        def results = feedDao.queryRaw(qb.prepareStatementString())
        results.size() == 1
    }

    def "Can add, select and update ThingData"(){
        given: "We have a new thing data"
        def newThingData = new ThingData(value: "123.45" , feedId: 0,  lat: 30.123, lon: -80.123, ele: 3247)
        ThingDataDao thingDataDao = new ThingDataDao(sqlliteService.connPool)

        when:
        thingDataDao.createOrUpdate(newThingData)

        then:
        def qb = thingDataDao.queryBuilder()
        qb.where().eq("FEED_ID",0)
        def results = thingDataDao.queryRaw(qb.prepareStatementString())
        results.size() == 1
    }
}
