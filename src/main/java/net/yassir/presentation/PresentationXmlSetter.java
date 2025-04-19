package net.yassir.presentation;

import net.yassir.context.XmlApplicationContext;
import net.yassir.dao.IDao;
import net.yassir.metier.IMetier;

// utilisation du setter
public class PresentationXmlSetter {
    public static void main(String[] args) throws Exception {
        // Charge la config XML via JAXB OXM
        XmlApplicationContext ctx = new XmlApplicationContext("beans.xml");
        IDao dao = (IDao) ctx.getBean("dao");
        IMetier metier = (IMetier) ctx.getBean("metierImpl3");
        System.out.println("(setter) DAO data = " + dao.getData());             // 42.0
        System.out.println("(setter) Metier calcul = " + metier.calcul());      // (42+10)*2 = 104.0

    }
}