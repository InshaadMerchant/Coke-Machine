/*
 * Inshaad Merchant 1001861293
 */
package code4_1001861293;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Code4_1001861293
{

    public static int CokeMenu(String machineName)
    {
        Scanner in = new Scanner(System.in);
        System.out.printf("%s\n", machineName);
        System.out.println("Please choose from the following options:");
        System.out.println("0. Walk away");
        System.out.println("1. Buy A Coke");
        System.out.println("2. Restock Machine");
        System.out.println("3. Add Change");
        System.out.println("4. Display Machine Info");
        System.out.println("5. Update Machine Name:");
        System.out.println("6. Update Coke Price:");
        System.out.printf("Enter your menu choice:");
        int Choice;
        do
        {
            try
            {
                Choice = in.nextInt();
            }
            catch (Exception e)
            {
                Choice = -1;
                in.nextLine();
            }

            if (Choice < 0 || Choice > 6)
            {
                System.out.printf("%s\n", machineName);
                System.out.println("Please choose from the following options:");
                System.out.println("0. Walk away");
                System.out.println("1. Buy A Coke");
                System.out.println("2. Restock Machine");
                System.out.println("3. Add Change");
                System.out.println("4. Display Machine Info");
                System.out.println("5. Update Machine Name:");
                System.out.println("6. Update Coke Price:");
                System.out.printf("Enter your menu choice:");
                Choice = in.nextInt();
            }
        }
        while (Choice < 0 || Choice > 6);
        return Choice;
    }

    public static String displayMoney(int convertamount)
    {
        String dollars = String.valueOf(convertamount / 100);
        String cents = String.valueOf(convertamount % 100);
        String strOut = (Integer.parseInt(cents) < 10) ? "$" + dollars + "." + "0" + cents : "$" + dollars + "." + cents;
        return strOut;
    }

    public static void ReadFile(String FileName, ArrayList<CokeMachine> SetOfCokeMachines)
    {
        File FH = new File(FileName);
        Scanner inFileRead = null;
        try
        {
            inFileRead = new Scanner(FH);
        }
        catch (Exception e)
        {
            System.out.println("File cannot be opened.");
            System.exit(0);
        }
        String arr[] = new String[4];
        while (inFileRead.hasNextLine())
        {
            arr = inFileRead.nextLine().split("[|]");
            SetOfCokeMachines.add(new CokeMachine(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3])));
        }
        inFileRead.close();
    }

    public static int MachineMenu(ArrayList<CokeMachine> SetOfCokeMachines)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("0. Exit");
        for (int i = 0; i < SetOfCokeMachines.size(); i++)
        {
            System.out.printf("%d. %s\n", i + 1, SetOfCokeMachines.get(i).getMachineName());
        }
        System.out.printf("%d. Add New Machine\n", SetOfCokeMachines.size() + 1);
        System.out.printf("Enter your menu choice:");
        int Choice;
        do
        {
            try
            {
                Choice = in.nextInt();
            }
            catch (Exception e)
            {
                Choice = -1;
                in.nextLine();
            }
            if (Choice < 0 || Choice > SetOfCokeMachines.size() + 1)
            {
                System.out.println("0. Exit");
                for (int i = 0; i < SetOfCokeMachines.size(); i++)
                {
                    System.out.printf("%d. %s\n", i + 1, SetOfCokeMachines.get(i).getMachineName());
                }
                System.out.printf("%d. Add New Machine\n", SetOfCokeMachines.size() + 1);
                System.out.print("Invalid Input! Please enter your menu choice again:");
                Choice = in.nextInt();
            }
        }
        while (Choice < 0 || Choice > SetOfCokeMachines.size() + 1);
        return Choice;
    }

    public static void WriteToFile(String OutFileName, ArrayList<CokeMachine> SetOfCokeMachines) throws FileNotFoundException
    {
        PrintWriter out = null;
        try
        {
            out = new PrintWriter(OutFileName);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Unable to write output file.");
            System.out.printf("%s\n", e.getMessage());
            throw (e);
        }
        for (CokeMachine it : SetOfCokeMachines)
        {
            out.printf("%s|%d|%d|%d\n", it.getMachineName(), it.getCokePrice(), it.getChangeLevel(), it.getInventoryLevel());
        }
        out.close();
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner in = new Scanner(System.in);
        CokeMachine MyCokeMachine = new CokeMachine("CSE 1325 CokeMachine", 50, 500, 100);
        int inventory = MyCokeMachine.getInventoryLevel();
        int change = MyCokeMachine.getChangeLevel();
        String amount;
        int quantity;
        String inFile = " ";
        String outFile = " ";
        if (args.length != 2)
        {
            System.out.println("Missing command line parameters - - Usage : INPUTFILENAME= OUTPUTFILENAME=");
            System.exit(0);
        }
        else
        {
            inFile = args[0].substring(args[0].indexOf('=') + 1);
            outFile = args[1].substring(args[1].indexOf('=') + 1);
        }
        ArrayList<CokeMachine> SetOfCokeMachines = new ArrayList<>();
        ReadFile(inFile, SetOfCokeMachines);
        int machineChoice = MachineMenu(SetOfCokeMachines);
        while (machineChoice != 0)
        {
            if (machineChoice == (SetOfCokeMachines.size() + 1))
            {
                SetOfCokeMachines.add(new CokeMachine());
            }
            else
            {
                MyCokeMachine = SetOfCokeMachines.get(machineChoice - 1);
                int Choice = CokeMenu(MyCokeMachine.getMachineName());
                while (Choice != 0)
                {
                    switch (Choice)
                    {
                        case 1:
                            amount = displayMoney(MyCokeMachine.getCokePrice());
                            System.out.printf("A coke costs %s\n", amount);
                            System.out.print("Enter your money:");
                            int money = in.nextInt();
                            if (inventory == 0)
                            {
                                System.out.print("Unable to sell a coke- call 1800WEDONTCARE to register a complaint... returning your payment\n");
                            }
                            else if (MyCokeMachine.getChangeLevel() == MyCokeMachine.getMaxChangeCapacity())
                            {
                                System.out.print("Unable to sell a coke- call 1800WEDONTCARE to register a complaint... returning your payment\n");
                            }
                            else
                            {
                                CokeMachine.ACTION action = MyCokeMachine.buyACoke(money);
                                switch (action)
                                {
                                    case EXACTPAYMENT:
                                        System.out.printf("Thank you for exact payment\nhere's your coke\n");
                                        break;
                                    case DISPENSECHANGE:
                                        int changegiven = money - (MyCokeMachine.getCokePrice());
                                        amount = displayMoney(changegiven);
                                        System.out.printf("Here is your coke and your change is %s\n", amount);
                                        break;
                                    case INSUFFICIENTCHANGE:
                                        System.out.println("Unable to give change at this time... returning your payment");
                                        break;
                                    case INSUFFICIENTFUNDS:
                                        System.out.println("Insufficient payment... returning your payment");
                                        break;
                                    default:
                                        System.out.printf("Something unknown happened.\n");
                                }

                            }
                            break;

                        case 2:
                            System.out.println("What is your restocking quantity: ");
                            quantity = in.nextInt();
                            in.nextLine();
                            if (MyCokeMachine.incrementInventoryLevel(quantity) == true)
                            {
                                inventory = inventory + quantity;
                                System.out.println("Your machine has been restocked");
                                System.out.printf("Your inventory level is: %d\n", MyCokeMachine.getInventoryLevel());
                            }
                            else
                            {
                                System.out.println("You have exceeded your machine's max capacity - no inventory was added.");
                                System.out.printf("Your inventory level is: %d\n", MyCokeMachine.getInventoryLevel());
                            }
                            break;
                        case 3:
                            System.out.println("How much change do you want to add: ");
                            quantity = in.nextInt();
                            in.nextLine();
                            if (MyCokeMachine.incrementChangeLevel(quantity) == true)
                            {
                                change = change + quantity;
                                String maxChangeCapacity = displayMoney(MyCokeMachine.getMaxChangeCapacity());
                                amount = displayMoney(MyCokeMachine.getChangeLevel());
                                System.out.println("Your change level has been updated");
                                System.out.printf("Your change level is %s with a max capacity of %s\n", amount, maxChangeCapacity);
                            }
                            else
                            {
                                String maxChangeCapacity = displayMoney(MyCokeMachine.getMaxChangeCapacity());
                                amount = displayMoney(MyCokeMachine.getChangeLevel());
                                System.out.println("You exceeded your machine's max change capacity- no change added.");
                                System.out.printf("Your change level is %s with a max capacity of %s\n", amount, maxChangeCapacity);
                            }
                            break;
                        case 4:
                            System.out.println(MyCokeMachine);
                            break;
                        case 5:
                            System.out.println("Enter a new machine name: ");
                            String newMachineName = in.nextLine();
                            MyCokeMachine.setMachineName(newMachineName);
                            System.out.println("Machine Name has been updated");
                            break;
                        case 6:
                            System.out.println("Enter a new coke price:");
                            int newCokePrice = in.nextInt();
                            in.nextLine();
                            MyCokeMachine.setCokePrice(newCokePrice);
                            System.out.println("Coke Price has been updated");
                            break;
                        default:
                            System.out.println("Something went wrong");
                    }
                    Choice = CokeMenu(MyCokeMachine.getMachineName());
                }

            }
            machineChoice = MachineMenu(SetOfCokeMachines);

        }
        if (MyCokeMachine.getNumberOfCokesSold() >= 1)
        {
            System.out.println("Bye - enjoy your Coke");
        }
        else
        {
            System.out.println("Are you sure you aren't really THIRSTY and need a Coke?");
        }
        WriteToFile(outFile, SetOfCokeMachines);
    }

}
