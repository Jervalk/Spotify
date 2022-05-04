package fr.spotify_en_mieux_core.models;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString()
@Entity
public abstract class AuthoredContent extends Content {

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Content_Artist")
	private Set<Artist> artists = new LinkedHashSet<>();
	
}
