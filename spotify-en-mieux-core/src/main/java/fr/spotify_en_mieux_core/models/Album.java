package fr.spotify_en_mieux_core.models;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString()
@Entity
public class Album extends AuthoredContent {

	private String name;
	
	private LocalDate releaseDate;
	
	private String cover;
	
	@ManyToMany
	@JoinTable(name = "Album_Music")
	private Set<Music> tracks = new LinkedHashSet<>();
	
}
