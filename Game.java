import java.util.Scanner;
import java.util.ArrayList;

class Board {
    private static ArrayList<Integer> piles = new ArrayList<Integer>();
    static private Scanner scanner;

    static void populate(int minPieces, int maxPieces) {
        scanner = new Scanner(System.in);
        // Initialize piles with a random number of pieces between 10 and 50
        System.out.println("How many piles would you like?");
        int pileCount = scanner.nextInt();
        for (int i =0; i < pileCount; i++){
            piles.add((int) (Math.random() * (maxPieces - minPieces + 1)) + minPieces);
        }
    }

    static void display() {
        System.out.println("The amount in each pile, from top to bottom, is:");
        for (int value : piles){
            System.out.println(value);
        }
    }

    static boolean isValidMove(int pileNumber, int numToRemove) {
        return pileNumber >= 1 && pileNumber <= 3 && numToRemove >= 1 && piles.get(pileNumber - 1) >= numToRemove;
    }

    static void applyMove(int pileNumber, int numToRemove) {
        int current = piles.get(pileNumber - 1);
        piles.set(pileNumber -1, current - numToRemove );

    }

    static boolean isGameOver() {
        for (int pile : piles) {
            if (pile > 0) {
                return false;
            }
        }
        return true;
    }
}

class Player {
    private Scanner scanner;

    Player() {
        scanner = new Scanner(System.in);
    }

    int getPlayerMove() {
        System.out.print("Enter the pile number and the number of objects to remove: ");
        int pileNumber = scanner.nextInt();
        int numToRemove = scanner.nextInt();

        return pileNumber * 10 + numToRemove; // Using a single integer to represent both inputs
    }

    boolean wantsToPlayAgain() {
        System.out.print("Do you want to play again? (yes/no): ");
        String response = scanner.next().toLowerCase();
        return response.equals("yes");
    }

    void closeScanner() {
        scanner.close();
    }
}

public class Game {
    private Player[] players;
    private int currentPlayerIndex;
    private int minPieces = 10;
    private int maxPieces = 50;
    private Scanner scanner;

    Game() {
        players = new Player[]{new Player(), new Player()};
        currentPlayerIndex = (int) (Math.random() * 2); // Randomly choose the first player
    }

    void play() {
        do {
            Board.populate(minPieces, maxPieces);

            while (!Board.isGameOver()) {
                Board.display();

                int move = players[currentPlayerIndex].getPlayerMove();

                int pileNumber = move / 10;
                int numToRemove = move % 10;

                if (Board.isValidMove(pileNumber, numToRemove)) {
                    Board.applyMove(pileNumber, numToRemove);
                } else {
                    System.out.println("Invalid move. Try again.");
                }

                switchPlayer();
            }

            // Display the winner
            Board.display();
            if (currentPlayerIndex == 0) {
                System.out.println("\nCongratulations! Player 1 wins!");
            } else {
                System.out.println("\nCongratulations! Player 2 wins!");
            }

        } while (players[0].wantsToPlayAgain() && players[1].wantsToPlayAgain());

        players[0].closeScanner();
        players[1].closeScanner();
    }

    private void switchPlayer() {
        currentPlayerIndex = 1 - currentPlayerIndex; // Switch between players 0 and 1
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Game of Nim!");

        Game nim = new Game();
        nim.play();
    }
}
