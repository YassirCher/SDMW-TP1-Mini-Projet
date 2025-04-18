package net.yassir.metier;

import net.yassir.dao.IDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("metier")
public class MetierImpl2 implements IMetier {
    private IDao dao; // Couplage faible

    /**
     * Pour injecter dans l'attribut dao
     * un objet d'une classe qui implémente l'interface IDO
     * au moment de l'instantiation
     */
    public MetierImpl2(@Qualifier("dao") IDao dao) {
        this.dao = dao;
    }


    @Override
    public double calcul() {
        double t = dao.getData()+10;
        double res = t *2 ;
        return res;
    }

    /**
     * Pour injecter dans l'attribut dao
     * un objet d'une classe qui implémente l'interface IDO
     * après instantiation
     */
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
