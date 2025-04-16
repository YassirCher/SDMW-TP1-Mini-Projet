package net.yassir.xml;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Classe de mapping pour un bean défini dans le fichier XML.
 */
public class BeanDefinition {
    private String id;
    private String className;
    private List<ConstructorArg> constructorArg;

    @XmlAttribute
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "class")
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    @XmlElement(name = "constructor-arg")
    public List<ConstructorArg> getConstructorArg() {
        return constructorArg;
    }
    public void setConstructorArg(List<ConstructorArg> constructorArg) {
        this.constructorArg = constructorArg;
    }
}