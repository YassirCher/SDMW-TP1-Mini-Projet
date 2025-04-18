package net.yassir.metier;

import net.yassir.dao.IDao;
import net.yassir.metier.IMetier;

public class MetierImpl3 implements IMetier {
    private IDao dao;
    private double initialValue;

    public MetierImpl3() {
        // Constructeur par défaut
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }

    public void init() {
        initialValue = dao.getData() + 10;
    }

    @Override
    public double calcul() {
        return initialValue * 2;
    }
}
