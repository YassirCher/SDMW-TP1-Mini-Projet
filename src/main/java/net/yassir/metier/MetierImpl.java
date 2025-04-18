package net.yassir.metier;

import net.yassir.annotation.Component;
import net.yassir.annotation.Inject;
import net.yassir.annotation.PostConstruct;
import net.yassir.dao.IDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implémentation de la logique métier.
 */
@Component(id = "metierImpl")
@Service("metier")
public class MetierImpl implements IMetier {

    // Injection par champ
    @Inject
    private IDao dao;
    private double initialValue;

    public MetierImpl(@Qualifier("d2") IDao dao) {
        this.dao = dao;
    }

    public MetierImpl(){}

    @PostConstruct
    public void init() {
        // Initialisation après injection – ici, on ajoute 10 à la valeur obtenue
        initialValue = dao.getData() + 10;
    }

    @Override
    public double calcul() {
        return initialValue * 2;
    }

    // Méthode setter alternative pour l'injection

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}