import java.awt.Point;
import java.util.Scanner;

public class Main {
	private static char[][] table;
	private static Point[] positions;

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			
			System.out.print("Informe a mensagem a ser criptografada: ");
			String message = sc.nextLine();

			System.out.print("Informe a chave: ");
			String key = sc.nextLine();

			table = new char[5][5];
			positions = new Point[26];

			// SUBSTITUINDO "J" POR "I" NA TABELA
			String s = String.format(key + alphabet).toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");

			// CRIACAO DA TABELA
			int len = s.length();
			for (int i = 0, j = 0; i < len; i++) {
				char c = s.charAt(i);
				if (positions[c - 'A'] == null) {
					table[j / 5][j % 5] = c;
					positions[c - 'A'] = new Point(j % 5, j / 5);
					j++;
				}
			}
			
			// EXIBE A TABELA
			System.out.println("\nQuadrado Playfair:");
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					System.out.print(table[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println();
			
			// SUBSTITUINDO "J" POR "I" NA CIFRA
			message = message.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
			
			// CRIPTOGRAFIA DA CIFRA
			StringBuilder result = new StringBuilder(message);
			int index = 1;

			for (int i = 0; i < result.length(); i += 2) {

				if (i == result.length() - 1)
					result.append(result.length() % 2 == 1 ? 'X' : "");

				else if (result.charAt(i) == result.charAt(i + 1))
					result.insert(i + 1, 'X');
			}
			
			int length = result.length();
			
			for (int i = 0; i < length; i += 2) {
				char a = result.charAt(i);
				char b = result.charAt(i + 1);

				int row1 = positions[a - 'A'].y;
				int row2 = positions[b - 'A'].y;
				int col1 = positions[a - 'A'].x;
				int col2 = positions[b - 'A'].x;

				if (row1 == row2) {
					col1 = (col1 + index) % 5;
					col2 = (col2 + index) % 5;

				} else if (col1 == col2) {
					row1 = (row1 + index) % 5;
					row2 = (row2 + index) % 5;

				} else {
					int tmp = col1;
					col1 = col2;
					col2 = tmp;
				}

				result.setCharAt(i, table[row1][col1]);
				result.setCharAt(i + 1, table[row2][col2]);
			}

			// EXIBE A CIFRA
			System.out.printf("Cifra de Playfair: %s\n", result.toString());
		}
	}
}