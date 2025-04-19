package net.yassir.presentation;

import net.yassir.context.AnnotationApplicationContext;
import net.yassir.metier.IMetier;

public class PresentationAnnotationConstructor {
    public static void main(String[] args) {
        try {
            AnnotationApplicationContext ctx =
                    new AnnotationApplicationContext("net.yassir");
            // on récupère le bean dont l'id est "metierCtorAnnot"
            IMetier metier = (IMetier) ctx.getBean("metierCtorAnnot");
            System.out.println("Résultat (constructeur) = " + metier.calcul());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
