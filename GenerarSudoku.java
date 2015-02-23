package sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerarSudoku {
	
	private static int TamanyQuadrant = 3;
	private static int TamanyQuadricula;
	private static int[][] cuadricula;
	private static int[] numeros;
	private static Random r = new Random();
	
	public static void main(String[] args) {
		
		if ((args.length>0) && (args[0].equals("2") || args[0].equals("3") || args[0].equals("4"))){
			TamanyQuadrant = Integer.parseInt(args[0]);
		}
		TamanyQuadricula=(int) Math.pow(TamanyQuadrant, 2);
		cuadricula=new int[TamanyQuadricula][TamanyQuadricula];
		numeros = new int[TamanyQuadricula];
				
		for (int i= 0; i < TamanyQuadricula; i++) 
			numeros[i] = i + 1;	

		int intentosFila = 0, nFilaAnt = 0;
		int nFila = 0;
		reinicioCuadricula:
		while (nFila < TamanyQuadricula){
			reinicioFila:
			for (int nColumna = 0; nColumna < TamanyQuadricula; nColumna++){
				List<Integer> numsPosibles = buscarNumsPosibles(nFila, nColumna);
				int numeroAleatorio = generaNumeroAleatorio(numsPosibles);
				if (numeroAleatorio != 0) {
					cuadricula[nFila][nColumna] = numeroAleatorio; 
				}else{
					if (nFila != nFilaAnt){
						nFilaAnt = nFila;
						intentosFila = 0;
					}
					intentosFila++;
					if (intentosFila < TamanyQuadricula){
						nFila--;
						break reinicioFila;
					}else{
						intentosFila=0;
						nFila=0;
						cuadricula=new int[TamanyQuadricula][TamanyQuadricula];
						continue reinicioCuadricula;
					}						
				}
   				System.out.print(cuadricula[nFila][nColumna]+" ");
			}
			System.out.println();
			nFila++;
		}
		
		System.out.println();
		int numero;
		for (nFila = 0; nFila < TamanyQuadricula; nFila++){
			for (int nColumna = 0; nColumna < TamanyQuadricula; nColumna++){
   					numero = cuadricula[nFila][nColumna];
				if (numero < 10){
	   				System.out.print(numero+" ");
				}else{
					numero = (numero-10+65);
					System.out.print((char)numero+" ");
				}
			}
			System.out.println();
		}
	}
	
	private static List<Integer> buscarNumsPosibles(int nFila, int nColumna) {
		List<Integer> numsPosibles = new ArrayList<Integer>();
		for (int num : numeros){
			if (!numeroEnFila(num, nFila, nColumna)){
				if (!numeroEnColumna(num, nFila, nColumna)){
					if (!numeroEnCuadricula(num, nFila, nColumna)){
						numsPosibles.add(num);
					}
				}
			}
		}
		return numsPosibles;
	}
	
	private static boolean numeroEnFila(int num, int nFila, int nColumna) {
		boolean numEncontrado=false;
		for (int i = 0; i < nColumna; i++){
			if(cuadricula[nFila][i] == num){
				numEncontrado = true;
				break;
			}
		} 
		return numEncontrado;
	}

	private static boolean numeroEnColumna(int num, int nFila, int nColumna) {
		boolean numEncontrado=false;
		for (int i = 0; i < nFila; i++){
			if(cuadricula[i][nColumna] == num){
				numEncontrado = true;
				break;
			}
		} 
		return numEncontrado;
	}
	
	private static boolean numeroEnCuadricula(int num, int nFila, int nCol) {
		boolean encontrado = false;
		int posIniFila = determinaPosIni(nFila);
		int posIniCol = determinaPosIni(nCol);
		int posFinFila = posIniFila+TamanyQuadrant;
		int posFinCol = posIniCol+TamanyQuadrant;
		if (!(nFila == posIniFila && nCol == posIniCol)){
			for (int i=posIniFila; i<posFinFila; i++){
				for (int j=posIniCol; (j<posFinCol)&&!(i==nFila&&j==nCol); j++){
					if (cuadricula[i][j]==num) encontrado = true;
				}
			}
		}
		return encontrado;
	}

	private static int determinaPosIni(int nPos) {
		int i = nPos;
		while (!(i == 0 || i % TamanyQuadrant == 0)) i--;
		return i;
	}
	
	private static int generaNumeroAleatorio(List<Integer> numsPosibles) {
		switch (numsPosibles.size()){
			case 0:
				return 0;
			case 1:
				return numsPosibles.get(0);
			default:
				return numsPosibles.get(r.nextInt(numsPosibles.size()));
		}
	}
}
