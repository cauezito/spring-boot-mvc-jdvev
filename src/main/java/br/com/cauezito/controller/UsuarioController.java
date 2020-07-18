package br.com.cauezito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.cauezito.model.Usuario;
import br.com.cauezito.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroUsuario")
	public String inicio() {
		return "cadastro/usuario";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/salvarUsuario")
	public String salvar(Usuario usuario) {
		usuarioRepository.save(usuario);
		return "cadastro/usuario";
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/listarUsuarios")
	public ModelAndView usuarios() {
		ModelAndView mv = new ModelAndView("cadastro/usuario");
		Iterable<Usuario> usuarios = usuarioRepository.findAll();
		mv.addObject("usuarios", usuarios);
		return mv;
		
	}
	
}
