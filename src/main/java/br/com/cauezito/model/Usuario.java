package br.com.cauezito.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty(message = "O nome deve ser preenchido")
	@NotNull(message = "O nome deve ser preenchido")
	private String nome;

	@NotEmpty(message = "O sobrenome deve ser preenchido")
	@NotNull(message = "O sobrenome deve ser preenchido")
	private String sobrenome;

	@Length(min = 8, max = 30)
	private String login;

	@Length(min = 6, max = 20)
	private String senha;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Telefone> telefones;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarios_role", joinColumns = @JoinColumn(name = "usuario_id",
	referencedColumnName = "id", table =  "usuario"),
	inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id", table = "role"))
	private List<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
