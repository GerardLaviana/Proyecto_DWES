package principal.persistencia;


import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import principal.model.Receta;
import principal.utils.HibernateUtil;

public class RecetaDAO {
	
	public void insertarReceta(Receta re) {
		Transaction tr = null;
		Session sesion = null;
		
		try{
			sesion = HibernateUtil.getSessionFactory().openSession();
			tr = sesion.beginTransaction();
			sesion.persist(re);
			tr.commit();
		}catch (PersistenceException e) {
			tr.rollback();
			System.out.println(e.getMessage());
			System.out.println("He fallado en insertar Receta");
		}finally {
			sesion.close();
		}
	}
}
