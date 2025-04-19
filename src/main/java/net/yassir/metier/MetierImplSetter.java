package net.yassir.metier;

import net.yassir.annotation.Component;
import net.yassir.annotation.Inject;
import net.yassir.annotation.PostConstruct;
import net.yassir.dao.IDao;

@Component(id="metierSetterAnnot")
public class MetierImplSetter implements IMetier {
    private IDao dao;
    private double initialValue;

    public MetierImplSetter() { }

    // **Setter annoté @Inject**
    @Inject
    public void setDao(IDao dao) {
        this.dao = dao;
    }

    @PostConstruct
    public void init() {
        initialValue = dao.getData() + 10;
    }

    @Override
    public double calcul() {
        return initialValue * 2;
    }
}
