package fr.spotify_en_mieux_core.models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString()
@Entity
public class Playlist extends Content {

	private String name;
	
	@ManyToMany
	@JoinTable(name = "Playlist_Music")
	private List<Music> musics = new LinkedList<>();
	
}
