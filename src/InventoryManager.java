import java.io.*;
import java.util.*;

public class InventoryManager {

    private static final String INVENTORY_FILE = "./inventory.txt";

    public static void main(String[] args) {
        // Entry point for the program
        ensureFileExists();
        addItem("Apple", 1);
       addItem("banana", 5);
       addItem("orange", 8);

        updateItem("Apple", 10);
     //   updateItem("Orange", 3);

        readInventory();
    }

    private static void ensureFileExists() {
        File file = new File(INVENTORY_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Inventory file created.");
            } catch (IOException e) {
                System.out.println("Error creating inventory file: " + e.getMessage());
            }
        }
    }

    public static void readInventory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            System.out.println("Current Inventory:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void addItem(String itemName, int itemCount) {
        List<String> inventory = new ArrayList<>();
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(itemName)) {
                    found = true;
                    inventory.add(itemName + "," + (Integer.parseInt(parts[1]) ));
                } else {
                    inventory.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (!found) {
            inventory.add(itemName + "," + itemCount);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (String item : inventory) {
                writer.write(item);
                writer.newLine();
            }
            System.out.println("Item added/updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void updateItem(String itemName, int itemCount) {
        List<String> inventory = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(itemName)) {
                    found = true;
                    inventory.add(itemName + "," + itemCount);
                } else {
                    inventory.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (!found) {
            System.out.println("Item not found. Adding as new item.");
            inventory.add(itemName + "," + itemCount);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE))) {
            for (String item : inventory) {
                writer.write(item);
                writer.newLine();
            }
            System.out.println("Item updated successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
