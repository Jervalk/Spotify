package fr.spotify_en_mieux_core.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
public class Artist extends Content {

	private String name;
	
	private String picture;
	
	@ManyToMany(mappedBy="artists")
	private Set<AuthoredContent> contents = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "Artist_Group")
	private Set<Group> groups = new HashSet<>();
	
}
