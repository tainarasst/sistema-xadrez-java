package Aplicação;

import java.util.Scanner;

import Xadrez.PartidaXadrez;
import Xadrez.PecasXadrez;
import Xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		PartidaXadrez partidaXadrez = new PartidaXadrez();
		
		while(true) {
		UI.printTatuleiro(partidaXadrez.getPecas());
		System.out.println();
		System.out.print("Origem: ");
		PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
		
		System.out.println();
		System.out.print("Destino: ");
		PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
		
		PecasXadrez capturadaPecas = partidaXadrez.exeMoviXadrez(origem, destino);
		
		}
		
	}
}
