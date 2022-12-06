package principal.persistencia;


import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import principal.model.Ingrediente;
import principal.utils.HibernateUtil;

public class IngredienteDAO {
	
	public void insertarIngrediente(Ingrediente in) {
		Transaction tr = null;
		Session sesion = null;

		try {
			sesion = HibernateUtil.getSessionFactory().openSession();
			tr = sesion.beginTransaction();
			sesion.persist(in);
			tr.commit();
		} catch (PersistenceException e) {
			tr.rollback();
			System.out.println(e.getMessage());
			System.out.println("He fallado en insertar Ingrediente");
		} finally {
			sesion.close();
		}
	}
}
