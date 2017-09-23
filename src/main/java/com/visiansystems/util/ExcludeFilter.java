package com.visiansystems.util;

import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLStreamReader;
import java.util.Arrays;

public class ExcludeFilter implements StreamFilter {

    private String[] tagNames;

    public ExcludeFilter(String... names) {
        tagNames = names;
        Arrays.sort(this.tagNames);
    }

    public boolean accept(XMLStreamReader rd) {
        return rd.hasName()
               && (Arrays.binarySearch(tagNames, rd.getLocalName()) < 0);
    }
}
