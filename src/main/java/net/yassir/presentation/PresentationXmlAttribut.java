package net.yassir.presentation;

import net.yassir.context.XmlApplicationContext;
import net.yassir.metier.IMetier;

// injection directe via l'attribut
public class PresentationXmlAttribut {
    public static void main(String[] args) throws Exception {
        XmlApplicationContext ctx = new XmlApplicationContext("beans-att.xml");
        IMetier metier = (IMetier) ctx.getBean("metierField");
        System.out.println("Résultat (field) = " + metier.calcul());
    }
}
