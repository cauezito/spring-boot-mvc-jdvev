package br.com.cauezito.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.cauezito.model.Profissao;

@Transactional
@Repository
public interface ProfissaoRepository extends CrudRepository<Profissao, Long> {
	
}
