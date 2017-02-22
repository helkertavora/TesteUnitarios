package br.com.fa7.testes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.fa7.dao.Conexao;
import br.com.fa7.model.Lance;
import br.com.fa7.model.Usuario;

public class LanceBancoTest {
	
	private static EntityManager em = null;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;
	private Usuario helker;
	private List<Usuario> listaUsuarios; 
	private List<Usuario> usuariosRetornoBanco;
	private List<Lance> lancesRetornoBanco;
	
	@BeforeClass
	public static void setUpConexao() throws Exception {
		 em = Conexao.getConnection();
	}

	@Before
	public void setUp() throws Exception {
		this.joao = new Usuario("João doria");
		this.jose = new Usuario("José mendoça");
		this.maria = new Usuario("Maria joaquina");
		this.helker = new Usuario("Francisco Helker");
		listaUsuarios = new ArrayList<Usuario>();
		usuariosRetornoBanco = em.createQuery("select u from Usuario u", Usuario.class).getResultList();
		lancesRetornoBanco = em.createQuery("select l from Lance l", Lance.class).getResultList();
		em.getTransaction().begin();
	}

	@Test
	public void salvando4UsuariosEmBanco() {
		listaUsuarios.add(helker);
		listaUsuarios.add(jose);
		listaUsuarios.add(joao);
		listaUsuarios.add(maria);
		for (Usuario usuario : listaUsuarios) {
			em.persist(usuario);
			em.flush();
		}
		assertEquals(4, usuariosRetornoBanco.size(), 0.00001);
	}
	
	@Test
	public void salvandoLanceEmBancoComUsuario(){
		Double valor = 250.0;
		Long i = usuariosRetornoBanco.get(0).getId();
		for (Usuario usuario : usuariosRetornoBanco) {
			usuario = em.find(Usuario.class, i);
			Lance lance = new Lance(usuario,valor);
			em.persist(lance);
			em.flush();
			valor += 250.0;
			i += 1L;
		}
		assertTrue(lancesRetornoBanco.iterator().next().getUsuario() != null);
	}
	
	@Test
	public void deveExcluirLancesEmMassa(){
		for (Lance lance : lancesRetornoBanco) {
			em.remove(lance);
			em.flush();
		}
	}
	
	@After
	public void setUpFinal() throws Exception {
		em.getTransaction().commit();
	}
	
	@AfterClass
	public static void afterClass() {
		em.close();
	}


}
