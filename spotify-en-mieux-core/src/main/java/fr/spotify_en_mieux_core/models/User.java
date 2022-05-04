package fr.spotify_en_mieux_core.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import fr.spotify_en_mieux_core.validators.In;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class User {
	
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue
	private int id;
	
	@NotBlank
	@NotNull
	private String username;

	@NotBlank
	@NotNull
	@Size(min = 8)
	private String password;
	
	@Email
	private String email;
	
	@In(authorizedValues= {"ROLE_ADMIN", "ROLE_PRODUCER", "ROLE_USER"})
	private String role;
	
}
