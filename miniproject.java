import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class miniproject {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Map<String, Double> expenses = initializeExpenses();

        System.out.println("Personal Finance Calculator");

        double monthlyIncome = 0.0;
        while (true) {
            System.out.print("Enter your monthly income: ");
            try {
                monthlyIncome = input.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value for monthly income.");
                input.next(); 
            }
        }

        int months = 0;
        while (true) {
            System.out.print("Enter the number of months to track expenses: ");
            try {
                months = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer value for the number of months.");
                input.next(); 
            }
        }

        double totalExpenses = 0.0;
        Map<String, Double> monthlyExpenses = new HashMap<>();

        for (int i = 1; i <= months; i++) {
            System.out.println("Month " + i + " expenses:");
            double monthlySpent = 0.0;

            for (Map.Entry<String, Double> entry : expenses.entrySet()) {
                while (true) {
                    System.out.print("Please enter the " + entry.getKey() + " expense: ");
                    try {
                        double expenseAmount = input.nextDouble();
                        monthlySpent += expenseAmount;
                        monthlyExpenses.put(entry.getKey(), expenseAmount);  
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a numeric value for " + entry.getKey() + " expense.");
                        input.next(); 
                    }
                }
            }

            input.nextLine(); 
            while (true) {
                System.out.print("Enter any additional expense name (or press enter to skip): ");
                String additionalExpense = input.nextLine();
                if (!additionalExpense.isEmpty() && isNumeric(additionalExpense)) {
                    System.out.println("Invalid input. Please enter a valid expense name.");
                } else if (!additionalExpense.isEmpty()) {
                    while (true) {
                        try {
                            System.out.print("Enter the amount for " + additionalExpense + ": ");
                            double additionalExpenseAmount = input.nextDouble();
                            monthlySpent += additionalExpenseAmount;
                            monthlyExpenses.put(additionalExpense, additionalExpenseAmount);
                            input.nextLine(); 
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a numeric value for " + additionalExpense + " expense.");
                            input.next(); 
                        }
                    }
                    break;
                } else {
                    break;
                }
            }

            System.out.println("Total spent for month " + i + ": " + monthlySpent + " from " + monthlyIncome);
            if (monthlySpent > monthlyIncome) {
                System.out.println("Total monthly expenses exceed monthly income. Don't spend too much money; you have to save for the future.");
            }
            totalExpenses += monthlySpent;
            double monthlySavings = monthlyIncome - monthlySpent;
            if (monthlySavings > 0) {
                System.out.println("Monthly savings for month " + i + ": " + monthlySavings);
            }
        }

        double averageMonthlySavings = calculateAverageMonthlySavings(monthlyIncome, totalExpenses, months);
        System.out.println("Average monthly savings for " + months + " months: " + averageMonthlySavings);

        double savingsGoal = 0.0;
        while (true) {
            System.out.print("Enter your savings goal: ");
            try {
                savingsGoal = input.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value for the savings goal.");
                input.next();
            }
        }

        int remainingMonthsToGoal = calculateRemainingMonthsToGoal(savingsGoal, averageMonthlySavings, months);
        System.out.println("Remaining months to reach your savings goal: " + remainingMonthsToGoal);

        displayTotalExpensesSummary(monthlyExpenses);
        displayPercentageOfTotalExpenses(monthlyExpenses, totalExpenses);

        input.close();
    }

    public static Map<String, Double> initializeExpenses() {
        Map<String, Double> expenses = new HashMap<>();
        expenses.put("Loan", 0.0);
        expenses.put("Food", 0.0);
        expenses.put("Transportation", 0.0);
        expenses.put("Movie", 0.0);
        return expenses;
    }

    public static double calculateAverageMonthlySavings(double monthlyIncome, double totalExpenses, int months) {
        return (monthlyIncome * months - totalExpenses) / months;
    }

    public static int calculateRemainingMonthsToGoal(double savingsGoal, double averageMonthlySavings, int months) {
        return (int) Math.ceil((savingsGoal - (averageMonthlySavings * months)) / averageMonthlySavings);
    }

    public static void displayTotalExpensesSummary(Map<String, Double> monthlyExpenses) {
        System.out.println("\nTotal expenses summary:");
        for (Map.Entry<String, Double> entry : monthlyExpenses.entrySet()) {
            System.out.println(entry.getKey() + " expenses: " + entry.getValue());
        }
    }

    public static void displayPercentageOfTotalExpenses(Map<String, Double> monthlyExpenses, double totalExpenses) {
        System.out.println("\nPercentage of total expenses for each category:");
        for (Map.Entry<String, Double> entry : monthlyExpenses.entrySet()) {
            double percentage = (entry.getValue() / totalExpenses) * 100;
            System.out.println(entry.getKey() + ": " + String.format("%.2f", percentage) + "%");
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
