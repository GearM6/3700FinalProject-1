import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class Player {
    private Socket socket;
    private OutputStream out;
    private PrintWriter writer;
    private BufferedReader reader;


    public Socket getSocket() {
        return socket;
    }

    public void write(String message){
        this.writer.println(message);
    }

    public boolean isConnected(){
        return socket.isConnected();
    }

    public String read(){
        String message = "";
        try {
            message = this.reader.readLine();
            this.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    public Player(Socket socket) {
        this.socket = socket;
        try {
            InputStream in = socket.getInputStream();
            this.out = socket.getOutputStream();
            this.writer = new PrintWriter(this.out, true);
            this.reader = new BufferedReader(new InputStreamReader(in));
        } catch (IOException e) {
            System.out.println("Unable to get I/O for socket");
        }
    }

    public static int fight(String hand1, String hand2, String hand3){
        switch (hand1){
            case "rock" :
                if(hand2.equals("rock") && hand3.equals("scissors")){
                    return 1;
                }
                else if(hand2.equals("scissors") && hand3.equals("rock")){
                    return 1;
                }
                else if(hand2.equals("scissors") && hand3.equals("scissors")){
                    return 2;
                }
                break;
            case "paper" :
                if(hand2.equals("paper") && hand3.equals("rock")){
                    return 1;
                }
                else if(hand2.equals("rock") && hand3.equals("paper")){
                    return 1;
                }
                else if(hand2.equals("rock") && hand3.equals("rock")){
                    return 2;
                }
                break;
            case "scissors" :
                if(hand2.equals("scissors") && hand3.equals("paper")){
                    return 1;
                }
                else if(hand2.equals("paper") && hand3.equals("scissors")){
                    return 1;
                }
                else if(hand2.equals("paper") && hand3.equals("paper")){
                    return 2;
                }
                break;
        }

        return 0;
    }
}
