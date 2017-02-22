package br.com.fa7.testes;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import br.com.fa7.dao.LeilaoDao;
import br.com.fa7.model.CriadorDeLeilao;
import br.com.fa7.model.EncerradorDeLeilao;
import br.com.fa7.model.Lance;
import br.com.fa7.model.Leilao;
import br.com.fa7.model.Usuario;

public class LeilaoTest {
	
	private Leilao leilao;
	
	@Before
	public void setUp() throws Exception {
		leilao = new CriadorDeLeilao().para("Macbook Pro 15").constroi();
	}

	@Test
	public void deveReceberUmLance() {
		assertEquals(0, leilao.getLances().size());
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000.0));
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void deveReceberVariosLances() {
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000.0));
		leilao.propoe(new Lance(new Usuario("Steve Wozniak"), 3000.0));
		assertEquals(2, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(3000, leilao.getLances().get(1).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Usuario steveJobs = new Usuario("Steve Jobs");
		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(steveJobs, 3000.0));
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
		Usuario steveJobs = new Usuario("Steve Jobs");
		Usuario billGates = new Usuario("Bill Gates");
		leilao.propoe(new Lance(steveJobs, 2000.0));
		leilao.propoe(new Lance(billGates, 3000.0));
		leilao.propoe(new Lance(steveJobs, 4000.0));
		leilao.propoe(new Lance(billGates, 5000.0));
		leilao.propoe(new Lance(steveJobs, 6000.0));
		leilao.propoe(new Lance(billGates, 7000.0));
		leilao.propoe(new Lance(steveJobs, 8000.0));
		leilao.propoe(new Lance(billGates, 9000.0));
		leilao.propoe(new Lance(steveJobs, 10000.0));
		leilao.propoe(new Lance(billGates, 11000.0));
		// deve ser ignorado
		leilao.propoe(new Lance(steveJobs, 12000.0));
		
		assertEquals(10, leilao.getLances().size());
		int ultimo = leilao.getLances().size() - 1;
		Lance ultimoLance = leilao.getLances().get(ultimo);
		assertEquals(11000.0, ultimoLance.getValor(), 0.00001);
	}
	
	@Test
	public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(2016, 2, 22);
		
		Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
		
		LeilaoDao daoFalso = new LeilaoDao();
		daoFalso.salva(leilao1);
		daoFalso.salva(leilao2);
		EncerradorDeLeilao encerrador = new EncerradorDeLeilao();
		encerrador.encerra();
		assertEquals(2, encerrador.getTotalEncerrados());
		assertTrue(leilao1.encerra());
		assertTrue(leilao2.encerra());
	}
	
}



