package br.com.cauezito.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ModelAndView inicio() {
		ModelAndView mv = new ModelAndView("cadastro/usuario");
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	// ** = ignora tudo o que vier antes na url
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarUsuario")
	public ModelAndView salvar(Usuario usuario) {
		usuarioRepository.save(usuario);
		ModelAndView mv = new ModelAndView("cadastro/usuario");
		Iterable<Usuario> usuarios = usuarioRepository.findAll();
		mv.addObject("usuarios", usuarios);
		mv.addObject("usuario", new Usuario());
		return mv;
	}	
	
	@GetMapping("/editarUsuario/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("cadastro/usuario");
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		mv.addObject("usuario", usuario.get());
		return mv;
	}
	
}
