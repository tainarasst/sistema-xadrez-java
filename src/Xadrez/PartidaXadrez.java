package Xadrez;

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
	
	private void lugarNovaPeca(char coluna, int linha, PecasXadrez pecas) {
		tabuleiro.lugarPeca(pecas, new PosicaoXadrez(coluna, linha).daPosicao());
	}
	
	private void confInicial() {
		lugarNovaPeca('c', 1, new Torre(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('c', 2, new Torre(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('d', 2, new Torre(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('e', 2, new Torre(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('e', 1, new Torre(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('d', 1, new Rei(tabuleiro, Cores.BRANCO));

		lugarNovaPeca('c', 7, new Torre(tabuleiro, Cores.PRETO));
		lugarNovaPeca('c', 8, new Torre(tabuleiro, Cores.PRETO));
		lugarNovaPeca('d', 7, new Torre(tabuleiro, Cores.PRETO));
		lugarNovaPeca('e', 7, new Torre(tabuleiro, Cores.PRETO));
		lugarNovaPeca('e', 8, new Torre(tabuleiro, Cores.PRETO));
		lugarNovaPeca('d', 8, new Rei(tabuleiro, Cores.PRETO));
	}
	
}
