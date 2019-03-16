package SearchEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class postingFile {
    static class Struct {
        String field1, field2, flag;

        public Struct(String field1, String field2, String flag) {
            this.field1 = field1;
            this.field2 = field2;
            this.flag = flag;
        }
    }
    public static void printFile(LinkedList<Struct> post, FileWriter f)
    {
        try{
            for(int i=0; i<post.size(); i++)
            {
                f.write(post.get(i).field1+" "+post.get(i).field2+" "+post.get(i).flag);
                if(i==0)
                    f.write(":");
                else if(i!=0 && (i!=post.size()-1))
                    f.write(",");

            }
            f.append("\r\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

        public static ArrayList<LinkedList<Struct>> insert() {
            LinkedList<Struct> post1 = new LinkedList<Struct>();
            ArrayList<LinkedList<Struct>> postingFile = new ArrayList<LinkedList<Struct>>();
            post1.add(new Struct("\0", "\0", "\0"));
            String word = "\0", numOfDoc = "\0", hits = "\0", substring = "\0";
            int countOfDoc = 0;
            try {
                File index3 = new File("index/indexFile3.txt");
                Scanner myReader = new Scanner(index3);
                try {

                    FileWriter PostingFile = new FileWriter("index/postingFile.txt");
                    //PostingFile.append("word")
                for( int i = 0; myReader.hasNextLine(); i++) {
                    String data = myReader.nextLine();
                    Pattern pattern = Pattern.compile("\t\t");
                    Matcher matcher = pattern.matcher(data);
                    if (matcher.find()) {
                        word = data.substring(0, matcher.start());
                        substring = data.substring(matcher.end());
                    }
                    Matcher matcher1 = pattern.matcher(substring);
                    if (matcher1.find()) {
                        numOfDoc = substring.substring(0, matcher1.start());
                        hits = substring.substring(matcher1.end());
                    }
                    if (!(data.equals("#"))) {
                        countOfDoc++;
                        post1.add(new Struct(numOfDoc, hits, "0"));
                    } else {
                        post1.set(0, new Struct(word, Integer.toString(countOfDoc),soundex(word)));
                        postingFile.add(post1);
                        printFile(post1, PostingFile);
                        post1 = new LinkedList<Struct>();
                        post1.add(new Struct("\0", "\0", "\0"));
                        countOfDoc = 0;
                    }
                }
                PostingFile.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            return postingFile;
        }

        public static String soundex(String s) {
            char[] x = s.toUpperCase().toCharArray();
            char firstLetter = x[0];

            // convert letters to numeric code
            for (int i = 0; i < x.length; i++) {
                switch (x[i]) {

                    case 'B':
                    case 'F':
                    case 'P':
                    case 'V':
                        x[i] = '1';
                        break;

                    case 'C':
                    case 'G':
                    case 'J':
                    case 'K':
                    case 'Q':
                    case 'S':
                    case 'X':
                    case 'Z':
                        x[i] = '2';
                        break;

                    case 'D':
                    case 'T':
                        x[i] = '3';
                        break;

                    case 'L':
                        x[i] = '4';
                        break;

                    case 'M':
                    case 'N':
                        x[i] = '5';
                        break;

                    case 'R':
                        x[i] = '6';
                        break;

                    default:
                        x[i] = '0';
                        break;
                }
            }

            // remove duplicates
            String output = "" + firstLetter;
            for (int i = 1; i < x.length; i++)
                if (x[i] != x[i-1] && x[i] != '0')
                    output += x[i];

            // pad with 0's or truncate
            output = output + "0000";
            return output.substring(0, 4);
        }
}

