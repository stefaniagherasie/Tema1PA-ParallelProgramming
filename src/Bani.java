import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Calculeaza numarul de moduri in care Gigel poate aranja N bacnote.
 */
public class Bani {
	static class Task {
		public static final String INPUT_FILE = "bani.in";
		public static final String OUTPUT_FILE = "bani.out";

		
		private int N;
		private int type;
		private static final long MOD = 1000000007;

		/**
		 * Citirea din fisier pentru a obtine datele necesare 
		 */
		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(
								INPUT_FILE)));
				
				type = sc.nextInt();
				N = sc.nextInt();
				
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Afisarea rezultatului.
		 * @param result Numarul de moduri in care se pot aranja bacnotele
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
		 * Metoda care calculeaza puterea unui numar
		 * @return base ^ exponent
		 */
		private static long fastPow(long base, int exp) {
			/* base^0 = 1 */
			if (exp == 0)  {
				return 1;
			}

			long aux = 1;
			while (exp != 1) {
				/* exponentul este par */
				if (exp % 2 == 0) {
					base = (1L * base * base) % MOD;
					exp /= 2;
				/* exponentul este impar */
				} else {
					aux = (1L * aux * base) % MOD;
					exp--;
				}
			}

			return (1L * aux * base) % MOD;
		}

		/**
		 * Calculeaza numarul de moduri in care se pot aranja bacnotele.
		 * @return Rezultatul problemei
		 */
		private long get_solution() {
			long result = 0;
			
			/* 
			 * La tipul 1 se calculeaza numarul de moduri de aranjare
			 * ca 5 * 2^(N-1).
			 */
			if (type == 1) {
				result = 5 * fastPow(2, N - 1);
				
			/* La tipul 2 se foloseste Programare Dinamica */
			} else if (type == 2) {
				
				/* 
				 * Pe primul rand se pastreaza valorile de la pasul i,
				 * iar pe al doilea rand cele de la pasul i+1.
				 * Coloanele reprezinta bacnotele.
				 */
				long[][] dp = new long[2][5];
				
				dp[0][0] = 1;	// Bacnota de 10lei
				dp[0][1] = 1;	// Bacnota de 50lei
				dp[0][2] = 1;	// Bacnota de 100lei
				dp[0][3] = 1;	// Bacnota de 200lei
				dp[0][4] = 1;	// Bacnota de 500lei
				
				for (int i = 1; i < N; i++) {
					/* La 10lei se poate ajunge de la 50, 200 sau 500 */
					dp[1][0] = (1L * dp[0][1] + 1L * dp[0][2] + 1L * dp[0][4]) % MOD;
					/* La 50lei se poate ajunge de la 10 sau 200 */
					dp[1][1] = (1L * dp[0][0] + 1L * dp[0][3]) % MOD;
					/* La 100lei se poate ajunge de la 10, 100 sau 200 */
					dp[1][2] = (1L * dp[0][0] + 1L * dp[0][2] + 1L * dp[0][3]) % MOD;
					/* La 200lei se poate ajunge de la 50 sau 500 */
					dp[1][3] = (1L * dp[0][1] + 1L * dp[0][4]) % MOD;
					/* La 500lei se poate ajunge doar de la 200 */
					dp[1][4] = (1L * dp[0][3]) % MOD;
					
					/* 
					 * Copiem valorile de pe al 2lea rand pe primul pentru 
					 * putea calcula corect la pasul urmator.
					 */
					dp[0] = Arrays.copyOf(dp[1], dp[1].length);
				}
				
				/* Calculam suma pentru a obtine numarul de moduri de aranjare */
				for (long billResult : dp[1]) {
					result += billResult;
				}
			}
			
			return result;
		}

		/**
		 * Rezolva Task-ul
		 */
		public void solve() {
			readInput();
			writeOutput(get_solution());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
