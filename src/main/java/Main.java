import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Simple snake in java.");
        System.out.println("The original snake game coded in java.");

        Scanner scanner = new Scanner(System.in);

        String regexOnlyNumbers = "[0-9]+";
        boolean regexMatch = false;
        int boardSize = 3;

        do {
            System.out.println("Chose your board size: (enter a number between 3 and 30, default is 3)");
            String boardSizeUserInput = scanner.nextLine();

            if(boardSizeUserInput.isEmpty()) break;

            regexMatch = boardSizeUserInput.matches(regexOnlyNumbers);
            boardSize = Integer.parseInt(boardSizeUserInput);

        }while(!regexMatch || boardSize < 3 || boardSize > 30);

        System.out.println("The board size is set to " + boardSize);
        SnakeGameController.run(boardSize);
    }
}
