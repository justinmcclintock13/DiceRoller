package main;

import java.util.Scanner;
import jmcclintock_diceBox_javaBadge.DiceBox;

public class Main
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		String userInput = "";
		DiceBox usersDice = new DiceBox();
		
		System.out.print("Please enter a dice roll, or 'exit' to quit: ");
		
		do
		{
			userInput = input.nextLine();
			rollTheDice(userInput, usersDice);
		}while(!userInput.equals("exit") && !userInput.equals("Exit"));
		
		System.out.println("Have a good day!");
	}
	
	private static void rollTheDice(String input, DiceBox usersDice)
	{
		if(!input.equals("exit") && !input.equals("Exit"))
		{
			usersDice.startEquation(input);
			if(usersDice.validOperation)
				usersDice.printTotal();
		}
	}
}
