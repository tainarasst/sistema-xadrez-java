package Xadrez;

import JogoTabuleiro.Tabuleiro;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
	}
	
	public PecasXadrez[][] getPecas(){
		PecasXadrez[][] pex = new  PecasXadrez[tabuleiro.getLinha()][tabuleiro.getColuna()];
		for (int i=0; i<tabuleiro.getLinha(); i++) {
			for (int j=0; j<tabuleiro.getColuna(); j++) {
				pex[i][j] = (PecasXadrez) tabuleiro.pecas(i, j);
			}
		}
		return pex;
	}
	
}
