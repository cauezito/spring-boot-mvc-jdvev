package br.com.cauezito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cauezito.model.Telefone;

@Repository
@Transactional
public interface TelefoneRepository extends CrudRepository<Telefone, Long> {
	@Query("select t from Telefone t where t.usuario.id = ?1")
	public List<Telefone> getTelefones(Long idUsuario);
}
