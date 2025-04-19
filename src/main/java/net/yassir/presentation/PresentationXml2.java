package net.yassir.presentation;

import net.yassir.context.XmlApplicationContext;
import net.yassir.metier.IMetier;
// utilisation du constructeur
public class PresentationXml2 {
    public static void main(String[] args) throws Exception {
        XmlApplicationContext ctx = new XmlApplicationContext("beans.xml");
        // on récupère le bean qui utilise le constructeur
        IMetier metier = (IMetier) ctx.getBean("metierCtor");
        System.out.println("Résultat (constructeur) = " + metier.calcul());
    }
}
