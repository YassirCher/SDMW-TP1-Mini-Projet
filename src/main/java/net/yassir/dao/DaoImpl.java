package net.yassir.dao;


import net.yassir.annotation.Component;
import net.yassir.dao.IDao;
/**
 * Implémentation simple de l'interface IDao.
 */
@Component(id="net.yassir.dao.IDao")
public class DaoImpl implements IDao {
    @Override
    public double getData() {
        // Pour cet exemple, on renvoie une valeur fixe.
        return 42;
    }
}