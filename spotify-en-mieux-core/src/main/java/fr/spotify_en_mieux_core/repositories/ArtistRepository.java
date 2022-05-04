package fr.spotify_en_mieux_core.repositories;

import org.hibernate.Session;

import fr.spotify_en_mieux_core.models.Artist;
import fr.spotify_en_mieux_core.models.Group;
import fr.spotify_en_mieux_core.utils.HibernateUtil;

public class ArtistRepository extends GenericRepository<Artist> {

	public ArtistRepository() {
		super(Artist.class);
	}

	public Object findAllGroups() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			return s.createQuery("from Group g", Group.class)
				.getResultList();
		}
	}

}
