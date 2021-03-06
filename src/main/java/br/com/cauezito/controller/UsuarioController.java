package br.com.cauezito.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.cauezito.model.Telefone;
import br.com.cauezito.model.Usuario;
import br.com.cauezito.repository.ProfissaoRepository;
import br.com.cauezito.repository.TelefoneRepository;
import br.com.cauezito.repository.UsuarioRepository;
import br.com.cauezito.util.ReportUtil;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private ProfissaoRepository profissaoRepository;
	
	@Autowired
	private ReportUtil report;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroUsuario")
	public ModelAndView inicio() {
		ModelAndView mv = new ModelAndView("cadastro/usuario");
		Iterable<Usuario> usuarios = usuarioRepository.findAll();
		mv.addObject("usuarios", usuarios);
		mv.addObject("usuario", new Usuario());
		mv.addObject("profissoes", profissaoRepository.findAll());
		return mv;
	}
	// ** = ignora tudo o que vier antes na url
	@RequestMapping(method = RequestMethod.POST, value = "**/salvarUsuario")
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("cadastro/usuario");
		mv.addObject("profissoes", profissaoRepository.findAll());
		if(bindingResult.hasErrors()) {
			Iterable<Usuario> usuarios = usuarioRepository.findAll();
			mv.addObject("usuarios", usuarios);
			mv.addObject("usuario", usuario);
			List<String> mensagens = new ArrayList<String>();
			for(ObjectError error: bindingResult.getAllErrors()) {
				mensagens.add(error.getDefaultMessage()); //Anotações @ da entity
			}
			mv.addObject("msg", mensagens);
			return mv;
		}
		usuarioRepository.save(usuario);		
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
		mv.addObject("profissoes", profissaoRepository.findAll());
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
	public ModelAndView pesquisarUsuario(@RequestParam("nomePesquisado") String nomePesquisado,
			@RequestParam("generoPesquisado") String generoPesquisado) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		if(generoPesquisado != null && !generoPesquisado.isEmpty()) {
			usuarios = usuarioRepository.buscaUsuarioPorNomeEGenero(nomePesquisado, generoPesquisado);
		} else {
			usuarios = usuarioRepository.buscaUsuarioPorNome(nomePesquisado);
		}
		
		ModelAndView mv = new ModelAndView("cadastro/usuario");
		mv.addObject("usuarios", usuarios);
		mv.addObject("usuario", new Usuario());
		return mv;
	}
	
	@GetMapping("**/pesquisarUsuario")
	public void imprimirPdf(@RequestParam("nomePesquisado") String nomePesquisado,
			@RequestParam("generoPesquisado") String generoPesquisado, HttpServletRequest request,
			HttpServletResponse response) {
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		if(generoPesquisado != null && !generoPesquisado.isEmpty()
				&& nomePesquisado != null && !nomePesquisado.isEmpty()) {
				usuarios = usuarioRepository.buscaUsuarioPorNomeEGenero(nomePesquisado, generoPesquisado);
		} else if (nomePesquisado != null && !nomePesquisado.isEmpty()) {
				usuarios = usuarioRepository.buscaUsuarioPorNome(nomePesquisado);
		} else if (generoPesquisado != null && !generoPesquisado.isEmpty()){
				usuarios = usuarioRepository.buscaUsuarioPorGenero(generoPesquisado);
		} else {
				Iterable<Usuario> iterable = usuarioRepository.findAll();
				for (Usuario usuario : iterable) {
					usuarios.add(usuario);
				}
		}
		
		//gerador de relatório
		byte[] pdf = report.gerarRelatorio(usuarios, "Usuarios", request.getServletContext());
		
		//define o tamanho 
		response.setContentLength(pdf.length);
		//define o tipo de arquivo
		response.setContentType("application/octet-stream");
		//define o cabeçalho 
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "Usuarios.pdf");
		response.setHeader(headerKey, headerValue);
		//finaliza resposta para o navegador
		try {
			response.getOutputStream().write(pdf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/telefones/{id}")
	public ModelAndView exibeTelefones(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("cadastro/telefones");
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		mv.addObject("usuario", usuario.get());
		mv.addObject("telefones", telefoneRepository.getTelefones(id));
		return mv;
	}
	
	@PostMapping("**/addTelefone/{id}")
	public ModelAndView adicionarTelefone(Telefone telefone, 	
			@PathVariable("id") Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		telefone.setUsuario(usuario);
		telefoneRepository.save(telefone);
		ModelAndView mv = new ModelAndView("cadastro/telefones");
		mv.addObject("telefones", telefoneRepository.getTelefones(id));
		mv.addObject("usuario", usuario);
		
		return mv;
	}
	
	@GetMapping("**/excluirTelefone/{idTelefone}")
	public ModelAndView excluirTelefone(@PathVariable("idTelefone") Long id) {
		Usuario usuario = telefoneRepository.findById(id).get().getUsuario();
		ModelAndView mv = new ModelAndView("cadastro/telefones");
		telefoneRepository.deleteById(id);
		mv.addObject("usuario", usuario);
		mv.addObject("telefones", usuario.getTelefones());
		return mv;
	}
	
}
