package JogoTabuleiro;

public abstract class Pecas {
	
	protected Posicao posicao;
	private Tabuleiro tabuleiro;
	
	public Pecas(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}

	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public abstract boolean[][] possibMover();
	
	public boolean possibMover(Posicao posicao) {
		return possibMover()[posicao.getLinha()][posicao.getColuna()];
	}
	
	public boolean peloMenosUmMov() {
		boolean[][] mat = possibMover();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if(mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
}
