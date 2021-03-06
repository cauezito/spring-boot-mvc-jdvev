package br.com.cauezito.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cauezito.model.Usuario;
import br.com.cauezito.repository.UsuarioRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.buscaUsuarioPorLogin(login);
		if(usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}
		
		return new User(usuario.getLogin(),
				usuario.getSenha(), 
				usuario.isEnabled(),
				usuario.isAccountNonExpired(),
				usuario.isCredentialsNonExpired(), 
				usuario.isAccountNonLocked(),
				usuario.getAuthorities());
	}

}
