package principal.persistencia;


import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import principal.model.Usuario;
import principal.utils.HibernateUtil;

public class UsuarioDAO {
	
	public void insertarUsuario(Usuario usu) {
		Transaction tr = null;
		Session sesion = null;
		
		try{
			sesion = HibernateUtil.getSessionFactory().openSession();
			tr = sesion.beginTransaction();
			sesion.persist(usu);
			tr.commit();
		}catch (PersistenceException e) {
			tr.rollback();
			System.out.println(e.getMessage());
			System.out.println("He fallado en insertar Usuario");
		}finally {
			sesion.close();
		}
	}
}
