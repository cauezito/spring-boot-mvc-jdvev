package br.com.cauezito.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty(message = "O nome deve ser preenchido")
	@NotNull(message =  "O nome deve ser preenchido")
	private String nome;
	
	@NotEmpty(message = "O sobrenome deve ser preenchido")
	@NotNull(message = "O sobrenome deve ser preenchido")
	private String sobrenome;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Telefone> telefones;
}
