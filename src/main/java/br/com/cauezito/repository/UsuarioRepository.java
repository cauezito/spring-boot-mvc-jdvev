package br.com.cauezito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cauezito.model.Usuario;

@Repository
@Transactional
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	@Query("select u from Usuario u where u.nome like %?1%")
	List<Usuario> buscaUsuarioPorNome(String nome);
	@Query("select u from Usuario u where u.login = ?1")
	Usuario buscaUsuarioPorLogin(String login);
}
