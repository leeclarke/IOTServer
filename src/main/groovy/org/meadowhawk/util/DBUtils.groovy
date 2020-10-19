package org.meadowhawk.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.meadowhawk.datastore.sqllite.SqlliteConstants.DATE_TIME_FORMAT

class DBUtils {
    static DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT, Locale.getDefault())

    static String getNowDateTime() {
        LocalDateTime now = LocalDateTime.now()
        return dtFormatter.format( now)
    }

    static LocalDateTime toLocalDateTime(String stringVal){
        LocalDateTime ldtVal
        if(stringVal && !stringVal.isEmpty()){
            ldtVal = dtFormatter.parse(stringVal)
        } else {
            ldtVal = LocalDateTime.now()
        }
        ldtVal
    }
}
