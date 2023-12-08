package Xadrez;

import JogoTabuleiro.Pecas;
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
		PecasXadrez[][] mat = new  PecasXadrez[tabuleiro.getLinha()][tabuleiro.getColuna()];
		for (int i=0; i<tabuleiro.getLinha(); i++) {
			for (int j=0; j<tabuleiro.getColuna(); j++) {
				mat[i][j] = (PecasXadrez) tabuleiro.pecas(i, j);
			}
		}
		return mat;
	}
	
	public PecasXadrez exeMoviXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.daPosicao();
		Posicao destino = posicaoDestino.daPosicao();
		validarPosicaoOrigem(origem);
		Pecas capturadaPecas = fazerMover(origem, destino);
		return (PecasXadrez) capturadaPecas;
	}
	
	private Pecas fazerMover(Posicao origem, Posicao destino) {
		Pecas p = tabuleiro.removePeca(origem);
		Pecas capturadaPecas = tabuleiro.removePeca(destino);
		tabuleiro.lugarPeca(p, destino);
		return capturadaPecas;
	}
	
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new ExcecaoXadrez("Nao existe pecas na posicao de origem.");
		}
		if(!tabuleiro.pecas(posicao).peloMenosUmMov()) {
			throw new ExcecaoXadrez("Nao ha movimento possivel para a peca escolhida.");
		}
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
