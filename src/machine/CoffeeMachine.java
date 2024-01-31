package machine;

public class CoffeeMachine {
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;
    private State currentState;

    public CoffeeMachine(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
        this.currentState = State.IDLE;
        setMainState();
    }

    private void setMainState() {
        this.currentState = State.IDLE;
        System.out.println("Write action (buy, fill, take, remaining, exit):");
    }

    public void processStates(String input) {
        switch (currentState) {
            case IDLE:
                setState(input);
                break;
            case CHOOSING_COFFEE:
                processChoosingCoffeeState(input);
                setMainState();
                break;
            case FILLINGWATER:
                fillWater(input);
                System.out.println("Write how many ml of milk you want to add:");
                currentState = State.FILLINGMILK;
                break;
            case FILLINGMILK:
                fillMilk(input);
                System.out.println("Write how many grams of coffee beans you want to add:");
                currentState = State.FILLINGBEANS;
                break;
            case FILLINGBEANS:
                fillBeans(input);
                System.out.print("Write how many disposable cups of coffee do you want to add:\n> ");
                currentState = State.FILLINGCUPS;
                break;
            case FILLINGCUPS:
                fillCups(input);
                setMainState();
                break;
            default:
                System.out.println("Invalid state");
                break;
        }
    }

    public void setState(String input) {
        switch (input) {
            case "buy":
                currentState = State.CHOOSING_COFFEE;
                System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                break;
            case "fill":
                currentState = State.FILLINGWATER;
                break;
            case "take":
                take();
                setMainState();
                break;
            case "remaining":
                supplies();
                setMainState();
                break;
            case "exit":
                break;
            default:
                System.out.println("Invalid action");
                break;
        }
    }

    private void processChoosingCoffeeState(String input) {
        buy(input);
        currentState = State.IDLE;
    }

    private void buy(String choice) {
        switch (choice) {
            case "1":
                buyCoffee(250, 0, 16, 4);
                break;
            case "2":
                buyCoffee(350, 75, 20, 7);
                break;
            case "3":
                buyCoffee(200, 100, 12, 6);
                break;
            case "back":
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    private void buyCoffee(int water, int milk, int beans, int money) {
        if (canMake(water, milk, beans)) {
            this.water -= water;
            this.milk -= milk;
            this.beans -= beans;
            this.money += money;
            this.cups--;
            System.out.println("I have enough resources, making you a coffee!");
        } else {
            if (this.water < water) {
                System.out.println("Sorry, not enough water!");
            }
            if (this.milk < milk) {
                System.out.println("Sorry, not enough milk!");
            }
            if (this.beans < beans) {
                System.out.println("Sorry, not enough coffee beans!");
            }
            if (this.cups < 1) {
                System.out.println("Sorry, not enough disposable cups!");
            }
        }
    }

    private boolean canMake(int water, int milk, int beans) {
        return this.water >= water
                && this.milk >= milk
                && this.beans >= beans
                && this.cups >= 1;
    }

    private void fillWater(String input) {
        int waterToAdd = parseInput(input);
        water += waterToAdd;
    }

    private void fillMilk(String input) {
        int milkToAdd = parseInput(input);
        milk += milkToAdd;
    }

    private void fillBeans(String input) {
        int coffeeToAdd = parseInput(input);
        beans += coffeeToAdd;
    }

    private void fillCups(String input) {
        int cupsToAdd = parseInput(input);
        cups += cupsToAdd;
    }

    private void take() {
        System.out.printf("I gave you $%d%n", this.money);
        this.money = 0;
    }

    private void supplies() {
        System.out.println("The coffee machine has:");
        System.out.printf("%d ml of water%n", this.water);
        System.out.printf("%d ml of milk%n", this.milk);
        System.out.printf("%d g of coffee beans%n", this.beans);
        System.out.printf("%d disposable cups%n", this.cups);
        System.out.printf("$%d of money%n", this.money);
    }

    private int parseInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return 0;
        }
    }
}