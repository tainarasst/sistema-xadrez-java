package Xadrez;

import JogoTabuleiro.Pecas;
import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;

public abstract class PecasXadrez extends Pecas {
	
	private Cores cores;

	public PecasXadrez(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro);
		this.cores = cores;
	}

	public Cores getCores() {
		return cores;
	}

	protected boolean existePecaOponente(Posicao posicao) {
		PecasXadrez p = (PecasXadrez)getTabuleiro().pecas(posicao);
		return p != null && p.getCores() != cores;
	}
	
}
