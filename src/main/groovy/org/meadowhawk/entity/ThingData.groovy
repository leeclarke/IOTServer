package org.meadowhawk.entity

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import java.time.LocalDateTime

@ToString
@EqualsAndHashCode
class ThingData {
    Integer id
    String value
    Integer feedId
    LocalDateTime createDate
    BigDecimal lat
    BigDecimal lon
    BigDecimal ele
}
