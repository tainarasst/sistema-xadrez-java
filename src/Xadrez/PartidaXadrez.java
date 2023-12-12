package Xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import JogoTabuleiro.Pecas;
import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;
import Xadrez.pecas.Bispo;
import Xadrez.pecas.Cavalo;
import Xadrez.pecas.Peao;
import Xadrez.pecas.Rainha;
import Xadrez.pecas.Rei;
import Xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecasXadrez enPassantVulneravel;
	private PecasXadrez promocao;

	private List<Pecas> pecasNoTabuleiro = new ArrayList<>();
	private List<Pecas> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cores.BRANCO;
		confInicial();
	}

	public int getTurno() {
		return turno;
	}

	public Cores getJogadorAtual() {
		return jogadorAtual;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public PecasXadrez getEnPassantVulneravel() {
		return enPassantVulneravel;
	}

	public PecasXadrez getPromocao() {
		return promocao;
	}

	public PecasXadrez[][] getPecas() {
		PecasXadrez[][] mat = new PecasXadrez[tabuleiro.getLinha()][tabuleiro.getColuna()];
		for (int i = 0; i < tabuleiro.getLinha(); i++) {
			for (int j = 0; j < tabuleiro.getColuna(); j++) {
				mat[i][j] = (PecasXadrez) tabuleiro.pecas(i, j);
			}
		}
		return mat;
	}

	public boolean[][] possibMover(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.daPosicao();
		validarPosicaoOrigem(posicao);
		return tabuleiro.pecas(posicao).possibMover();
	}

	public PecasXadrez exeMoviXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.daPosicao();
		Posicao destino = posicaoDestino.daPosicao();
		validarPosicaoOrigem(origem);
		validarPosicaoDestino(origem, destino);
		Pecas pecaCapturada = fazerMover(origem, destino);

		if (testeCheck(jogadorAtual)) {
			desfazerMover(origem, destino, pecaCapturada);
			throw new ExcecaoXadrez("Voce nao pode se colocar em Check.");
		}

		PecasXadrez moverPeca = (PecasXadrez) tabuleiro.pecas(destino);

		// Jogada Especial - Promocao
		promocao = null;
		if (moverPeca instanceof Peao) {
			if (moverPeca.getCores() == Cores.BRANCO && destino.getLinha() == 0
					|| moverPeca.getCores() == Cores.PRETO && destino.getLinha() == 7) {
				promocao = (PecasXadrez) tabuleiro.pecas(destino);
				promocao = substituirPecaPromocao("Q");
			}
		}

		check = (testeCheck(oponente(jogadorAtual))) ? true : false;

		if (testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		} else {
			proximoTurno();
		}

		// Jogada Especial en Passant
		if (moverPeca instanceof Peao
				&& (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
			enPassantVulneravel = moverPeca;
		} else {
			enPassantVulneravel = null;
		}

		return (PecasXadrez) pecaCapturada;
	}

	public PecasXadrez substituirPecaPromocao(String tipo) {
		if (promocao == null) {
			throw new IllegalStateException("Nao ha pecas para ser promovida!");
		}
		if (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("Q")) {
			return promocao;
		}

		Posicao pos = promocao.getPosicaoXadrez().daPosicao();
		Pecas p = tabuleiro.removePeca(pos);
		pecasNoTabuleiro.remove(p);

		PecasXadrez novaPeca = novaPeca(tipo, promocao.getCores());
		tabuleiro.lugarPeca(novaPeca, pos);
		pecasNoTabuleiro.add(novaPeca);
		
		return novaPeca;
	}

	private PecasXadrez novaPeca(String tipo, Cores cores) {
		if (tipo.equals("B")) return new Bispo(tabuleiro, cores);
		if (tipo.equals("C")) return new Cavalo(tabuleiro, cores);
		if (tipo.equals("Q")) return new Rainha(tabuleiro, cores);
		return new Torre(tabuleiro, cores);
	}

	
	private Pecas fazerMover(Posicao origem, Posicao destino) {
		PecasXadrez p = (PecasXadrez) tabuleiro.removePeca(origem);
		p.aumentarContarMov();
		Pecas pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.lugarPeca(p, destino);

		if (pecasCapturadas != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}

		// #Jogada Especial - Roque Pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecasXadrez torre = (PecasXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.lugarPeca(torre, destinoT);
			torre.aumentarContarMov();
		}

		// #Jogada Especial - Roque Grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecasXadrez torre = (PecasXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.lugarPeca(torre, destinoT);
			torre.aumentarContarMov();
		}

		// Jogada Especial en Passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
				Posicao peaoPosicao;
				if (p.getCores() == Cores.BRANCO) {
					peaoPosicao = new Posicao(destino.getLinha() + 1, destino.getColuna());
				} else {
					peaoPosicao = new Posicao(destino.getLinha() - 1, destino.getColuna());
				}
				pecaCapturada = tabuleiro.removePeca(peaoPosicao);
				pecasCapturadas.add(pecaCapturada);
				pecasNoTabuleiro.remove(pecaCapturada);
			}
		}

		return pecaCapturada;
	}

	private void desfazerMover(Posicao origem, Posicao destino, Pecas pecaCapturada) {
		PecasXadrez p = (PecasXadrez) tabuleiro.removePeca(destino);
		p.diminuirContarMov();
		tabuleiro.lugarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}

		// #Jogada Especial - Roque Pequeno
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() + 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
			PecasXadrez torre = (PecasXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.lugarPeca(torre, origemT);
			torre.diminuirContarMov();
		}

		// #Jogada Especial - Roque Grande
		if (p instanceof Rei && destino.getColuna() == origem.getColuna() - 2) {
			Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
			Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
			PecasXadrez torre = (PecasXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.lugarPeca(torre, origemT);
			torre.diminuirContarMov();
		}

		// Jogada Especial en Passant
		if (p instanceof Peao) {
			if (origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulneravel) {
				PecasXadrez peao = (PecasXadrez) tabuleiro.removePeca(destino);
				Posicao peaoPosicao;
				if (p.getCores() == Cores.BRANCO) {
					peaoPosicao = new Posicao(3, destino.getColuna());
				} else {
					peaoPosicao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.lugarPeca(peao, peaoPosicao);
			}
		}

	}

	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new ExcecaoXadrez("Nao existe pecas na posicao de origem.");
		}
		if (jogadorAtual != ((PecasXadrez) tabuleiro.pecas(posicao)).getCores()) {
			throw new ExcecaoXadrez("A peca escolhida nao e sua.");
		}
		if (!tabuleiro.pecas(posicao).peloMenosUmMov()) {
			throw new ExcecaoXadrez("Nao ha movimento possivel para a peca escolhida.");
		}
	}

	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.pecas(origem).possibMover(destino)) {
			throw new ExcecaoXadrez("A peca escolhida nao pode se mover para a posicao de destino.");
		}
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Cores.BRANCO) ? Cores.PRETO : Cores.BRANCO;
	}

	private Cores oponente(Cores cores) {
		return (cores == Cores.BRANCO) ? Cores.PRETO : Cores.BRANCO;
	}

	private PecasXadrez rei(Cores cores) {
		List<Pecas> list = pecasNoTabuleiro.stream().filter(x -> ((PecasXadrez) x).getCores() == cores)
				.collect(Collectors.toList());
		for (Pecas p : list) {
			if (p instanceof Rei) {
				return (PecasXadrez) p;
			}
		}
		throw new IllegalStateException("Nao existe o rei na cor " + cores + "no tabuleiro.");
	}

	private boolean testeCheck(Cores cores) {
		Posicao reiPosicao = rei(cores).getPosicaoXadrez().daPosicao();
		List<Pecas> oponentePecas = pecasNoTabuleiro.stream()
				.filter(x -> ((PecasXadrez) x).getCores() == oponente(cores)).collect(Collectors.toList());
		for (Pecas p : oponentePecas) {
			boolean[][] mat = p.possibMover();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeCheckMate(Cores cores) {
		if (!testeCheck(cores)) {
			return false;
		}
		List<Pecas> list = pecasNoTabuleiro.stream().filter(x -> ((PecasXadrez) x).getCores() == cores)
				.collect(Collectors.toList());
		for (Pecas p : list) {
			boolean[][] mat = p.possibMover();
			for (int i = 0; i < tabuleiro.getLinha(); i++) {
				for (int j = 0; j < tabuleiro.getColuna(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecasXadrez) p).getPosicaoXadrez().daPosicao();
						Posicao destino = new Posicao(i, j);
						Pecas pecaCapturada = fazerMover(origem, destino);
						boolean testeCheck = testeCheck(cores);
						desfazerMover(origem, destino, pecaCapturada);
						if (!testeCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void lugarNovaPeca(char coluna, int linha, PecasXadrez pecas) {
		tabuleiro.lugarPeca(pecas, new PosicaoXadrez(coluna, linha).daPosicao());
		pecasNoTabuleiro.add(pecas);
	}

	private void confInicial() {
		lugarNovaPeca('a', 1, new Torre(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('b', 1, new Cavalo(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('c', 1, new Bispo(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('d', 1, new Rainha(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('e', 1, new Rei(tabuleiro, Cores.BRANCO, this));
		lugarNovaPeca('f', 1, new Bispo(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('g', 1, new Cavalo(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('h', 1, new Torre(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('a', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		lugarNovaPeca('b', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		lugarNovaPeca('c', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		lugarNovaPeca('d', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		lugarNovaPeca('e', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		lugarNovaPeca('f', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		lugarNovaPeca('g', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		lugarNovaPeca('h', 2, new Peao(tabuleiro, Cores.BRANCO, this));

		lugarNovaPeca('a', 8, new Torre(tabuleiro, Cores.PRETO));
		lugarNovaPeca('b', 8, new Cavalo(tabuleiro, Cores.PRETO));
		lugarNovaPeca('c', 8, new Bispo(tabuleiro, Cores.PRETO));
		lugarNovaPeca('d', 8, new Rainha(tabuleiro, Cores.PRETO));
		lugarNovaPeca('e', 8, new Rei(tabuleiro, Cores.PRETO, this));
		lugarNovaPeca('f', 8, new Bispo(tabuleiro, Cores.PRETO));
		lugarNovaPeca('g', 8, new Cavalo(tabuleiro, Cores.PRETO));
		lugarNovaPeca('h', 8, new Torre(tabuleiro, Cores.PRETO));
		lugarNovaPeca('a', 7, new Peao(tabuleiro, Cores.PRETO, this));
		lugarNovaPeca('b', 7, new Peao(tabuleiro, Cores.PRETO, this));
		lugarNovaPeca('c', 7, new Peao(tabuleiro, Cores.PRETO, this));
		lugarNovaPeca('d', 7, new Peao(tabuleiro, Cores.PRETO, this));
		lugarNovaPeca('e', 7, new Peao(tabuleiro, Cores.PRETO, this));
		lugarNovaPeca('f', 7, new Peao(tabuleiro, Cores.PRETO, this));
		lugarNovaPeca('g', 7, new Peao(tabuleiro, Cores.PRETO, this));
		lugarNovaPeca('h', 7, new Peao(tabuleiro, Cores.PRETO, this));
	}

}
