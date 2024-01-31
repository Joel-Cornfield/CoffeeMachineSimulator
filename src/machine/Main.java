package machine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CoffeeMachine coffeemachine = new CoffeeMachine(400, 540, 120, 9, 550);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }
            coffeemachine.processStates(userInput);
        }
        scanner.close();
    }
}
