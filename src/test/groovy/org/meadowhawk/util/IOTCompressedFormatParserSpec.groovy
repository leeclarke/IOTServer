package org.meadowhawk.util

import spock.lang.Specification

import java.time.LocalDateTime

class IOTCompressedFormatParserSpec extends Specification{

    def "Ensure that parseBigDecimal can parse positive and neg numbers from input"(){
        given:
            BigDecimal resp = IOTCompressedFormatParser.parseBigDecimal(valIn, dPlaces)
        expect:
            resp == bigDOut

        where:
        valIn         |dPlaces | bigDOut
        '100112000'   | 5      | new BigDecimal('-1.12')
        '002012300'   | 5      | new BigDecimal('20.123')
        '003230642'   | 5      | new BigDecimal('32.30642')
        '112261458'   | 5      | new BigDecimal('-122.61458')
        '000000000'   | 5      | new BigDecimal('0')
        ''            | 5      | new BigDecimal('0')
        '00000010000' | 3      | new BigDecimal('10')
        '10000213100' | 3      | new BigDecimal('-213.10')
        '00001242742' | 3      | new BigDecimal('1242.742')
        '00000000210' | 3      | new BigDecimal('.210')

    }

    def "Ensure parser can parse input properly"(){
        given:
            def testDateStr = "20200606153301"
            def testValue = "Hello World"
            def testFeedId = "00000230402"
            def testLat = "003230642"
            def testLon = "112261458"
            def testEle = "00003245100"
            LocalDateTime expectedDT = LocalDateTime.parse(testDateStr, "yyyyMMddHHmmss")
            def input = "00${testFeedId}${testDateStr}${testLat}${testLon}${testEle}${testValue}".padRight(252, " ")
        when:
            def resp = IOTCompressedFormatParser.parse(input)
        then:
            assert resp != null
            assert resp.id == "00"
            assert resp.feedId ==  Integer.parseInt(testFeedId)
            assert expectedDT == resp.createDate
            assert resp.value == testValue.padRight(196, ' ')
            assert resp.lat == new BigDecimal('32.30642')
            assert resp.lon == new BigDecimal('-122.61458')
            assert resp.ele == new BigDecimal('3245.100')
    }
}
//'00000002304022020060615330100323064211226145800003245100Hello World                                                                                                                                                                                         '