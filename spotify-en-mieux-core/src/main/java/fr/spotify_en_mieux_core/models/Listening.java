package fr.spotify_en_mieux_core.models;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString()
@Entity
public class Listening {

	@Embeddable
	public static class ListeningId implements Serializable {

		@ManyToOne
		private UserListener listener;
		
		@ManyToOne
		private Music listened;	
	}
	
	
	@EmbeddedId
	private ListeningId id;
	
	private int number;
	
	private ZonedDateTime last;
	
}
