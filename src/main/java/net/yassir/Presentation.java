package net.yassir;

import net.yassir.context.AnnotationApplicationContext;
import net.yassir.metier.IMetier;

public class Presentation {
    public static void main(String[] args) {
        try {
            // Création du contexte d'injection en scannant le package "com.example"
            AnnotationApplicationContext context = new AnnotationApplicationContext("net.yassir");

            // Récupération du bean métier dont l'id est "metierImpl" (défini dans l'annotation @Component)
            IMetier metier = (IMetier) context.getBean("metierImpl");

            // Exécution de la logique métier et affichage du résultat
            System.out.println("Résultat du calcul : " + metier.calcul());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}