package fr.spotify_en_mieux_core.repositories;

import java.util.Collection;

import org.hibernate.Session;

import fr.spotify_en_mieux_core.models.Music;
import fr.spotify_en_mieux_core.utils.HibernateUtil;

public class MusicRepository extends GenericRepository<Music> {

	public MusicRepository() {
		super(Music.class);
	}

	public Collection<Music> findByTitleLike(String title, Session s) {
		return s.createQuery("from Music where title like :title", Music.class)
				.setParameter("title", "%"+title+"%")
				.getResultList();
	}
	
	public Collection<Music> findByTitleLike(String title) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			return findByTitleLike(title, s);
		}
	}
}
