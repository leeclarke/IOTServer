package org.meadowhawk.util

import org.meadowhawk.dto.IOTCompressedFormat

import java.time.Clock
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class IOTCompressedFormatParser {
    static final DateTimeFormatter dateTime = DateTimeFormatter.ofPattern('yyyyMMddHHmmss')


    static FixedLengthSlurper parser = new FixedLengthSlurper(
            2, 'id',
            11, 'feedId', {(it)?Integer.parseInt(it):0},
            14, 'createDate', { (it)? LocalDateTime.parse(it, dateTime): LocalDateTime.now(Clock.systemUTC()) },
            9, 'lat', { parseBigDecimal(it,5) },
            9, 'lon', { parseBigDecimal(it,5) },
            11, 'ele', { parseBigDecimal(it,3) },
            196, 'value'
    )
    static BigDecimal parseBigDecimal(String valIn, int decimalPlaces) {
        def valFmt = '0'
        if (valIn && valIn.length() > 0) {
            def sign = (valIn.charAt(0) == '1') ? '-' : ''
            def dStart = (valIn.size()) - decimalPlaces
            valFmt =sign + valIn[1..dStart-1] + '.'+ valIn[dStart..valIn.size()-1]
        }
        new BigDecimal(valFmt)
    }
    static IOTCompressedFormat parse(String input){
        def values = parser.parseText(input)
        new IOTCompressedFormat(values)
    }
}
