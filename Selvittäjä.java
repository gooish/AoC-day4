import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Selvittäjä
 */
public class Selvittäjä {

    public static void main(String[] args) {
        int strictLines = 0;

        // System.out.println("vittu");
        String[] rivit = (loadfile("data.txt")).split("\n");
        // System.out.println(rivit.length);
        int validLines = 0;
        for (String rivi : rivit) {
            // goodByr(rivi);

            if (rivi.contains("byr:") && rivi.contains("iyr:") && rivi.contains("eyr:") && rivi.contains("hgt:") && rivi.contains("hcl:") && rivi.contains("ecl:") &&rivi.contains("pid:")) {
                validLines++;
                boolean isByr = false;
                boolean isIyr = false;
                boolean isEyr = false;
                boolean isHgt = false;
                boolean isHcl = false;
                boolean isEcl = false;
                boolean isPid = false;

                String[] eyeColours = {"amb", "blu", "gry", "grn", "hzl", "oth", "brn"};

                String[] arrRivi = rivi.split(" ");
                System.out.print(Arrays.toString(arrRivi) + ":   ");
                for (String text : arrRivi) {
                    if (text.contains("byr:")) {
                        isByr = checkBIE(text, 1920, 2002);
                    }
                    else if (text.contains("iyr:")) {
                        isIyr = checkBIE(text, 2010, 2020);
                    }
                    else if (text.contains("eyr:")) {
                        isEyr = checkBIE(text, 2020, 2030);
                    }
                    else if (text.contains("hgt:")) {
                        isHgt = checkHgt(text);
                    }
                    else if (text.contains("hcl:")) {
                        if (text.length() == 11) {
                            String hcl = text.substring(4);
                            System.out.print("hair color is " + hcl + " ");
                            isHcl = (hcl.matches("^#+([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$"));
                        }
                    }
                    else if (text.contains("ecl:")) {
                        for (String color : eyeColours) {
                            if (text.length() == 7 && text.contains(color)) {
                                isEcl = true;
                            }
                        }
                    }
                    else if (text.contains("pid:")) {
                        isPid = (text.length() == 13 && text.substring(4).matches("-?\\d+(\\.\\d+)?"));
                    }
                }
                System.out.printf("byr: %B Iyr: %B Eyr: %B Hgt: %B HCL: %B ECL: %B PID: %B%n", isByr, isIyr, isEyr, isHgt, isHcl, isEcl, isPid);
                if (isByr && isIyr && isEyr && isHgt && isHcl && isEcl && isPid) {
                    System.out.println("line is valid!!\n\n");
                    strictLines++;
                }
                else {
                    System.out.println("Line is not valid\n\n");
                }
            }
        }
        System.out.println("Valid Lines in old format:");
        System.out.println(validLines);
        System.out.println();
        System.out.println("Valid lines in new format:");
        System.out.println(strictLines);
    }

    public static String loadfile(String fname) {
        String out = "";
        try {
            Scanner input = new Scanner(new File(fname));
            while (input.hasNextLine()) {
                String nxtln = input.nextLine();
                if (nxtln == "") {
                    out = out + "\n";
                }
                else {
                    out = out + nxtln + " ";
                }
            }


        } catch (FileNotFoundException e) {
            return null;
        }
        


        return out;
    }

    static boolean checkBIE(String rivi, int min, int max) {
        int numero = Integer.parseInt(rivi.substring(4, 8));
        // System.out.print("BYR number is " + numero);
        if (numero >= min && numero <= max ) {
            return true;
        }
        return false;
    }

    static boolean checkHgt (String rivi) {
        boolean isInch = (rivi.contains("in"));
        boolean isCm = (rivi.contains("cm"));
        if (!isCm && !isInch) {
            return false;
        }
        if (isInch) {
            int inchNmb = Integer.parseInt(rivi.substring(4, rivi.indexOf("in")));
            // System.out.println("amount of inches is " + inchNmb);
            if (inchNmb >= 59 && inchNmb <= 76) {
                return true;
            }
        }
        if (isCm) {
            int cmNmb = Integer.parseInt(rivi.substring(4, rivi.indexOf("cm")));
            // System.out.println("amount of CM is " + cmNmb);
            if (cmNmb >= 150 && cmNmb <= 193) {
                return true;
            }
        }
        return false;
    }

    static boolean isInt (String rivi) {
        try {
            int i = Integer.parseInt(rivi);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}