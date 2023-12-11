package Xadrez.pecas;

import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;
import Xadrez.Cores;
import Xadrez.PartidaXadrez;
import Xadrez.PecasXadrez;

public class Peao extends PecasXadrez {
	
	private PartidaXadrez partidaXadrez;

	public Peao(Tabuleiro tabuleiro, Cores cores, PartidaXadrez partidaXadrez) {
		super(tabuleiro, cores);
		this.partidaXadrez = partidaXadrez;
	}

	@Override
	public boolean[][] possibMover() {
		boolean[][] mat = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];

		Posicao p = new Posicao(0, 0);
		
		if(getCores()== Cores.BRANCO) {
			p.setValores(posicao.getLinha() -1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() -2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() -1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().haUmaPeca(p2) && getContarMov() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() -1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() -1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//Jogada Especial en Passant Branco
			if(posicao.getLinha() == 3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(esquerda) && existePecaOponente(esquerda) && getTabuleiro().pecas(esquerda) == partidaXadrez.getEnPassantVulneravel()) {
					mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(direita) && existePecaOponente(direita) && getTabuleiro().pecas(direita) == partidaXadrez.getEnPassantVulneravel()) {
					mat[direita.getLinha() - 1][direita.getColuna()] = true;
				}
			}
		}
		else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().haUmaPeca(p2) && getContarMov() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExiste(p) && existePecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			//Jogada Especial en Passant Preto
			if(posicao.getLinha() == 4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if(getTabuleiro().posicaoExiste(esquerda) && existePecaOponente(esquerda) && getTabuleiro().pecas(esquerda) == partidaXadrez.getEnPassantVulneravel()) {
					mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if(getTabuleiro().posicaoExiste(direita) && existePecaOponente(direita) && getTabuleiro().pecas(direita) == partidaXadrez.getEnPassantVulneravel()) {
					mat[direita.getLinha() + 1][direita.getColuna()] = true;
				}
			}
		}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
	
}
