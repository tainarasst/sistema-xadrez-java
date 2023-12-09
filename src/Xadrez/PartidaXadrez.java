package Xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import JogoTabuleiro.Pecas;
import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;
import Xadrez.pecas.Rei;
import Xadrez.pecas.Torre;

public class PartidaXadrez {

	private int turno;
	private Cores jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;

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
	
	public PecasXadrez[][] getPecas(){
		PecasXadrez[][] mat = new  PecasXadrez[tabuleiro.getLinha()][tabuleiro.getColuna()];
		for (int i=0; i<tabuleiro.getLinha(); i++) {
			for (int j=0; j<tabuleiro.getColuna(); j++) {
				mat[i][j] = (PecasXadrez) tabuleiro.pecas(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibMover(PosicaoXadrez posicaoOrigem){
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
		
		if(testeCheck(jogadorAtual)) {
			desfazerMover(origem, destino, pecaCapturada);
			throw new ExcecaoXadrez("Voce nao pode se colocar em Check.");
		}
		
		check = (testeCheck(oponente(jogadorAtual))) ? true : false;
		
		if (testeCheckMate(oponente(jogadorAtual))) {
			checkMate = true;
		}
		else {
			proximoTurno();
		}
		
		return (PecasXadrez) pecaCapturada;
	}
	
	private Pecas fazerMover(Posicao origem, Posicao destino) {
		Pecas p = tabuleiro.removePeca(origem);
		Pecas pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.lugarPeca(p, destino);
		
		if(pecasCapturadas != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		
		return pecaCapturada;
	}
	
	private void desfazerMover(Posicao origem, Posicao destino, Pecas pecaCapturada) {
		Pecas p = tabuleiro.removePeca(destino);
		tabuleiro.lugarPeca(p, origem);
		
		if(pecaCapturada != null) {
			tabuleiro.lugarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}
	
	
	private void validarPosicaoOrigem(Posicao posicao) {
		if (!tabuleiro.haUmaPeca(posicao)) {
			throw new ExcecaoXadrez("Nao existe pecas na posicao de origem.");
		}
		if(jogadorAtual != ((PecasXadrez)tabuleiro.pecas(posicao)).getCores()){
			throw new ExcecaoXadrez("A peca escolhida nao e sua.");
		}
		if(!tabuleiro.pecas(posicao).peloMenosUmMov()) {
			throw new ExcecaoXadrez("Nao ha movimento possivel para a peca escolhida.");
		}
	}
	
	private void validarPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.pecas(origem).possibMover(destino)) {
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
		List<Pecas> list = pecasNoTabuleiro.stream().filter(x -> ((PecasXadrez)x).getCores() == cores).collect(Collectors.toList());
		for (Pecas p : list) {
			if (p instanceof Rei) {
				return (PecasXadrez)p;
			}
		}
		throw new IllegalStateException("Nao existe o rei na cor " + cores + "no tabuleiro.");
	}
	
	private boolean testeCheck (Cores cores) {
		Posicao reiPosicao = rei(cores).getPosicaoXadrez().daPosicao();
		List<Pecas> oponentePecas = pecasNoTabuleiro.stream().filter(x -> ((PecasXadrez)x).getCores() == oponente(cores)).collect(Collectors.toList());
		for (Pecas p : oponentePecas) {
			boolean[][] mat = p.possibMover();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testeCheckMate(Cores cores) {
		if(!testeCheck(cores)) {
			return false;
		}
		List<Pecas> list = pecasNoTabuleiro.stream().filter(x -> ((PecasXadrez)x).getCores() == cores).collect(Collectors.toList());
		for(Pecas p : list) {
			boolean[][] mat = p.possibMover();
			for(int i = 0; i < tabuleiro.getLinha(); i++) {
				for (int j = 0; j < tabuleiro.getColuna(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecasXadrez)p).getPosicaoXadrez().daPosicao();
						Posicao destino = new Posicao(i,j);
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
		lugarNovaPeca('h', 7, new Torre(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('d', 1, new Torre(tabuleiro, Cores.BRANCO));
		lugarNovaPeca('e', 1, new Rei(tabuleiro, Cores.BRANCO));
		
		lugarNovaPeca('b', 8, new Torre(tabuleiro, Cores.PRETO));
		lugarNovaPeca('a', 8, new Rei(tabuleiro, Cores.PRETO));
		}
	
}
