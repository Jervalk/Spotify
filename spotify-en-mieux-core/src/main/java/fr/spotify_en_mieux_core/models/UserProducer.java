package fr.spotify_en_mieux_core.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class UserProducer extends UserListener {
	
	@ManyToMany(mappedBy="followed")
	private Set<UserListener> followers = new HashSet<>();
	
	@OneToMany(mappedBy = "owner", targetEntity = Content.class)
	@Where(clause = "DTYPE = 'Artist' or DTYPE = 'Group'")
	private Set<Artist> ownedArtists = new HashSet<>();
	
	@OneToMany(mappedBy = "owner", targetEntity = Content.class)
	@Where(clause = "DTYPE = 'Album'")
	private Set<Album> ownedAlbums = new HashSet<>();
	
	@OneToMany(mappedBy = "owner", targetEntity = Content.class)
	@Where(clause = "DTYPE = 'Music'")
	private Set<Music> ownedMusics = new HashSet<>();
	
}
