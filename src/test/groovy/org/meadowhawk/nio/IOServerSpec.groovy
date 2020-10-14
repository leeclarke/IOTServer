package org.meadowhawk.nio

import spock.lang.Specification

class IOServerSpec extends Specification{

    def "Ensure that a config file can be properly loaded"(){
        given:
        def testConfig = './src/test/resources/test.toml'

        when:
        IOTServer server = new IOTServer(testConfig)

        then:
        assert server.config != null
        assert server.config.size() > 0
    }

    def "Ensure that a missing config file doesn't crash the server setup"(){
        given:
        def testConfig = './src/test/resources/missing.toml'

        when:
        IOTServer server = new IOTServer(testConfig)

        then:
        assert server.config != null
        assert server.selector != null
    }
}
