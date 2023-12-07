package JogoTabuleiro;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private Pecas[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new ExcecaoTabuleiro("Erro criando tabuleiro: é necessario que haja pelo menos uma linha e uma coluna.");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Pecas[linhas][colunas];
	}

	public int getLinha() {
		return linhas;
	}

	public int getColuna() {
		return colunas;
	}
	
	public Pecas pecas(int linha, int coluna) {
		if(!posicaoExiste(linha, coluna)) {
			throw new ExcecaoTabuleiro("Nao existe esta posicao no tabuleiro.");
		}
		return pecas[linha][coluna];
	}
	
	public Pecas pecas(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new ExcecaoTabuleiro("Nao existe esta posicao no tabuleiro.");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void lugarPeca(Pecas peca, Posicao posicao) {
		if(haUmaPeca(posicao)) {
			throw new ExcecaoTabuleiro("Ja existe uma peça nesta posicao.");
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Pecas removePeca(Posicao posicao) {
		if (!posicaoExiste(posicao)) {
			throw new ExcecaoTabuleiro("Nao existe esta posicao no tabuleiro.");
		}
		if(pecas(posicao) == null) {
			return null;
		}
		Pecas aux = pecas(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >=0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean haUmaPeca(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new ExcecaoTabuleiro("Nao existe esta posicao no tabuleiro.");
		}
		return pecas(posicao) != null;
	}
	
}
