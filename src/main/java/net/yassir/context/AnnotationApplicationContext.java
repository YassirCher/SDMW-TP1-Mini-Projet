package net.yassir.context;

import net.yassir.annotation.Component;
import net.yassir.annotation.Inject;
import net.yassir.annotation.PostConstruct;
import net.yassir.utils.ClassScanner;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * Contexte d'injection basé sur les annotations.
 */
public class AnnotationApplicationContext {
    private Map<String,Object> beans = new HashMap<>();

    public AnnotationApplicationContext(String basePackage) throws Exception {
        // —– 1) SCAN AUTOMATIQUE AVEC REFLECTIONS —–
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);

        // —– 2) INSTANCIATION DES BEANS —–
        for (Class<?> cls : classes) {
            Component comp = cls.getAnnotation(Component.class);
            String beanId = comp.id().isEmpty() ? cls.getSimpleName() : comp.id();
            Object instance = cls.getDeclaredConstructor().newInstance();
            beans.put(beanId, instance);
        }

        // —– 3) INJECTION CHAMPS & SETTERS —–
        for (Object bean : beans.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    String refId = field.getAnnotation(Inject.class).id();
                    refId = refId.isEmpty() ? field.getType().getSimpleName() : refId;
                    Object dependency = beans.get(refId);
                    field.setAccessible(true);
                    field.set(bean, dependency);
                }
            }
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Inject.class)
                        && method.getName().startsWith("set")
                        && method.getParameterCount() == 1) {
                    String refId = method.getAnnotation(Inject.class).id();
                    refId = refId.isEmpty()
                            ? method.getParameterTypes()[0].getSimpleName()
                            : refId;
                    Object dependency = beans.get(refId);
                    method.setAccessible(true);
                    method.invoke(bean, dependency);
                }
            }
        }

        // —– 4) APPEL DES MÉTHODES @PostConstruct —–
        for (Object bean : beans.values()) {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(PostConstruct.class)) {
                    method.setAccessible(true);
                    method.invoke(bean);
                }
            }
        }
    }


    public Object getBean(String id) {
        return beans.get(id);
    }
}
