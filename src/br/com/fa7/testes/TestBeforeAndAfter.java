package br.com.fa7.testes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBeforeAndAfter {

	@BeforeClass
	public static void setUpBeforeClass(){
		System.out.println("BeforeClass (Chamado apenas 1 unica vez na classe de teste)");
	}
	
	@Before
	public void setUpBefore() throws Exception {
		System.out.println("Before (Chamado sempre no Inicio de cada metodo de teste)");
	}
	@Test
	public void test1() {
		System.out.println("sou Test 1");
	}
	@Test
	public void test2() {
		System.out.println("sou Test 2");
	}
	@Test
	public void test3() {
		System.out.println("sou test 3");
	}
	@After
	public void after() {
		System.out.println("After (Chamado sempre ao Final de cada metodo de teste)");
	}
	@AfterClass
	public static void afterClass() {
		System.out.println("AfterClass (Chamado apenas 1 unica vez na classe de teste)");
	}

}
