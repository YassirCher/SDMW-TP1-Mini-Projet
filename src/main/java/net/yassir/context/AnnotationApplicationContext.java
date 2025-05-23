package net.yassir.context;

import net.yassir.annotation.Component;
import net.yassir.annotation.Inject;
import net.yassir.annotation.PostConstruct;
import net.yassir.utils.ClassScanner;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
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
        // --- Instanciation des beans ---
        for (Class<?> cls : classes) {
            Component comp = cls.getAnnotation(Component.class);
            String beanId = comp.id().isEmpty() ? cls.getSimpleName() : comp.id();

            Object instance;
            // 1) Constructor‑injection si un constructeur est annoté
            Constructor<?> injectCtor = Arrays.stream(cls.getDeclaredConstructors())
                    .filter(c -> c.isAnnotationPresent(Inject.class))
                    .findFirst()
                    .orElse(null);

            if (injectCtor != null) {
                // Préparation des arguments
                Class<?>[] paramTypes = injectCtor.getParameterTypes();
                Object[] args = new Object[paramTypes.length];
                for (int i = 0; i < paramTypes.length; i++) {
                    // On utilise le bean déjà instancié ou on lance une exception
                    args[i] = beans.get(paramTypes[i].getName());
                    if (args[i] == null) {
                        throw new RuntimeException("Dépendance non trouvée pour " + paramTypes[i]);
                    }
                }
                injectCtor.setAccessible(true);
                instance = injectCtor.newInstance(args);
            } else {
                instance = cls.getDeclaredConstructor().newInstance();
            }

            beans.put(beanId, instance);
        }


        // —– 3) INJECTION CHAMPS & SETTERS —–
        for (Object bean : beans.values()) {
            for (Field field : bean.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    // Créer une copie finale pour la lambda
                    final String refIdFinal = field.getAnnotation(Inject.class).id().isEmpty()
                            ? field.getType().getName()  // Nom complet de l'interface (ex: "net.yassir.dao.IDao")
                            : field.getAnnotation(Inject.class).id();

                    Object dependency = beans.values().stream()
                            .filter(obj -> {
                                Component comp = obj.getClass().getAnnotation(Component.class);
                                String beanId = comp.id().isEmpty()
                                        ? obj.getClass().getSimpleName()
                                        : comp.id();
                                return beanId.equals(refIdFinal)
                                        || obj.getClass().getName().equals(refIdFinal);
                            })
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Dépendance manquante: " + refIdFinal));

                    field.setAccessible(true);
                    field.set(bean, dependency);
                }
            }

        }
        // 4) Setter‑injection
        for (Object bean : beans.values()) {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Inject.class)
                        && method.getParameterCount() == 1) {

                    Class<?> paramType = method.getParameterTypes()[0];
                    Object dep = beans.values().stream()
                            .filter(b -> paramType.isInstance(b))
                            .findFirst()
                            .orElseThrow(() ->
                                    new RuntimeException("Dépendance manquante pour setter " + method.getName()));

                    method.setAccessible(true);
                    method.invoke(bean, dep);
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
