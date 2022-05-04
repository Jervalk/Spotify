package fr.spotify_en_mieux_core.models;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString()
@Entity
@Table(name = "Band")
public class Group extends Artist {
	
	@ManyToMany(mappedBy="groups", fetch = FetchType.EAGER)
	private Set<Artist> members = new LinkedHashSet<>();

}
