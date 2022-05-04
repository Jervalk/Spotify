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

import fr.spotify_en_mieux_core.models.Music;
import fr.spotify_en_mieux_core.models.Group;
import fr.spotify_en_mieux_core.repositories.MusicRepository;
import fr.spotify_en_mieux_core.utils.HibernateUtil;

public class MusicService {
	
	private MusicRepository mr = new MusicRepository();
	private FileService fs = new FileService();
	
	public Optional<Music> findById(int id) {
		return mr.findById(id);
	}
	
	public Collection<Music> findAll() {
		return mr.findAll();
	}
	
	
	public void save(Music m) throws IOException {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			Transaction t = s.beginTransaction();
			// saving music
			mr.save(m, s, t);
			s.getTransaction().commit();
			// copying artist picture locally
			if (m.getFile() != null)
				Files.move(fs.getTmpPath(m.getFile()), fs.getPath(m.getFile()));
		}
	}

	public void update(Music m) throws IOException {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			Transaction t = s.beginTransaction();
			// saving music
			mr.update(m, s, t);
			s.getTransaction().commit();
			// TODO: correct picture management
			// copying artist picture locally
			if (m.getFile() != null)
				Files.move(fs.getTmpPath(m.getFile()), fs.getPath(m.getFile()));
		}
	}
	
	
	public void deleteById(int id) throws IOException {
		delete(mr.findById(id).get());
	}
	
	
	public void delete(Music m) throws IOException {
		mr.delete(m);
		if (m.getFile() != null)
			Files.delete(fs.getPath(m.getFile()));
	}

}
