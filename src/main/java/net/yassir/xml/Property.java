package net.yassir.xml;

import jakarta.xml.bind.annotation.XmlAttribute;

public class Property {
    private String name;
    private String ref;

    @XmlAttribute
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getRef() {
        return ref;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }
}
