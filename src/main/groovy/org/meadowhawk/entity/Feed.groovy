package org.meadowhawk.entity

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class Feed {
    Integer id
    String name
    String description
    String key
    Group group
}
