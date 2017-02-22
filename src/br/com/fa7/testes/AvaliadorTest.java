package br.com.fa7.testes;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.fa7.model.Avaliador;
import br.com.fa7.model.CriadorDeLeilao;
import br.com.fa7.model.Lance;
import br.com.fa7.model.Leilao;
import br.com.fa7.model.Usuario;

public class AvaliadorTest {
	
	
	private Avaliador leiloeiro; 
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;
	private Usuario helker;
	
	@Before
	public void setUp() throws Exception {
		this.leiloeiro = new Avaliador();
		this.joao = new Usuario("João");
		this.jose = new Usuario("José");
		this.maria = new Usuario("Maria");
		this.helker = new Usuario("Helker");
		
	}
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		Leilao leilao = new CriadorDeLeilao()
		.para("Playstation 3 Novo").lance(joao, 250.0).lance(jose, 300.0).lance(maria, 400.0).lance(helker, 700.0).constroi();
		leiloeiro.avalia(leilao);
		assertEquals(700.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(250.0, leiloeiro.getMenorLance(), 0.00001);
		
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Leilao leilao = new CriadorDeLeilao()
		.para("Playstation 3 Novo").lance(joao, 250.0).lance(jose, 300.0).lance(maria, 400.0).lance(helker, 800.0).constroi();
		leiloeiro.avalia(leilao);
		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertEquals(3, maiores.size());
		assertEquals(800.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(400.0, maiores.get(1).getValor(), 0.00001);
		assertEquals(300.0, maiores.get(2).getValor(), 0.00001);
	}
	
	@Test(expected=RuntimeException.class)
		public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").constroi();
		leiloeiro.avalia(leilao);
	}
}
