import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Calculeaza numarul de moduri in care se pot imparti M bomboane la N studenti.
 */
public class Bomboane {
	
	static class Task {
		public static final String INPUT_FILE = "bomboane.in";
		public static final String OUTPUT_FILE = "bomboane.out";

		private static final long MOD = 1000000007;
		private int N, M;
		/**
		 * Lista cu intervalele de bomboane ale studentilor.
		 */
		private ArrayList<Interval> intervals = new ArrayList<Interval>();

		/**
		 * Citirea din fisier pentru a obtine datele necesare 
		 */
		private void readInput() {
			try {
				BufferedReader reader = (new BufferedReader(new FileReader(
						INPUT_FILE)));
				
				for (int i = 0; i <= N; i++) {
					String[] input = (new String(reader.readLine())).split(" ", 0);
					
					if (i == 0) {
						/* Obtinem numarul de studenti si de bomboane */
						N  = Integer.parseInt(input[0]);
						M  = Integer.parseInt(input[1]);
					} else {
						/* Stocam intervalele intr-un ArrayList */
						intervals.add(new Interval(input[0], input[1]));
					}
				}
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Afisarea rezultatului.
		 * @param result Numarul de moduri de impartire a bomboanelor.
		 */
		private void writeOutput(long result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
								OUTPUT_FILE)));
				
				pw.print(result % MOD);
				
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Calculeaza numarul de moduri de impartire a bomboanelor.
		 * @return Rezultatul problemei.
		 */
		private long get_solution() {
			/*
			 * Matrice de N * (M+1) necesara pentru programare dinamica.
			 */
			long[][] dp = new long[N][M + 1];

			/*
			 * Pe primul rand din matrice se pune 1 la indicii corespunzatori 
			 * numerelor din primul interval.
			 */
			for (int k = intervals.get(0).start; k <= intervals.get(0).end && k <= M; k++) {
				dp[0][k] = 1;
			}
			
			/*
			 * dp[m][n] reprezinta in cate moduri pot obtine m bomboane prin 
			 * combinatii de n intervale.
			 */
			for (int i = 1; i < N; i++) {
				for (int j = 0; j <= M; j++) {
					/*
					 * Reculenta este dp[i+1][j] = ∑ dp[i][j - k] unde j este numarul de 
					 * bomboane si k este fiecare numar din intervalul persoanei i.
					 */
					for (int k = intervals.get(i).start; k <= intervals.get(i).end; k++) {
						/* Daca (j - k) < 0, dp[i][j-k] = 0 */
						if (j - k < 0) {
							break;
						}
						/* Aplicarea reculentei */
						dp[i][j] = dp[i][j] % MOD + dp[i - 1][j - k] % MOD;
					}
				}
			}
			
			return dp[N - 1][M];
		}

		/**
		 * Rezolva Task-ul
		 */
		public void solve() {
			readInput();
			writeOutput(get_solution());
		}
		

	}
	/**
	 * Clasa care reprezinta intervalul de bomboane al unui student.
	 */
	static class Interval {
		int start,end;
		Interval(String start, String end) { 
			this.start = Integer.parseInt(start);
			this.end = Integer.parseInt(end);
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
