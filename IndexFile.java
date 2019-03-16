package SearchEngine;
import java.util.*;
import java.io.*;


public class IndexFile {
    public static ArrayList<ArrayList<String>> parsing(String data, ArrayList<ArrayList<String>> indexTable1, String docNum) {
        String[] arr = data.toLowerCase().replaceAll("[^a-zA-Z ]", "").replaceAll("\r\n", "").split(" ");
        for (String word : arr) {
            // push(indexTable1,ss,docNum);
            indexTable1.get(0).add(word);
            indexTable1.get(1).add(docNum);

        }
        return indexTable1;
    }

    public static int countLines() {
        byte[] c = new byte[1024];
        int count = 0;
        int readChars = 0;
        boolean empty = true;
        try {
            InputStream is = new BufferedInputStream(new FileInputStream("index/indexFile.txt"));
                while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return (count == 0 && !empty) ? 1 : count;
    }
    public static void frequency(String[][] arr) {

        int count = 0,i;
        String word = arr[0][0];
        String doc = arr[0][1];
        try {
            FileWriter IndexFile3 = new FileWriter("index/indexFile3.txt");
            for (i = 0; i < arr.length; i++) {
                while (i < arr.length - 1 && word.equals(arr[i + 1][0]) && doc.equals(arr[i + 1][1])) {
                    count++;
                    i++;
                }


                IndexFile3.write(arr[i][0] + "\t\t" + arr[i][1] + "\t\t" + (count + 1));
                IndexFile3.write("\r\n");
                if (i < arr.length - 1) {
                    if (!(word.equals(arr[i + 1][0]))) {
                        IndexFile3.write("#\r\n");
                    }
                }
                if(i<arr.length-1) {
                    word = arr[i+1][0];
                    doc = arr[i+1][1];
                }
                count=0;
            }
            IndexFile3.close();
        }
            catch(IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }



        }
    }



