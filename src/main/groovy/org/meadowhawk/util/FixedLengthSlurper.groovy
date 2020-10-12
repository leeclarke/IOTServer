package org.meadowhawk.util

class FixedLengthSlurper {

    List formats = []

    /**
     * Constructor.
     * @param vars the formats that should be used when parsing the file
     */
    FixedLengthSlurper(Object ... vars) {
        int varsIndex = 0
        while (varsIndex < vars.size()) {
            //the size and column name must be provided in pairs
            def format = [size: vars[varsIndex], name: vars[varsIndex + 1]]
            varsIndex += 2

            //check next argument to see if a formatting closure was provided
            if (varsIndex < vars.size() && vars[varsIndex] instanceof Closure) {
                format << [formatter: vars[varsIndex]]
                varsIndex++
            }
            formats << format
        }
    }

    /**
     * Reads through the text and applies all formats to break apart the data
     * into mapped properties
     * @param data the fixed length text to parse
     */
    def parseText = { String data ->
        def values = [:]
        int currentIndex = 0

        formats.each { format ->
            //extract the data
            values[format.'name'] =

                    data[currentIndex .. (currentIndex + format.'size' - 1)]

            //if a formatting closure was provided, apply it's formatting
            if (format.'formatter') {
                values[format.'name'] = format.'formatter'(values[format.'name'])
            }

            //increment the indexing for the next format
            currentIndex += format.'size'
        }

        return values
    }
}

