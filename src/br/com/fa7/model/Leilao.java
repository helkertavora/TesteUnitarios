package br.com.fa7.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Leilao {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	@Temporal(TemporalType.DATE)
	private Calendar data;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Lance> lances;
	
	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances  = new ArrayList<Lance>();
	}
	
	public Leilao(){}
	
	public void propoe(Lance lance){
		if(lances.isEmpty() || podeDarLance(lance)){
		lances.add(lance);
		}
	}
	
	private boolean podeDarLance(Lance lance) {
		return !ultimoLanceDado().getUsuario().equals(lance.getUsuario()) && totalDeLances(lance) < 5;
		}
	
	public int totalDeLances(Lance lance){
		int total = 0;
		for(Lance l : lances) {
		if(l.getUsuario().equals(lance.getUsuario())) 
			total++;
		}
		return total;
	}
	
	private Lance ultimoLanceDado(){
		return lances.get(lances.size()-1);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	public void setLances(List<Lance> lances) {
		this.lances = lances;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public boolean encerra() {
		return true;
	}
	
}
