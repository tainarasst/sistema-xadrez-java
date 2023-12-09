package Xadrez;

import JogoTabuleiro.Pecas;
import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;

public abstract class PecasXadrez extends Pecas {
	
	private Cores cores;
	private int contarMov;

	public PecasXadrez(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro);
		this.cores = cores;
	}

	public Cores getCores() {
		return cores;
	}
	
	public int getContarMov() {
		return contarMov;
	}
	
	public void aumentarContarMov() {
		contarMov++;
	}
	
	public void diminuirContarMov() {
		contarMov--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.paraPosicao(posicao);
	}

	protected boolean existePecaOponente(Posicao posicao) {
		PecasXadrez p = (PecasXadrez)getTabuleiro().pecas(posicao);
		return p != null && p.getCores() != cores;
	}
	
}
