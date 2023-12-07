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
			throw new ExcecaoTabuleiro("Não existe está posição no tabuleiro.");
		}
		return pecas[linha][coluna];
	}
	
	public Pecas pecas(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new ExcecaoTabuleiro("Não existe está posição no tabuleiro.");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void lugarPeca(Pecas peca, Posicao posicao) {
		if(haUmaPeca(posicao)) {
			throw new ExcecaoTabuleiro("Já existe uma peça nesta posição.");
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	private boolean posicaoExiste(int linha, int coluna) {
		return linha >=0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	public boolean posicaoExiste(Posicao posicao) {
		return posicaoExiste(posicao.getLinha(), posicao.getColuna());
	}
	
	public boolean haUmaPeca(Posicao posicao) {
		if(!posicaoExiste(posicao)) {
			throw new ExcecaoTabuleiro("Não existe está posição no tabuleiro.");
		}
		return pecas(posicao) != null;
	}
	
}
