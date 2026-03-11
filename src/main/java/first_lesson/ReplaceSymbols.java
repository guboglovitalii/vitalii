package first_lesson;
import java.io.*;

public class ReplaceSymbols {
        public static void main(String[] args) {

int numbersCount = 0;
int latinLettersCount = 0;


            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt"), "UTF-8"));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt"), "UTF-8"))) {


                    int ch;
                    while ((ch = reader.read()) != -1) {
                        char c = (char) ch;

                        if ("0123456789".contains(String.valueOf(c))) {
                            writer.write('#');
                            numbersCount++;
                        }

                        else if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <='z')) {
                            writer.write('*');
                            latinLettersCount++;
                        }

                        else {
                            writer.write(c);
                        }

                        

                        
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                        System.out.println("Цыфры: " + numbersCount);
                        System.out.println("Латинские буквы: " + latinLettersCount);

        }
}