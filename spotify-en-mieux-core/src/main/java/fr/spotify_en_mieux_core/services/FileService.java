package fr.spotify_en_mieux_core.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import fr.spotify_en_mieux_core.utils.PropertiesUtil;

public class FileService {
	
	public static Path BASE_PATH = Paths.get(PropertiesUtil.getProperty("spotify.filepath"));
	public static Path TMP_PATH = BASE_PATH.resolve(Paths.get("tmp/"));
	
	public Path generateUniqueFileName(String prefix, String extension) {
		Path path;
		do {
			path = Paths.get(PropertiesUtil.getProperty("spotify.filepath") + prefix + "-" + UUID.randomUUID() + extension);
		} while (Files.exists(path));
		return path.getFileName();
	}

	public Path getPath(String fileName) {
		return getPath(Paths.get(fileName));
	}
	
	public Path getPath(Path fileName) {
		return BASE_PATH.resolve(fileName);
	}

	public Path getTmpPath(String fileName) {
		return getTmpPath(Paths.get(fileName));
	}
	
	public Path getTmpPath(Path fileName) {
		return TMP_PATH.resolve(fileName);
	}
}
