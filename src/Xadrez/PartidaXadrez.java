package Xadrez;

import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;
import Xadrez.pecas.Rei;
import Xadrez.pecas.Torre;

public class PartidaXadrez {

	private Tabuleiro tabuleiro;
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		confInicial();
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
	
	private void confInicial() {
		tabuleiro.lugarPeca(new Torre(tabuleiro, Cores.BRACO), new Posicao(2, 1));
		tabuleiro.lugarPeca(new Rei(tabuleiro, Cores.PRETO), new Posicao(0, 4));
		tabuleiro.lugarPeca(new Rei(tabuleiro, Cores.BRACO), new Posicao(7, 4));

	}
	
}
