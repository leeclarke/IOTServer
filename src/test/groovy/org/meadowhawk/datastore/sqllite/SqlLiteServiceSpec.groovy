package org.meadowhawk.datastore.sqllite

import org.tomlj.Toml
import org.tomlj.TomlParseResult
import spock.lang.Shared
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

class SqlLiteServiceSpec extends Specification{

    @Shared
//    def tomlFile = new File('./src/test/resources/test.toml')
    Path source = Paths.get('./src/test/resources/test.toml')

    def "If no db exists set up the Things DB"(){
        given:

        TomlParseResult config = Toml.parse(source)
        def dbPath = './src/test/resources/temp.db'
        SqlLiteService sqlliteService = new SqlLiteService(config)

        when:
        sqlliteService.setUpDatabase()

        then: "The DB should have been created with required tables"
        def metadata = sqlliteService.sql.connection.getMetaData()
        def groupsTable = metadata.getTables(null, null, "GROUPS", null)
        assert groupsTable.next()

        def feedsTable = metadata.getTables(null, null, "FEEDS", null)
        assert feedsTable.next()

        def dataTable = metadata.getTables(null, null, "THINGS_DATA", null)
        assert dataTable.next()


        File dbFile = new File(dbPath)
        dbFile.delete()
    }
}
