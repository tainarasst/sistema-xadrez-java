package Xadrez.pecas;

import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;
import Xadrez.Cores;
import Xadrez.PecasXadrez;

public class Bispo extends PecasXadrez {

	public Bispo(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro, cores);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibMover() {
		boolean[][] mat = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];

		Posicao p = new Posicao(0, 0);

		// Noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() -1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() -1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Nordeste
		p.setValores(posicao.getLinha() -1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setValores(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		return mat;
	}

}
