package br.com.fa7.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Conexao {
	
	static EntityManager em = null;

	public static EntityManager getConnection (){
		return em = (EntityManager) Persistence.createEntityManagerFactory("unidadePersistencia").createEntityManager();
	}
}
