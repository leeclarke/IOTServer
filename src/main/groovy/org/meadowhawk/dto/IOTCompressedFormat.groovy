package org.meadowhawk.dto

import groovy.transform.ToString

import java.time.LocalDateTime

@ToString
class IOTCompressedFormat {
    String id
    String value
    Integer feedId
    LocalDateTime createDate
    BigDecimal lat
    BigDecimal lon
    BigDecimal ele
}

/*
Compact Message Format                                                                    |
------------------------------------------------------------------------------------------|
name          | length | format        | Notes                                            |
------------------------------------------------------------------------------------------|
type          | 2      | 00            | 00 - id for compact format                       |
feed-id       | 11     | 00000000000   |                                                  |
created-date  | 14     | yyyyMMddhhmmss| UTC * if empty server time will be inserted      |
lat           | 9      | 000000000     | 1st digit is neg val indicator                   |
lon           | 9      | 000000000     | last 5 are decimal, 3 whole numbers              |
ele           | 11     | 00000000000   | optional pass blank or pad 0s last 3 are decimal |
value         | 196    |               | free text                                        |
------------------------------------------------------------------------------------------|
lat|lon spec and examples  first digit indicates a negative value id == 1                 |
To have a predictable fixed length format there are a few rules to sending values.        |
Examples:                                                                                 |
value      | send format|                                                                 |
-1.12      | 100112000  |                                                                 |
20.123     | 002012300  |                                                                 |
32.30642   | 003230642  |                                                                 |
-122.61458 | 112261458  |                                                                 |
------------------------------------------------------------------------------------------|
ele: negative values same as lat/lon                                                      |
can be left blank.  if used it must be padded with zeros. Not last 3 are decimal amounts  |
Example:                                                                                  |
value       | format                                                                      |
nothing     | BLANK                                                                       |
10          | 00000010000                                                                 |
.210        | 00000000210                                                                 |
-213.10     | 10000213100                                                                 |
1242.742    | 00001242742                                                                 |
------------------------------------------------------------------------------------------|
 */