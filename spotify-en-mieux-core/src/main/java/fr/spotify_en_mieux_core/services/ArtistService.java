package fr.spotify_en_mieux_core.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.spotify_en_mieux_core.models.Artist;
import fr.spotify_en_mieux_core.models.Group;
import fr.spotify_en_mieux_core.repositories.ArtistRepository;
import fr.spotify_en_mieux_core.utils.HibernateUtil;

public class ArtistService {
	
	private ArtistRepository ar = new ArtistRepository();
	private FileService fs = new FileService();
	
	public Optional<Artist> findById(int id) {
		return ar.findById(id);
	}
	
	public Collection<Artist> findAll() {
		return ar.findAll();
	}
	
	
	public void save(Artist a) throws IOException {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			Transaction t = s.beginTransaction();
			// saving artist
			ar.save(a, s, t);
			// updating groups members
			if (a instanceof Group) {
				((Group) a).getMembers().forEach(m -> {
					m.getGroups().add((Group) a);
					ar.update(m, s, t);
				});
			}
			s.getTransaction().commit();
			// copying artist picture locally
			if (a.getPicture() != null)
				Files.move(fs.getTmpPath(a.getPicture()), fs.getPath(a.getPicture()));
		}
	}
	
	
	public void update(Artist a) throws IOException {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			Transaction t = s.beginTransaction();
			// fetching original values from db
			Artist old = ar.findById(a.getId(), s).get();
			// updating groups members
			if (old instanceof Group) {
				((Group) old).getMembers().stream()
					.filter(m -> !(a instanceof Group) || !((Group) a).getMembers().contains(m))
					.forEach(m -> {
						m.getGroups().remove(old);
						ar.update(m, s, t);
					});
			}
			if (a instanceof Group) {
				((Group) a).getMembers().forEach(m -> {
					m.getGroups().add((Group) a);
					ar.update(m, s, t);
				});
			}
			// updating artist
			String oldPicture = old.getPicture();
			ar.update(a, s, t);
			s.getTransaction().commit();
			// copying artist picture locally if needed
			if (a.getPicture() == null || !a.getPicture().equals(oldPicture)) {
				if (oldPicture != null)
					Files.delete(fs.getPath(oldPicture));
				if (a.getPicture() != null)
					Files.move(fs.getTmpPath(a.getPicture()), fs.getPath(a.getPicture()));
			}
		}
	}
	
	
	public void deleteById(int id) throws IOException {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			Transaction t = s.beginTransaction();
			Artist a = ar.findById(id, s).get();
			if (a instanceof Group) {
				((Group) a).getMembers().forEach(m -> {
					m.getGroups().remove((Group) a);
					ar.update(m, s, t);
				});
			}
			ar.delete(a, s, t);
			s.getTransaction().commit();
			if (a.getPicture() != null)
				Files.delete(fs.getPath(a.getPicture()));
		}
	}

	public Object findAllGroups() {
		return ar.findAllGroups();
	}

}
