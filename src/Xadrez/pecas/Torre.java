package Xadrez.pecas;

import JogoTabuleiro.Tabuleiro;
import Xadrez.Cores;
import Xadrez.PecasXadrez;

public class Torre extends PecasXadrez {

	public Torre(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro, cores);
	}
	
	@Override
	public String toString() {
		return "T";
	}

	@Override
	public boolean[][] possibMover() {
		boolean[][] mat = new boolean[getTabuleiro().getLinha()][getTabuleiro().getColuna()];
		return mat;
	}
	
}
