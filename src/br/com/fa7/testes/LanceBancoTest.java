package br.com.fa7.testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.fa7.dao.Conexao;
import br.com.fa7.model.CriadorDeLeilao;
import br.com.fa7.model.Lance;
import br.com.fa7.model.Leilao;
import br.com.fa7.model.Usuario;

public class LanceBancoTest {
	
	private static EntityManager em = null;
	private CriadorDeLeilao leilao;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;
	private Usuario marcos;
	private List<Usuario> listaUsuarios; 
	private List<Usuario> usuariosRetornoBanco;
	private List<Lance> lancesRetornoBanco;
	private List<Leilao> leiloesRetornoBanco;
	
	@BeforeClass
	public static void setUpConexao() throws Exception {
		 em = Conexao.getConnection();
	}

	@Before
	public void setUp() throws Exception {
		leilao = new CriadorDeLeilao().para("Notebook Dell");
		this.joao = new Usuario("João doria");
		this.jose = new Usuario("José mendoça");
		this.maria = new Usuario("Maria joaquina");
		this.marcos = new Usuario("Francisco Helker");
		listaUsuarios = new ArrayList<Usuario>();
		usuariosRetornoBanco();
		lancesRetornoBanco();
		leilaoRetornoBanco();
		em.getTransaction().begin();
	}

	@Test
	public void deveSalvar4UsuariosEmBanco() {
		listaUsuarios.add(marcos);
		listaUsuarios.add(jose);
		listaUsuarios.add(joao);
		listaUsuarios.add(maria);
		for (Usuario usuario : listaUsuarios) {
			em.persist(usuario);
			em.flush();
		}
		assertEquals(4, usuariosRetornoBanco().size(), 0.00001);
	}
	
	@Test
	public void salvandoLanceEmBancoComUsuario(){
		Double valor = 250.0;
		for (Usuario usuario : usuariosRetornoBanco()) {
			usuario = em.find(Usuario.class, usuario.getId());
			Lance lance = new Lance(usuario,valor);
			em.persist(lance);
			em.flush();
			valor += 250.0;
		}
		assertTrue(lancesRetornoBanco().iterator().next().getUsuario() != null);
	}
	
	@Test
	public void salvandoLeilaoCom4LancesEmBanco(){
		for (Lance lance : lancesRetornoBanco()) {
			leiloesRetornoBanco.add(leilao.lance(lance).naData(Calendar.getInstance()).constroi());
			leiloesRetornoBanco.add(leilao.lance(lance).naData(Calendar.getInstance()).constroi());
			leiloesRetornoBanco.add(leilao.lance(lance).naData(Calendar.getInstance()).constroi());
			leiloesRetornoBanco.add(leilao.lance(lance).naData(Calendar.getInstance()).constroi());
		}
		for (Leilao leilao : leiloesRetornoBanco) {
			em.persist(leilao);
			em.flush();
		}
		assertEquals(1, leilaoRetornoBanco().size());
	}
	
	@Test
	public void deveExcluirLancesEmMassa(){
		for (Lance lance : lancesRetornoBanco) {
			em.remove(lance);
			em.flush();
		}
		
		assertTrue(lancesRetornoBanco().isEmpty());
	}
	
	@Test
	public void deveExcluirUsuariosEmMassa(){
		for (Usuario usu : usuariosRetornoBanco()) {
			em.remove(usu);
			em.flush();
		}
		assertTrue(usuariosRetornoBanco().isEmpty());
	}
	
	@Test
	public void deveExcluirLeilao(){
		for (Leilao leilao : leilaoRetornoBanco()) {
			em.remove(leilao);
			em.flush();
		}
		assertTrue(leilaoRetornoBanco().isEmpty());
	}
	
	@After
	public void setUpFinal() throws Exception {
		em.getTransaction().commit();
	}
	
	@AfterClass
	public static void afterClass() {
		em.close();
	}

	public List<Usuario> usuariosRetornoBanco(){
		return usuariosRetornoBanco = em.createQuery("select u from Usuario u", Usuario.class).getResultList();
	}
	public List<Lance> lancesRetornoBanco(){
		return lancesRetornoBanco = em.createQuery("select l from Lance l", Lance.class).getResultList();
	}
	
	public List<Leilao> leilaoRetornoBanco(){
		return leiloesRetornoBanco = em.createQuery("select l from Leilao l", Leilao.class).getResultList();
	}

}
