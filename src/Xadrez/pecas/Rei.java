package Xadrez.pecas;

import JogoTabuleiro.Tabuleiro;
import Xadrez.Cores;
import Xadrez.PecasXadrez;

public class Rei extends PecasXadrez {

	public Rei(Tabuleiro tabuleiro, Cores cores) {
		super(tabuleiro, cores);
	}
	
	@Override
	public String toString() {
		return "R";
	}

}
