package Aplicação;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Xadrez.ExcecaoXadrez;
import Xadrez.PartidaXadrez;
import Xadrez.PecasXadrez;
import Xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		List<PecasXadrez> captura = new ArrayList<>();
		
		
		
		while (true) {
			try {
				UI.limparTela();
				UI.printPartida(partidaXadrez, captura);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
	
				boolean[][] possibMover = partidaXadrez.possibMover(origem);
				UI.limparTela();
				UI.printTabuleiro(partidaXadrez.getPecas(), possibMover);
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
	
				PecasXadrez capturadaPecas = partidaXadrez.exeMoviXadrez(origem, destino);
				
				if(capturadaPecas != null) {
					captura.add(capturadaPecas);
				}
		}
			catch (ExcecaoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
		}

	}
}
