package fr.spotify_en_mieux_core.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Content {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
	private UserListener owner;
	
	@ManyToMany
	private Set<UserListener> likers = new HashSet<>();
	
}
