/*
 * Write a distributed program to simulate a three-person rock/scissors/paper game.
 * Each player randomly chooses one of rock, scissors, or paper. Then the players compare their choices to see who "won."
 * Rock smashes scissors, scissors cut paper, and paper covers rock.
 *       Award a player 2 points if it beats both the others;
 *       award two players 1 point each if they both beat the third;
 *       otherwise award no points. Then the players play another game.
 * Use one process for each player. The players must interact directly with each other. Do not use an additional coordinator process.
 * There should be one input, numGames, the number of games to play. Input as a command line argument or console.
 *
 * Print a trace of the results of each game as the program executes. At the end print the total points won by each player.
 * Turn in output for 10 games and for 30 games
 * */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Player1 {
    //TONY
    private static final int port = 3000;

    public static void main(String[] args) throws IOException {
        ArrayList<String> hands = new ArrayList<>(Arrays.asList("rock", "paper", "scissors"));
        System.out.println("How many games are we playing?");
        Scanner in = new Scanner(System.in);
        int numberOfGames = in.nextInt();
        int gamesPlayed = 0;
        String selection = "";
        int score = 0;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
            Player client1 = new Player(serverSocket.accept());
            Player player1 = new Player(new Socket("127.0.0.1", 4000));
            Player client2 = new Player(serverSocket.accept());
            Player player2 = new Player(new Socket("127.0.0.1", 5000));

            client1.write(String.valueOf(numberOfGames));
            client2.write(String.valueOf(numberOfGames));

            while(gamesPlayed < numberOfGames){
                System.out.println("Round " + (gamesPlayed+1));
                selection = hands.get(new Random().nextInt(3));
                client1.write(selection);
                client2.write(selection);
                String player1Choice = player1.read();
                String player2Choice = player2.read();
                System.out.println("Steve threw " + player1Choice + ", Bruce threw "+ player2Choice + " and I threw " + selection);
                score += Player.fight(selection, player1Choice, player2Choice);

                gamesPlayed++;
            }
            client1.write(String.valueOf(score));
            client2.write(String.valueOf(score));
            System.out.println("My Score: " + score);
            System.out.println("Steve's Score: " + player1.read());
            System.out.println("Bruce's Score: " + player2.read());

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
