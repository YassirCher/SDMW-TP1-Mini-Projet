package net.yassir.presentation;

import net.yassir.context.XmlApplicationContext;
import net.yassir.dao.IDao;
import net.yassir.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PresentationXml {
    public static void main(String[] args) throws Exception {
        ApplicationContext springContext =
                new ClassPathXmlApplicationContext("beans.xml");
        IMetier metier = springContext.getBean(IMetier.class);
        System.out.println("RES="+metier.calcul());
    }
}