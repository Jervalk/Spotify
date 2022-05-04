package fr.spotify_en_mieux_core;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import fr.spotify_en_mieux_core.models.Playlist;
import fr.spotify_en_mieux_core.models.User;
import fr.spotify_en_mieux_core.models.UserListener;
import fr.spotify_en_mieux_core.models.UserProducer;
import fr.spotify_en_mieux_core.repositories.UserRepository;
import fr.spotify_en_mieux_core.utils.HibernateUtil;


public class App {
	
	public static void main(String[] args) {
	
		UserRepository ur = new UserRepository();
		
		// save
		User u = new UserProducer();
		u.setUsername("andre");
		u.setPassword("andre");
		u.setEmail("a@a.a");
		ur.save(u);
		
		// findall
		System.out.println("> findall");
		ur.findAll().forEach(us -> System.out.println("\t" + us));
		
		// findById
		System.out.println("> findById\n\t" + ur.findById(u.getId()));
		
		// delete
		System.out.println("> delete");
		ur.delete(u);
		ur.findAll().forEach(us -> System.out.println("\t" + us));
		
		
		
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			s.beginTransaction();
			User u1 = ur.findById(3, s).get();
			u1.setPassword("encore un autre truc");
			s.getTransaction().commit();
		}
		
		
		
	}
}
