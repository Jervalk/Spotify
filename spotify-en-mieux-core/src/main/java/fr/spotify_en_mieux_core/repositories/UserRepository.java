package fr.spotify_en_mieux_core.repositories;

import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Session;

import fr.spotify_en_mieux_core.models.User;
import fr.spotify_en_mieux_core.utils.HibernateUtil;

public class UserRepository extends GenericRepository<User> {

	public UserRepository() {
		super(User.class);
	}
	
	
	public Optional<User> findByUsernameAndPassword(String username, String password, Session s) {
		try {
			return Optional.of(s.createQuery("from User u where u.username = :username and password = :password", User.class)
					.setParameter("username", username)
					.setParameter("password", password)
					.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}

	public Optional<User> findByUsernameAndPassword(String username, String password) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			return findByUsernameAndPassword(username, password, s);
		}
	}

}
