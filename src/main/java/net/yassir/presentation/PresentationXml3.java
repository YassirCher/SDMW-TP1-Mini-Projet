package net.yassir.presentation;

import net.yassir.context.XmlApplicationContext;
import net.yassir.metier.IMetier;

// injection directe via l'attribut
public class PresentationXml3 {
    public static void main(String[] args) throws Exception {
        XmlApplicationContext ctx = new XmlApplicationContext("beans.xml");
        IMetier metier = (IMetier) ctx.getBean("metierField");
        System.out.println("Résultat (field) = " + metier.calcul());
    }
}
