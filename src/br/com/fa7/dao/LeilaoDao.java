package br.com.fa7.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.fa7.model.Leilao;

public class LeilaoDao {
	
	private EntityManager con = Conexao.getConnection();
	
	public List<Leilao> listarLeiloes(){
		return this.con.createQuery("select c from Leilao c order by id", Leilao.class).getResultList();
	}

	public List<Leilao> encerrados() {
		List<Leilao> filtrados = new ArrayList<Leilao>();
		for (Leilao leilao : listarLeiloes()) {
			if (leilao.encerra())
				filtrados.add(leilao);
		}
		return filtrados;
	}

	public List<Leilao> correntes() {
		List<Leilao> filtrados = new ArrayList<Leilao>();
		for (Leilao leilao : listarLeiloes()) {
			if (leilao.encerra())
				filtrados.add(leilao);
		}
		return filtrados;
	}

	public void atualiza(Leilao leilao) {
		this.con.merge(leilao);
		this.con.flush();
	}

}