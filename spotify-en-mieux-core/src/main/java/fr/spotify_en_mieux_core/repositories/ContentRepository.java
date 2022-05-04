package fr.spotify_en_mieux_core.repositories;

import fr.spotify_en_mieux_core.models.Content;

public class ContentRepository extends GenericRepository<Content> {

	public ContentRepository() {
		super(Content.class);
	}

}
