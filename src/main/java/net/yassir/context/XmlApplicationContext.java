package net.yassir.context;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import net.yassir.xml.BeanDefinition;
import net.yassir.xml.Beans;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Contexte d'injection basé sur la configuration XML.
 * Cette implémentation est simplifiée et ne gère ici que l'instanciation des beans.
 */
public class XmlApplicationContext {
    private Map<String, Object> beans = new HashMap<>();

    /**
     * @param xmlPath chemin relatif sur le classpath (ex. "beans.xml")
     */
    public XmlApplicationContext(String xmlPath) throws Exception {
        // 1. Lecture du fichier depuis le classpath
        InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(xmlPath);
        if (is == null) {
            throw new IllegalArgumentException("Ressource introuvable : " + xmlPath);
        }                                                      // :contentReference[oaicite:5]{index=5}

        // 2. Unmarshalling JAXB depuis l'InputStream
        JAXBContext jaxbCtx = JAXBContext.newInstance(Beans.class);
        Unmarshaller um = jaxbCtx.createUnmarshaller();
        Beans config = (Beans) um.unmarshal(is);               // :contentReference[oaicite:6]{index=6} :contentReference[oaicite:7]{index=7}

        // 3. Instanciation des beans sans injection (version simplifiée)
        for (BeanDefinition bd : config.getBeans()) {
            Class<?> cls = Class.forName(bd.getClassName());
            Object instance = cls.getDeclaredConstructor().newInstance();
            beans.put(bd.getId(), instance);
        }
    }

    public Object getBean(String id) {
        return beans.get(id);
    }
}