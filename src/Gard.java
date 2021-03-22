import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Calculeaza numarul de bucati redundante din gardul lui Gigel.
 */
public class Gard {
	static class Task {
		public static final String INPUT_FILE = "gard.in";
		public static final String OUTPUT_FILE = "gard.out";


		private int N;
		private static final long MOD = 1000000007;

		/**
		 * Afisarea rezultatului.
		 * @param result Numarul de bucati redundante
		 */
		private void writeOutput(long result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));

				pw.printf("%d", result % MOD);		
				
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Calculeaza numarul de bucati redundante.
		 * @return Rezultatul problemei
		 */
		private long get_solution() {
			/**
			 * Lista de bucati de gard.
			 */
			ArrayList<Piece> pieces = new ArrayList<Piece>();
			
			try {
				BufferedReader reader = (new BufferedReader(new FileReader(
								INPUT_FILE)));

				N = Integer.parseInt(reader.readLine());

				for (int i = 1; i <= N; i++) {
					String[] input = (new String(reader.readLine())).split(" ", 0);
					pieces.add(new Piece(input[0], input[1]));
				}
				
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			/* 
			 * Sorteaza intervalele dupa pozitia de start, eventual
			 * dupa lungimea intervalului.
			 */
			Collections.sort(pieces);
			
			
			for (int i = 0; i < pieces.size(); i++) {
				for (int j = i + 1; j < pieces.size(); j++) {
					/* 
					 * Putem sa nu mai verificam intervalul curent cand 
					 * interval1.end < interval2.start
					 */
					if (pieces.get(j).start > pieces.get(i).end) {
						break;
					}
					
					/* Eliminarea oricarui interval redundant */
					if (pieces.get(i).start <= pieces.get(j).start
						&& pieces.get(i).end >= pieces.get(j).end) {
						pieces.remove(j);
						j--;
					}
				}
			}
			
			return N - pieces.size();
		}

		/**
		 * Rezolva Task-ul
		 */
		public void solve() {
			writeOutput(get_solution());
		}
	}
	
	
	/**
	 * Clasa care reprezinta o bucata de gard, vazuta ca un interval 
	 */

	public static void main(String[] args) {
		new Task().solve();
	}
}

class Piece implements Comparable<Piece> {
	int start,end; 

	Piece(String start, String end) {
		this.start = Integer.parseInt(start); 
		this.end = Integer.parseInt(end); 
	}
	
	/**
	 * Suprascrierea metodei compareTo() pentru sortarea intervalelor
	 * dupa punctul de start si in caz de egalitate, dupa lungimea intervalului. 
	 */
	@Override
	public int compareTo(Piece interval) {
		if (start == interval.start) {
			return interval.end - end;
		}
		return start - interval.start;
	}
}