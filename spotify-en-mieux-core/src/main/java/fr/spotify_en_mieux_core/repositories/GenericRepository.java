package fr.spotify_en_mieux_core.repositories;

import java.util.Collection;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.spotify_en_mieux_core.utils.HibernateUtil;

public class GenericRepository <T> {

	
	private Class<T> clazz;
	
	public GenericRepository(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public Collection<T> findAll(Session s) {
		return s.createQuery("from " + clazz.getName(), clazz).getResultList();
	}

	public Collection<T> findAll() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			return findAll(s);
		}
	}

	public Optional<T> findById(int id, Session s) {
		return Optional.ofNullable(s.get(clazz, id));
	}

	public Optional<T> findById(int id) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			return findById(id, s);
		}
	}

	public void save(T u, Session s, Transaction t) {
		s.save(u);
	}

	public void save(T u, Session s) {
		s.beginTransaction();
		save(u, s, s.getTransaction());
		s.getTransaction().commit();
	}

	public void save(T u) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			save(u, s);
		}
	}

	public void update(T u, Session s, Transaction t) {
		s.merge(u);
	}

	public void update(T u, Session s) {
		s.beginTransaction();
		update(u, s, s.getTransaction());
		s.getTransaction().commit();
	}

	public void update(T u) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			update(u, s);
		}
	}

	public void delete(T u, Session s, Transaction t) {
		s.delete(u);
	}

	public void delete(T u, Session s) {
		s.beginTransaction();
		delete(u, s, s.beginTransaction());
		s.getTransaction().commit();
	}

	public void delete(T u) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			delete(u, s);
		}
	}

	public void deleteById(int id, Session s, Transaction t) {
		s.delete(findById(id, s).get());
	}

	public void deleteById(int id, Session s) {
		s.beginTransaction();
		deleteById(id, s, s.getTransaction());
		s.getTransaction().commit();
	}

	public void deleteById(int id) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			deleteById(id, s);
		}
	}

}
