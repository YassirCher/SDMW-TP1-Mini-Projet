package net.yassir.presentation;

import net.yassir.context.AnnotationApplicationContext;
import net.yassir.metier.IMetier;

public class PresentationAnnotationSetter {
    public static void main(String[] args) {
        try {
            AnnotationApplicationContext ctx =
                    new AnnotationApplicationContext("net.yassir");
            // on récupère le bean dont l'id est "metierSetterAnnot"
            IMetier metier = (IMetier) ctx.getBean("metierSetterAnnot");
            System.out.println("Résultat (setter) = " + metier.calcul());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
