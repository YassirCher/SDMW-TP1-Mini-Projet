package net.yassir.context;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import net.yassir.xml.BeanDefinition;
import net.yassir.xml.Beans;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Contexte d'injection basé sur la configuration XML.
 * Cette implémentation est simplifiée et ne gère ici que l'instanciation des beans.
 */
public class XmlApplicationContext {
    private Map<String, Object> beans = new HashMap<>();

    public XmlApplicationContext(String xmlPath) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Beans.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Beans config = (Beans) unmarshaller.unmarshal(new File(xmlPath));
        for (BeanDefinition bd : config.getBeans()) {
            Class<?> cls = Class.forName(bd.getClassName());
            Object instance = cls.getDeclaredConstructor().newInstance();
            beans.put(bd.getId(), instance);
        }
        // Pour cette version, l'injection (champ/setter) n'est pas gérée.
    }

    public Object getBean(String id) {
        return beans.get(id);
    }
}