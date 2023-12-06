package Xadrez;

import JogoTabuleiro.Pecas;
import JogoTabuleiro.Tabuleiro;

public class PecasXadrez extends Pecas {
	
	private Cores cores;

	public PecasXadrez(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro);
		this.cores = cores;
	}

	public Cores getCores() {
		return cores;
	}

}
