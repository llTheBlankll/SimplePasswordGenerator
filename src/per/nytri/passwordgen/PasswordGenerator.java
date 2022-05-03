package per.nytri.passwordgen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class PasswordGenerator {

    private String base_directory = "C:\\Users\\Nytri\\AppData\\Local\\Temp";
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new PasswordGenerator().askInput();
    }

    public void askInput() {
        int PASSWORD_LENGTH = 0;
        try {
            System.out.print("Password Length: ");
            PASSWORD_LENGTH = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Password Length. Only numeric are allowed.");
            System.exit(1);
        }

        String password = Generate(PASSWORD_LENGTH);

        try {
            File file = new File(base_directory + "\\" + Generate(8) + ".txt");
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(password);
                fileWriter.flush();
                fileWriter.close();
                System.out.println("File " + file.getName() + " successfully created.");
            } else {
                System.out.println("Cannot create file.");
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public String Generate(int PASSWORD_LENGTH) {
        String loweralpha = "abcdefghijklmnopqrstuvwxyz";
        String upperalpha = "ABCDEFGHIJKLMNOPARSTUVWXYZ";
        String numeric = "01234567890";
        String symbols = "!@#$%^&*";
        StringBuilder builder = new StringBuilder(PASSWORD_LENGTH);

        for (int length = 0; length < PASSWORD_LENGTH; length++) {
            switch (randomInt(1, 4)) {
                case 1:
                    builder.append(loweralpha.charAt(randomInt(0, loweralpha.length() - 1)));
                    break;
                case 2:
                    builder.append(upperalpha.charAt(randomInt(0, upperalpha.length() - 1)));
                    break;
                case 3:
                    builder.append(numeric.charAt(randomInt(0, numeric.length() - 1)));
                    break;
                default:
                    // To create a file, the symbols are not allowed to be first letter at filename or else the Windows will raise an error.
                    // Instead, we will skip symbols at the first position of the filename and then use them later since after the first position was occupied by alphabets we can
                    // create a file without any problems with symbols in the name of the file.
                    if ((builder.length() <= 1)) {
                        switch (randomInt(1, 3)) {
                            case 1:
                                builder.append(loweralpha.charAt(randomInt(0, loweralpha.length() - 1)));
                                break;
                            case 2:
                                builder.append(upperalpha.charAt(randomInt(0, upperalpha.length() - 1)));
                                break;
                            case 3:
                                builder.append(numeric.charAt(randomInt(0, numeric.length() - 1)));
                                break;
                        }
                    } else {
                        builder.append(symbols.charAt(randomInt(0, symbols.length() - 1)));
                        break;
                    }
            }
        }

        return builder.toString();
    }

    public Integer randomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
