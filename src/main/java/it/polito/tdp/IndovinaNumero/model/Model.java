package it.polito.tdp.IndovinaNumero.model;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

public class Model {
	
	private int NMAX;
	private int TMAX;
	private int segreto;
	private int tentativiFatti;
	private boolean inGioco = false;
	
	private Set<Integer> tentativi;
	
	public void nuovaParita(int difficolta) {
		
		if (difficolta == 1) {
			NMAX = 10;
			TMAX = 5;
		} else if (difficolta == 2) {
			NMAX = 100;
			TMAX = 8;
		} else if (difficolta == 3) {
			NMAX = 150;
			TMAX = 7;
		}
		
		//gestione inizio nuova partita
    	this.segreto = (int) (Math.random() * NMAX) + 1; //0 non è infatti ammissibile
    	this.tentativiFatti = 0;
    	this.inGioco = true;
    	
    	this.tentativi = new HashSet<Integer>();
	}
	
	public int tentativo(int tentativo) {
		//controllo se la partita è in corso
		if(!inGioco) {
			throw new IllegalStateException("Hai perso, il segreto era: " + this.segreto);
		}
		
		//controllo dell'input
		if(!tentativoValido(tentativo)) {
			throw new InvalidParameterException("Devi inserire un numero tra 1 e " + this.NMAX + ", non puoi inoltre inserire due volte lo stesso numero");
		}
		
		//il tentativo è valido
		this.tentativiFatti ++;
		this.tentativi.add(tentativo);
		
		if (this.tentativiFatti == (TMAX-1)) {
			this.inGioco = false;
		}
		
		if (tentativo == this.segreto) {
			this.inGioco = false;
			return 0;
		}
		else if (tentativo < this.segreto) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
	private boolean tentativoValido(int tentativo) {
		if(tentativo<1 || tentativo > NMAX)
			return false;
		if (tentativi.contains(tentativo))
			return false;
		return true;
	}

	public int getNMAX() {
		return NMAX;
	}

	public int getTMAX() {
		return TMAX;
	}

	public int getSegreto() {
		return segreto;
	}

	public int getTentativiFatti() {
		return tentativiFatti;
	}

	public boolean isInGioco() {
		return inGioco;
	}



}
