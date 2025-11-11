import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Abstract base class for products
abstract class Product
{
    private final String brand;
    private final String model;
    private final double price;
    private int quantity;

    public Product(String brand, String model, double price, int quantity)
    {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBrand()
    {
        return brand;
    }

    public String getModel()
    {
        return model;
    }

    public double getPrice()
    {
        return price;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    // Abstract method to display product information
    public abstract void displayProductInfo();
}

// Laptop class extends Product and adds specific attributes
class Laptop extends Product
{
    private final String processor;
    private final int ram;
    private final int storage;

    public Laptop(String brand, String model, String processor, int ram, int storage, double price, int quantity)
    {
        super(brand, model, price, quantity);
        this.processor = processor;
        this.ram = ram;
        this.storage = storage;
    }

    public String getProcessor()
    {
        return processor;
    }

    public int getRam()
    {
        return ram;
    }

    public int getStorage()
    {
        return storage;
    }

    @Override
    public void displayProductInfo()
    {
        System.out.println("Brand: " + getBrand());
        System.out.println("Model: " + getModel());
        System.out.println("Processor: " + processor);
        System.out.println("RAM: " + ram + "GB");
        System.out.println("Storage: " + storage + "GB");
        System.out.println("Price: Rs. " + getPrice());
    }
}


// Laptop management system class
public class Laptop_Management_System
{
    private final ArrayList<Laptop> laptops = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    // Main method
    public static void main(String[] args)
    {
        Laptop_Management_System system = new Laptop_Management_System();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do
        {
            System.out.println("\nLaptop Management System Menu:");
            System.out.println("1. Add Laptop");
            System.out.println("2. Search Laptop");
            System.out.println("3. Remove Laptop");
            System.out.println("4. View All Laptops");
            System.out.println("5. Sort Laptops by Price");
            System.out.println("6. Calculate Average Price of Laptops");
            System.out.println("7. Update Laptop Availability Status");
            System.out.println("8. Count Laptops by Brand");
            System.out.println("9. Generate Summary Report");
            System.out.println("10. Export Data to CSV");
            System.out.println("11. Import Data from CSV");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice)
            {
                case 1:
                    system.addLaptop();
                    break;
                case 2:
                    system.searchLaptop();
                    break;
                case 3:
                    system.removeLaptop();
                    break;
                case 4:
                    system.viewAllLaptops();
                    break;
                case 5:
                    system.sortLaptopsByPrice();
                    break;
                case 6:
                    system.calculateAveragePrice();
                    break;
                case 7:
                    system.updateAvailibilityStatus();
                    break;
                case 8:
                    system.countLaptopsByBrand();
                    break;
                case 9:
                    system.generateReport();
                    break;
                case 10:
                    System.out.print("Enter filename to export to CSV:  ");
                    String exportFile = scanner.nextLine();
                    system.exportToCSV(exportFile);
                    break;
                case 11:
                    system.importFromCSV();
                    System.out.println("Data Imported Successfully !!");
                    break;
                case 12:
                    System.out.println("Thank you for visiting our Store !!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 12);
        scanner.close();
    }

    // Method to add a laptop to the system
    public void addLaptop()
    {
        System.out.print("Enter laptop brand:   ");
        String brand = scanner.nextLine();

        System.out.print("Enter laptop model:   ");
        String model = scanner.nextLine();

        System.out.print("Enter laptop processor:   ");
        String processor = scanner.nextLine();

        System.out.print("Enter laptop RAM (in GB):  ");
        int ram = scanner.nextInt();

        System.out.print("Enter laptop storage (in GB):  ");
        int storage = scanner.nextInt();

        System.out.print("Enter laptop price:   ");
        double price = scanner.nextDouble();

        System.out.print("Enter number of laptops in stock:  ");
        int qty = scanner.nextInt();
        scanner.nextLine();

        Laptop laptop = new Laptop(brand, model, processor, ram, storage, price, qty);
        laptops.add(laptop);

        System.out.println("Laptop added successfully!");
    }

    // Method to display all laptops
    public void viewAllLaptops()
    {
        if (laptops.isEmpty())
        {
            System.out.println("No laptops available.");
            return;
        }

        for (int i = 0; i < laptops.size(); i++)
        {
            System.out.println("\nLaptop " + (i + 1));
            laptops.get(i).displayProductInfo();
        }
    }

    // Method to remove a laptop
    public void removeLaptop()
    {
        System.out.print("Enter the model of the laptop to remove:  ");
        String modelToRemove = scanner.nextLine();

        for (int i = 0; i < laptops.size(); i++)
        {
            if (laptops.get(i).getModel().equalsIgnoreCase(modelToRemove))
            {
                laptops.remove(i);
                System.out.println("Laptop removed successfully.");
                return;
            }
        }
        System.out.println("Laptop not found.");
    }

    // Method to search for a laptop
    //brand, model, processor, ram, storage, price

    public void searchLaptop()
    {
        System.out.println("""
                Categories:
                    1. Brand
                    2. Model
                    3. Processor
                    4. RAM
                    5. Storage
                    6. Price
                What parameters do you want to search?
                Enter serial numbers (e.g., 1 2 4):
                """);

        String[] inputChoices = scanner.nextLine().split(" ");
        int p1 = 0, p2 = 0;
        String brand = null, model = null, processor = null;
        int ram = 0, storage = 0;
        double priceLow = 0.0, priceHigh = 0.0;

        // Reading search criteria based on user input
        for (String choice : inputChoices)
        {
            int option = Integer.parseInt(choice.trim());
            switch (option)
            {
                case 1:
                    System.out.print("Enter Brand name: ");
                    brand = scanner.nextLine().trim();
                    break;
                case 2:
                    System.out.print("Enter Model name: ");
                    model = scanner.nextLine().trim();
                    break;
                case 3:
                    System.out.print("Enter Processor name: ");
                    processor = scanner.nextLine().trim();
                    break;
                case 4:
                    System.out.print("Enter RAM size (in GB): ");
                    ram = scanner.nextInt();
                    scanner.nextLine();
                    break;
                case 5:
                    System.out.print("Enter storage size (in GB): ");
                    storage = scanner.nextInt();
                    scanner.nextLine();
                    break;
                case 6:
                    System.out.print("Enter price range (min and max): ");
                    priceLow = scanner.nextDouble();
                    priceHigh = scanner.nextDouble();
                    scanner.nextLine();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        // Filter laptops based on user input
        String finalBrand = brand;
        String finalModel = model;
        String finalProcessor = processor;
        int finalRam = ram;
        double finalPriceLow = priceLow;
        int finalStorage = storage;
        double finalPriceHigh = priceHigh;
        List<Laptop> filteredLaptops = laptops.stream()
                .filter(lp -> (finalBrand == null || lp.getBrand().equalsIgnoreCase(finalBrand)) &&
                        (finalModel == null || lp.getModel().equalsIgnoreCase(finalModel)) &&
                        (finalProcessor == null || lp.getProcessor().equalsIgnoreCase(finalProcessor)) &&
                        (finalRam == 0 || lp.getRam() == finalRam) &&
                        (finalStorage == 0 || lp.getStorage() == finalStorage) &&
                        (finalPriceLow == 0.0 || lp.getPrice() >= finalPriceLow) &&
                        (finalPriceHigh == 0.0 || lp.getPrice() <= finalPriceHigh))
                .collect(Collectors.toList());

        // Display search results
        if (filteredLaptops.isEmpty())
        {
            System.out.println("No laptops found matching the search criteria.");
        }
        else
        {
            System.out.println("Search Results:");
            for (Laptop l : filteredLaptops)
            {
                l.displayProductInfo();
                System.out.println();
            }
        }
    }

    //  Update Laptop Availability Status
    public void updateAvailibilityStatus()
    {
        System.out.print("Enter the model of the laptop to update: ");
        String model = scanner.nextLine().trim();

        for (Laptop laptop : laptops)
        {
            if (laptop.getModel().equalsIgnoreCase(model))
            {
                System.out.print("Enter new quantity: ");
                int newQuantity = scanner.nextInt();
                scanner.nextLine(); // Clear buffer

                // Assume a setter for quantity in the Laptop class (you'll need to add one)
                laptop.setQuantity(newQuantity);
                System.out.println("Availability status updated successfully!");
                return;
            }
        }
        System.out.println("Laptop model not found.");
    }
    // Generate Summary Report

    // Generate Summary Report
    public void generateReport()
    {
        System.out.println("\n===== Laptop Summary Report =====");

        // Total Laptops in Inventory
        System.out.println("Total Laptops in Inventory: " + laptops.size());

        // Unique Brands Available
        long uniqueBrands = laptops.stream().map(Laptop::getBrand).distinct().count();
        System.out.println("Number of Unique Brands Available: " + uniqueBrands);

        // Total Stock Value
        double totalStockValue = laptops.stream().mapToDouble(l -> l.getPrice() * l.getQuantity()).sum();
        System.out.printf("Total Value of Stock: Rs. %.2f%n", totalStockValue);

        // Average RAM and Storage
        double avgRam = laptops.stream().mapToInt(Laptop::getRam).average().orElse(0.0);
        double avgStorage = laptops.stream().mapToInt(Laptop::getStorage).average().orElse(0.0);
        System.out.printf("Average RAM: %.2f GB%n", avgRam);
        System.out.printf("Average Storage: %.2f GB%n", avgStorage);

        // Average Price
        double avgPrice = laptops.stream().mapToDouble(Laptop::getPrice).average().orElse(0.0);
        System.out.printf("Average Price of Laptops: Rs. %.2f%n", avgPrice);

        // Median Price
        List<Double> sortedPrices = laptops.stream()
                .map(Laptop::getPrice)
                .sorted()
                .collect(Collectors.toList());
        double medianPrice = sortedPrices.size() % 2 == 0
                ? (sortedPrices.get(sortedPrices.size() / 2 - 1) + sortedPrices.get(sortedPrices.size() / 2)) / 2
                : sortedPrices.get(sortedPrices.size() / 2);
        System.out.printf("Median Price of Laptops: Rs. %.2f%n", medianPrice);

        // Highest and Lowest Price Laptops
        Laptop highestPricedLaptop = laptops.stream().max(Comparator.comparingDouble(Laptop::getPrice)).orElse(null);
        Laptop lowestPricedLaptop = laptops.stream().min(Comparator.comparingDouble(Laptop::getPrice)).orElse(null);

        if (highestPricedLaptop != null)
        {
            System.out.println("\nHighest Priced Laptop: ");
            highestPricedLaptop.displayProductInfo();
        }
        if (lowestPricedLaptop != null)
        {
            System.out.println("\nLowest Priced Laptop: ");
            lowestPricedLaptop.displayProductInfo();
        }

        // Most Common Model (if applicable)
        laptops.stream()
                .collect(Collectors.groupingBy(Laptop::getModel, Collectors.counting()))
                .entrySet().stream()
                .max(Comparator.comparingLong(e -> e.getValue()))
                .ifPresent(entry -> System.out.println(
                        "\nMost Common Model in Stock: " + entry.getKey() + " (Quantity: " + entry.getValue() + ")"));
    }


    //Count Laptops By Brand

    public void countLaptopsByBrand()
    {
        System.out.print("Enter brand name: ");
        String brand = scanner.nextLine().trim();
        int num = (int) laptops.stream()
                .filter(l -> l.getBrand().equalsIgnoreCase(brand)).distinct().count();

        System.out.println("Number of Laptop of brand " + brand + " are: " + num);
    }

    // Method to sort and display laptops by price
    public void sortLaptopsByPrice()
    {
        if (laptops.isEmpty())
        {
            System.out.println("No laptops available.");
            return;
        }

        laptops.sort(Comparator.comparingDouble(Laptop::getPrice));
        System.out.println("Laptops sorted by price:");
        viewAllLaptops();
    }

    // Method to calculate the average price of laptops
    public void calculateAveragePrice()
    {
        if (laptops.isEmpty())
        {
            System.out.println("No laptops available.");
            return;
        }

        double total = laptops.stream().mapToDouble(Laptop::getPrice).sum();
        double average = total / laptops.size();
        System.out.printf("The average price of laptops is: Rs. %.2f%n", average);
    }

    //Method to import data from CSV File
    public ArrayList<Laptop> importFromCSV()
    {
        String filepath = "/home/anushka/anu/programming/java/src/laptop_management_sys/Laptops.csv";
        String splitCsvBy = ",";

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
            String line = "";
            line = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null)
            {

                String[] splitArray = line.split(splitCsvBy);

                Laptop laptop = new Laptop(splitArray[0], splitArray[1], splitArray[2], Integer.parseInt(splitArray[3]),
                        Integer.parseInt(splitArray[4]), Double.parseDouble(splitArray[5]),
                        Integer.parseInt(splitArray[6]));
                laptops.add(laptop);
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("The file was not found.");
        } catch (IOException e)
        {
            System.out.println("IOE exception");
        } catch (NumberFormatException ignored)
        {
        }

        return laptops;
    }

    // Method to export data to a CSV file
    public void exportToCSV(String filename)
    {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filename)))
        {
            String[] header = {"Brand", "Model", "Processor", "RAM", "Storage", "Price", "Quantity"};
            writer.writeNext(header);

            for (Laptop laptop : laptops)
            {
                String[] data = {
                        laptop.getBrand(),
                        laptop.getModel(),
                        laptop.getProcessor(),
                        String.valueOf(laptop.getRam()),
                        String.valueOf(laptop.getStorage()),
                        String.valueOf(laptop.getPrice()),
                        String.valueOf(laptop.getQuantity()) // Quantity field added
                };
                writer.writeNext(data);
            }
            System.out.println("Data exported to " + filename + " successfully.");
        } catch (IOException e)
        {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }


}
