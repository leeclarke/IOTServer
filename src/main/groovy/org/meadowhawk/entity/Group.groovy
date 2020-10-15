package org.meadowhawk.entity

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class Group {
    Integer id
    String name
    String description
    List<Feed> feeds
}
