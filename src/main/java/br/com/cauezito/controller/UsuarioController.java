package br.com.cauezito.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.cauezito.model.Telefone;
import br.com.cauezito.model.Usuario;
import br.com.cauezito.repository.TelefoneRepository;
import br.com.cauezito.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroUsuario")
	public ModelAndView inicio() {
		ModelAndView mv = new ModelAndView("cadastro/usuario");
		Iterable<Usuario> usuarios = usuarioRepository.findAll();
		mv.addObject("usuarios", usuarios);
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
	
	@GetMapping("/excluirUsuario/{id}")
	public ModelAndView excluir(@PathVariable("id") Long id) {
		usuarioRepository.deleteById(id);
		ModelAndView mv = new ModelAndView("cadastro/usuario");
		mv.addObject("usuarios", usuarioRepository.findAll());
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@PostMapping("**/pesquisarUsuario")
	public ModelAndView pesquisarUsuario(@RequestParam("nomePesquisado") String nomePesquisado ) {
		ModelAndView mv = new ModelAndView("cadastro/usuario");
		mv.addObject("usuarios", usuarioRepository.buscaUsuarioPorNome(nomePesquisado));
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@GetMapping("/telefones/{id}")
	public ModelAndView exibeTelefones(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("cadastro/telefones");
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		mv.addObject("usuario", usuario.get());
		return mv;
	}
	
	@PostMapping("**/addTelefone/{id}")
	public ModelAndView adicionarTelefone(Telefone telefone, 	
			@PathVariable("id") Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		telefone.setUsuario(usuario);
		telefoneRepository.save(telefone);
		ModelAndView mv = new ModelAndView("cadastro/telefones");
		mv.addObject("usuario", usuario);
		
		return mv;
	}
	
}
