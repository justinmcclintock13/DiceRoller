package jmcclintock_diceBox_javaBadge;

import java.util.Random;
import java.util.Vector;


public class DiceBox
{
	private int diceTotal;
	Vector<String> diceList = new Vector<String>();
	Vector<String> operatorList = new Vector<String>();
	public boolean validOperation = true;

	public void startEquation(String diceToRoll)
	{
		validOperation = true;
		
		char[] ch = new char[diceToRoll.length()];
		
		for(int i = 0; i < diceToRoll.length(); i++)
		{
			ch[i] = diceToRoll.charAt(i);
		}
		readEquation(ch);
		
		//System.out.println(diceList);
		//System.out.println(operatorList);
		
		if(validOperation)
		{
			if(!diceList.isEmpty())
				startCalculations();
		}
		else
			invalidEquation();
	}
	
	private void readEquation (char[] diceArray)
	{	
		String newDice = "";
		for(int i = 0; i < diceArray.length; i++)
		{
			if(diceArray[i] == 'd' || diceArray [i] == 'D' || Character.isDigit(diceArray[i]))
			{
				newDice = newDice + diceArray[i];
			}
			else if(diceArray[i] == '+' || diceArray[i] == '-')
			{
				if(!newDice.equals(""))
				{
					diceList.add(newDice);
					newDice = "";
				}
				String newOperator = "";
				newOperator = newOperator + diceArray[i];
				operatorList.add(newOperator);
			}
			else if(diceArray[i] != ' ')
			{
				//if there is an invalid character then dump the equation
				validOperation = false;
				i = diceArray.length;
				diceList.removeAllElements();
				operatorList.removeAllElements();
			}
			else if(diceArray[i] == ' ' && !newDice.equals(""))
			{
				diceList.add(newDice);
				newDice = "";
			}
		}
		if(!newDice.equals(""))
		{
			diceList.add(newDice);
			newDice = "";
		}
	}

	private void startCalculations()
	{
		//System.out.println("Starting Calulcations");
		String DiceOrMod = diceList.firstElement();
		diceList.remove(0);
		if(!DiceOrMod.equals("+") && !DiceOrMod.equals("-"))
		{
			addingValue(DiceOrMod);
		
			//System.out.println("The current total is " + diceTotal);

			while(!diceList.isEmpty() && !operatorList.isEmpty())
			{
				DiceOrMod = diceList.firstElement();
				diceList.remove(0);
				String plusOrMinus = operatorList.firstElement();
				operatorList.remove(0);
				if(plusOrMinus.equals("+"))
					addingValue(DiceOrMod);
				else
					subtractingValue(DiceOrMod);
				//System.out.println("The current total is " + diceTotal);
			}
		}
		
		if(!diceList.isEmpty() || !operatorList.isEmpty())
			invalidEquation();
	}

	private void addingValue(String diceAmount)
	{
		if(diceAmount.contains("d") || diceAmount.contains("D"))
		{
			int amountOfDice = getDiceAmount(diceAmount);
			int maxDieValue = getMaxDieValue(diceAmount);
			addDice(amountOfDice, maxDieValue);
		}
		else
		{
			int modifer = Integer.parseInt(diceAmount);
			addModifier(modifer);
		}
	}
	
	private void subtractingValue(String diceAmount)
	{
		if(diceAmount.contains("d") || diceAmount.contains("D"))
		{
			int amountOfDice = getDiceAmount(diceAmount);
			int maxDieValue = getMaxDieValue(diceAmount);
			subtractDice(amountOfDice, maxDieValue);
		}
		else
		{
			int modifer = Integer.parseInt(diceAmount);
			subtractModifier(modifer);
		}
	}

	private int getDiceAmount(String dice)
	{
		int numberOfDice = 0;
		String diceNumber = "";
		char[] diceToRoll = new char[dice.length()];
		for(int j = 0; j < dice.length(); j++)
		{
			diceToRoll[j] = dice.charAt(j);
		}
		
		int i = 0;
		while(diceToRoll[i] != 'd' && diceToRoll[i] != 'D')
		{
			diceNumber += diceToRoll[i];
			i++;
		}
		if(!diceNumber.equals(""))
			numberOfDice = Integer.parseInt(diceNumber);
		else
			numberOfDice = 1;
		
		return numberOfDice;
	}
	
	
	private int getMaxDieValue(String dice)
	{
		char[] diceToRoll = new char[dice.length()];
		for(int j = 0; j < dice.length(); j++)
		{
			diceToRoll[j] = dice.charAt(j);
		}
		int i = 0;
		while(diceToRoll[i] != 'd' && diceToRoll[i] != 'D')
			i++;
		i++;
		String maxDie = "";
		while(i < dice.length())
		{
			maxDie = maxDie + diceToRoll[i];
			i++;
		}
		int maximumDieValue = Integer.parseInt(maxDie);
		return maximumDieValue;
	}


	private void addDice(int numDice, int dieMax)
	{
		int currentTotal = rollDice(numDice,dieMax);
		diceTotal = diceTotal + currentTotal;
	}
	

	private void subtractDice(int numDice, int dieMax)
	{
		int currentTotal = rollDice(numDice,dieMax);
		diceTotal = diceTotal - currentTotal;
	}
	

	private void addModifier (int diceMod)
	{
		diceTotal += diceMod;
	}
	

	private void subtractModifier (int diceMod)
	{
		diceTotal -= diceMod;
	}
	
	

	private int rollDice (int numDice, int dieMax)
	{
		int currentTotal = 0;
		Random rand = new Random();
		
		for(int i = 0; i < numDice; i++)
		{
			int dieRoll = rand.nextInt(dieMax) + 1;
			//System.out.println("d"+dieMax+" number "+i+" is a "+dieRoll);
			currentTotal += dieRoll;
			//System.out.println("d" + dieMax + " rolled a " + dieRoll);

		}
		return currentTotal;
	}
	

	private void invalidEquation()
	{
		System.out.println("This is an invalid operation, please try again");
		validOperation = false;
		diceList.removeAllElements();
		operatorList.removeAllElements();
		diceTotal = 0;
	}


	public void printTotal()
	{
		System.out.println("Total: " + diceTotal);
		diceTotal = 0;
	}
	
}
