package org.meadowhawk.util

import spock.lang.Specification

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FixedLengthSlurperSpec extends Specification{

    def "When processing a good file all values are placed in the correct fields"(){
        given:
            def dateTime = DateTimeFormatter.ofPattern("yyyyMMdd")
            def parser = new FixedLengthSlurper(
                    20, 'fullname',
                    2, 'state',
                    12 , 'phone',
                    8, 'dateTime', { LocalDate.parse(it, dateTime) },
                    11, 'amount', {String val -> new BigDecimal(val[0..val.size() -3] + '.' + val[val.size() - 2, val.size() -1]) }
            )

            List values = []

        when:
            new File('./src/test/resources/slurpTestFile.txt').eachLine {values << parser.parseText(it)}

        then:
            assert values.size() == 3
            assert values[0].fullname == "Mr Furry Pants      "
            assert values[0].state == "WA"
            assert values[0].phone == "201-332-0949"
            assert values[0].dateTime ==  new LocalDate(2001,01,02)
            assert (new BigDecimal("2832.10")).equals(values[0].amount)

            assert values[1].fullname == "Kira Merryweather   "
            assert values[1].state == "VA"
            assert values[1].phone == "451-232-4949"
            assert values[1].dateTime == new LocalDate(2011,01,03)
            assert (new BigDecimal("1456.52")).equals(values[1].amount)
    }
}
