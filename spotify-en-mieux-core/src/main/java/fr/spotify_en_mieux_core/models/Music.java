package fr.spotify_en_mieux_core.models;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString()
@Entity
public class Music extends AuthoredContent {

	private String title;
	
	private Duration duration;
	
	private String file;
	
	@ManyToMany(mappedBy = "musics")
	private Set<Playlist> playlists = new HashSet<>();

	@ManyToMany(mappedBy = "tracks", fetch = FetchType.EAGER)
	private Set<Album> albums = new HashSet<>();
	
	@OneToMany(mappedBy="id.listened")
	private Set<Listening> listeners = new HashSet<>();
	
	@ManyToMany(mappedBy="musics", fetch = FetchType.EAGER)
	private Set<Style> styles = new HashSet<>();
	
}
