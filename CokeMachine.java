/*
 * Inshaad Merchant 1001861293
 */
package code4_1001861293;

public class CokeMachine
{

    public enum ACTION
    {
        DISPENSECHANGE, INSUFFICIENTCHANGE, INSUFFICIENTFUNDS, EXACTPAYMENT
    }

    private String machineName;
    private int changeLevel;
    private int maxChangeCapacity = 5000;
    private int inventoryLevel;
    private int maxInventoryCapacity = 100;
    private int CokePrice;
    private int changeDispensed;
    private static int numberOfCokesSold = 0;

    public CokeMachine(String name, int cost, int change, int inventory)
    {
        machineName = name;
        CokePrice = cost;
        changeLevel = change;
        inventoryLevel = inventory;
    }
    
    public CokeMachine()
    {
        machineName = "NewMachine";
        CokePrice = 50;
        changeLevel = 500;
        inventoryLevel = 100;
    }        
    
    public String toString()
    {
        return String.format("Machine Name \t\t\t\t %s\nCurrent Inventory Level \t\t %d\nCurrent Change Level \t\t\t %d\nLast Change Dispensed \t\t\t %d\nInventory Capacity \t\t\t %d\nChange Capacity \t\t\t %d\nCoke Price \t\t\t\t %d\nCokes Sold \t\t\t\t %d\n", machineName, inventoryLevel, changeLevel, changeDispensed, maxInventoryCapacity, maxChangeCapacity, CokePrice, numberOfCokesSold);
    }

    public String getMachineName()
    {
        return machineName;
    }

    public int getChangeLevel()
    {
        return changeLevel;
    }

    public int getMaxChangeCapacity()
    {
        return maxChangeCapacity;
    }

    public int getInventoryLevel()
    {
        return inventoryLevel;
    }

    public int getMaxInventoryLevel()
    {
        return maxInventoryCapacity;
    }

    public int getCokePrice()
    {
        return CokePrice;
    }

    public int getChangeDispensed()
    {
        return changeDispensed;
    }

    public int getNumberOfCokesSold()
    {
        return numberOfCokesSold;
    }
    
    public void setMachineName(String newMachineName)
    {
        machineName = newMachineName;
    }
    
    public void setCokePrice (int newCokePrice)
    {
        CokePrice = newCokePrice;
    }  
    
    public boolean incrementInventoryLevel(int amountToAdd)
    {
        boolean flag = false;
        if ((inventoryLevel + amountToAdd) <= maxInventoryCapacity)
        {
            inventoryLevel = inventoryLevel + amountToAdd;
            flag = true;
        }
        return flag;

    }

    public boolean incrementChangeLevel(int amountToAdd)
    {
        boolean flag = false;
        if ((changeLevel + amountToAdd) <= maxChangeCapacity)
        {
            changeLevel = changeLevel + amountToAdd;
            flag = true;
        }

        return flag;
    }

    public ACTION buyACoke(int payment)
    {
        ACTION action = ACTION.EXACTPAYMENT;
        if (CokePrice == payment)
        {
            inventoryLevel = inventoryLevel - 1;
            changeLevel = changeLevel + payment;
            numberOfCokesSold++;
        }
        if (CokePrice < payment && changeLevel > payment)
        {
            action = ACTION.DISPENSECHANGE;
            inventoryLevel = inventoryLevel - 1;
            changeDispensed = payment - CokePrice;
            changeLevel = changeLevel + payment - CokePrice;
            numberOfCokesSold++;
        }
        if (CokePrice < payment && changeLevel < payment)
        {
            action = ACTION.INSUFFICIENTCHANGE;
        }
        if (CokePrice > payment)
        {
            action = ACTION.INSUFFICIENTFUNDS;
        }

        return action;
    }

}
