package fr.spotify_en_mieux_core.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
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
public class UserListener extends User {

	@ManyToMany
	@JoinTable(name = "Friends")
	private Set<UserListener> friends = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "FollowersFollowed")
	private Set<UserProducer> followed = new HashSet<>();

	@OneToMany(mappedBy = "owner", targetEntity = Content.class, cascade = CascadeType.ALL)
	@Where(clause = "DTYPE = 'Playlist'")
	private Set<Playlist> ownedPlaylists = new HashSet<>();

	@ManyToMany(mappedBy = "likers", targetEntity = Content.class)
	@Where(clause = "DTYPE = 'Playlist'")
	private Set<Playlist> likedPlaylists = new HashSet<>();

	@ManyToMany(mappedBy = "likers", targetEntity = Content.class)
	@Where(clause = "DTYPE = 'Artist' or DTYPE = 'Group'")
	private Set<Artist> likedArtists = new HashSet<>();

	@ManyToMany(mappedBy = "likers", targetEntity = Content.class)
	@Where(clause = "DTYPE = 'Album'")
	private Set<Album> likedAlbums = new HashSet<>();

	@ManyToMany(mappedBy = "likers", targetEntity = Content.class)
	@Where(clause = "DTYPE = 'Music'")
	private Set<Music> likedMusics = new HashSet<>();

	@OneToMany(mappedBy = "id.listener")
	private Set<Listening> listenings = new HashSet<>();

}
