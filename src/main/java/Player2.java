import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Player2 {
    //STEVE
    private static final int port = 4000;

    public static void main(String[] args) throws IOException {
        ArrayList<String> hands = new ArrayList<>(Arrays.asList("rock", "paper", "scissors"));
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port)

            ;
            int numberOfGames = 0;
            int gamesPlayed = 0;
            String selection = "";
            int score = 0;

            boolean scanning = true;
            Player player1;
            Player player2;
            while (scanning) {
                try {
                    player1 = new Player(new Socket("127.0.0.1", 3000));
                    player2 = new Player(new Socket("127.0.0.1", 5000));
                    numberOfGames = Integer.valueOf(player1.read());
                    scanning = false;
                    Player client1 = new Player(serverSocket.accept());
                    Player client2 = new Player(serverSocket.accept());

                    while (gamesPlayed < numberOfGames) {
                        System.out.println("Round " + (gamesPlayed + 1));
                        selection = hands.get(new Random().nextInt(3));
                        client1.write(selection);
                        client2.write(selection);
                        String player1Choice = player1.read();
                        String player2Choice = player2.read();
                        System.out.println("Tony threw " + player1Choice + ", Bruce threw " + player2Choice +  " and I threw " + selection);
                        score += Player.fight(selection, player1Choice, player2Choice);

                        gamesPlayed++;
                    }
                    client1.write(String.valueOf(score));
                    client2.write(String.valueOf(score));
                    System.out.println("My Score: " + score);
                    System.out.println("Tony's Score: " + player1.read());
                    System.out.println("Bruce's Score: " + player2.read());


                } catch (ConnectException e) {
                    System.out.println("Connection failed, trying again");
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

