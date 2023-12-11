package Xadrez.pecas;

import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;
import Xadrez.Cores;
import Xadrez.PartidaXadrez;
import Xadrez.PecasXadrez;

public class Rei extends PecasXadrez {

	private PartidaXadrez partidaXadrez;

	public Rei(Tabuleiro tabuleiro, Cores cores, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cores);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public String toString() {
		return "R";
	}

	private boolean podeMover(Posicao posicao) {
		PecasXadrez p = (PecasXadrez) getTabuleiro().pecas(posicao);
		return p == null || p.getCores() != getCores();
	}

	private boolean testeRoqueTorre(Posicao posicao) {
		PecasXadrez p = (PecasXadrez) getTabuleiro().pecas(posicao);
		return p != null && p instanceof Torre && p.getCores() == getCores() && p.getContarMov() == 0;
	}

	@Override
	public boolean[][] possibMover() {
		boolean[][] mat = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];

		Posicao p = new Posicao(0, 0);

		// above
		p.setValores(posicao.getLinha() - 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// below
		p.setValores(posicao.getLinha() + 1, posicao.getColuna());
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// left
		p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// right
		p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// noroeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// nordeste
		p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudoeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// sudeste
		p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		if (getTabuleiro().posicaoExiste(p) && podeMover(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Jogada Especial - Roque
		if (getContarMov() == 0 && !partidaXadrez.getCheck()) {
			// Movimento Especial - Roque Pqueno
			Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
			if (testeRoqueTorre(posT1)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
				if (getTabuleiro().pecas(p1) == null && getTabuleiro().pecas(p2) == null) {
					mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
				}
			}
			// Jogada Especial - Roque Grande
			Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
			if (testeRoqueTorre(posT2)) {
				Posicao p1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				Posicao p2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
				Posicao p3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
				if (getTabuleiro().pecas(p1) == null && getTabuleiro().pecas(p2) == null && getTabuleiro().pecas(p3) == null) {
					mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
				}
			}

		}

		return mat;
	}

}
