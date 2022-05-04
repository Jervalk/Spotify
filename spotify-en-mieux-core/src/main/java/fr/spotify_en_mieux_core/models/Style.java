package fr.spotify_en_mieux_core.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString()
@Entity
public class Style {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	@ManyToMany
	@JoinTable(name = "Style_Music")
	private Set<Music> musics = new HashSet<>();
	
}
