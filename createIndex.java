package SearchEngine;
import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import java.util.regex.*;


public class createIndex {

   static ArrayList<LinkedList<postingFile.Struct>> postingFile=new ArrayList<LinkedList<postingFile.Struct>>();

   public static boolean checkIfExist(Stack<String> fileName,String name) {
       boolean flag = false;
       Iterator<String> stackIterator = fileName.iterator();
       while (stackIterator.hasNext()) {
           String item = stackIterator.next();
           if (item.equals(name)) flag = true;
       }
       return flag;
   }

    public static void create() {

        ArrayList<ArrayList<String>> indexTable1 = new ArrayList<ArrayList<String>>();
        indexTable1.add(new ArrayList<String>());
        indexTable1.add(new ArrayList<String>());
        File Source = new File("source");
        File Storage = new File("storage");
        int lengthSource = Source.listFiles().length;
        int lengthStorage;
        //copy files from source to storage
        try {
            for(int i=0; i<lengthSource ; i++) {
                lengthStorage = Storage.listFiles().length;
                File file = Source.listFiles()[i];
                String fileName = file.getName();
                File newFile = new File("source/" + fileName);
                Scanner myReader = new Scanner(newFile);
                FileWriter copyFile= new FileWriter("storage/" + fileName);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    if(data.equals("")) continue;
                    copyFile.write(data);
                    copyFile.append("\r\n");
                    indexTable1= IndexFile.parsing(data,indexTable1,fileName);
                }
                myReader.close();
                copyFile.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //create index file 1
        try {
            //First index table
            FileWriter IndexFile= new FileWriter("index/indexFile.txt",true);
            for(int i=0 ; i<indexTable1.get(0).size(); i++)
            {
                IndexFile.append(indexTable1.get(0).get(i));
                IndexFile.append("\t\t");
                IndexFile.append(indexTable1.get(1).get(i));
                IndexFile.append("\r\n");
            }
            IndexFile.close();
        }
        catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        int numOfLines = IndexFile.countLines();
        //create index file 2
        String[][] arr = new String[numOfLines][2];
        try {
            File myObj = new File("index/indexFile.txt");
            Scanner myReader = new Scanner(myObj);
            for (int i=0; myReader.hasNextLine(); i++) {
                String data = myReader.nextLine();
                Pattern pattern = Pattern.compile("\t\t");
                Matcher matcher = pattern.matcher(data);
                if (matcher.find()) {
                    arr[i][0] = data.substring(0, matcher.start());
                    arr[i][1] = data.substring(matcher.end());
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        Arrays.sort(arr, new Comparator<String[]>(){
            @Override
            public int compare(String[] first, String[] second){
                // compare the first element
                int comparedTo = first[0].compareTo(second[0]);
                // if the first element is same (result is 0), compare the second element
                if (comparedTo == 0) return first[1].compareTo(second[1]);
                else return comparedTo;
            }
        });
        try {

            FileWriter IndexFile2= new FileWriter("index/indexFile2.txt");

            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                   IndexFile2.write(arr[i][j] + " ");
                }
                IndexFile2.write("\r\n");
            }
            IndexFile2.close();
        }
        catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        IndexFile.frequency(arr);

        postingFile= SearchEngine.postingFile.insert();

        while(Source.listFiles().length>0) {
            File file = Source.listFiles()[Source.listFiles().length-1];
            if(file.delete())
                System.out.println("File deleted successfully");
            else System.out.println("Failed to delete the file");
        }
    }

    public static Stack<String> SearchOr(Stack<String> searchWord) {
        int size=0, totalSize=0;
        int j = 1, x=1;
        Stack<String> fileName = new Stack<String>();
        for(int index=0 ; index<searchWord.size() ; index++) {
            for (int i = 0; i < postingFile.size(); i++) {
                size = Integer.parseInt(postingFile.get(i).get(0).field2);
                if (searchWord.get(index).equals(postingFile.get(i).get(0).field1)) { //find word
                    x = 1;
                    totalSize += size;
                    while (j <= totalSize) {
                        boolean fileExist = checkIfExist(fileName, postingFile.get(i).get(x).field1);
                        if (fileExist) break;
                        if(postingFile.get(i).get(x).flag.equals("1"))
                        {
                            j++;
                            x++;
                            continue;
                        }
                        fileName.add(postingFile.get(i).get(x).field1);

                        j++;
                        x++;
                    }

                    break;
                }
            }
        }
        return fileName;
    }
    public static Stack<String> searchWild(String searchWord, int length) {
        int size=0, totalSize=0;
        int j = 0, x=1;
        Stack<String> fileName = new Stack<String>();
            for (int i = 0; i < postingFile.size(); i++) {
                size = Integer.parseInt(postingFile.get(i).get(0).field2);
                if(postingFile.get(i).get(0).field1.length()<length) continue;
                if (searchWord.equals(postingFile.get(i).get(0).field1.substring(0,length))) { //find word
                    x = 1;
                    totalSize += size;
                    while (j < totalSize) {
                        boolean fileExist = checkIfExist(fileName, postingFile.get(i).get(x).field1);
                        if (fileExist)
                        {
                            j++;
                            continue;
                        }
                        if(postingFile.get(i).get(x).flag.equals("1"))
                        {
                            j++;
                            x++;
                            continue;
                        }
                        fileName.add(postingFile.get(i).get(x).field1);

                        j++;
                        x++;
                    }
                }
            }
        return fileName;
    }



    public static Stack<String> searchAnd(Stack<String> searchWord)
    {
        int size=0, totalSize=0;
        int j = 1, x=1;
        Stack<String> fileName = new Stack<String>();
        for(int index=0 ; index<searchWord.size() ; index++) {
            for (int i = 0; i < postingFile.size(); i++) {
                size = Integer.parseInt(postingFile.get(i).get(0).field2);
                if (searchWord.get(index).equals(postingFile.get(i).get(0).field1)) {
                    x = 1;
                    totalSize += size;
                    while (j <= totalSize) {
                        if(postingFile.get(i).get(x).flag.equals("1"))
                        {
                            j++;
                            x++;
                            continue;
                        }
                        fileName.add(postingFile.get(i).get(x).field1);
                        j++;
                        x++;
                    }

                    break;
                }
            }
        }
        Stack<String> finalFiles= new Stack<String>(){};
        int finalSize=0;
            for (int i = 0; i < fileName.size(); i++) {
                for (int index = i + 1; index < fileName.size(); index++) {
                    if (fileName.get(i).equals(fileName.get(index))) {
                        finalFiles.push(fileName.get(i));
                }
            }
        }
        return finalFiles;
    }
    public static Stack<String> searchAndNot(Stack<String> searchWord)
    {
        int size=0, totalSize=0;
        int j = 1, x=1;
        Stack<String> word1 = new Stack<String>();
        for (int i = 0; i < postingFile.size(); i++) {
            size = Integer.parseInt(postingFile.get(i).get(0).field2);
            if (searchWord.get(0).equals(postingFile.get(i).get(0).field1)) {
                x=1;
                totalSize+=size;
                while (j <= totalSize) {
                    if(postingFile.get(i).get(x).flag.equals("1"))
                    {
                        j++;
                        x++;
                        continue;
                    }
                    word1.add(postingFile.get(i).get(x).field1);
                    j++;
                    x++;
                }

                break;
            }
        }
        size=0;
        totalSize=0;
        x=1;
        j=1;
        Stack<String> word2 = new Stack<String>();
        for (int i = 0; i < postingFile.size(); i++) {
            size = Integer.parseInt(postingFile.get(i).get(0).field2);
            if (searchWord.get(1).equals(postingFile.get(i).get(0).field1)) {
                x=1;
                totalSize+=size;
                while (j <= totalSize) {
                    if(postingFile.get(i).get(x).flag.equals("1"))
                    {
                        j++;
                        x++;
                        continue;
                    }
                    word2.add(postingFile.get(i).get(x).field1);
                    j++;
                    x++;
                }

                break;
            }
        }
            for(int k=0; k<word2.size(); k++)
            {
                for(int p=0; p<word1.size(); p++)
                {
                    word1.remove(word2.get(k));
                }
            }


        return word1;
    }
    public static Stack<String> SearchSoundex(String searchWord) {
        int size=0, totalSize=0;
        int j = 1, x=1;
        Stack<String> fileName = new Stack<String>();
            for (int i = 0; i < postingFile.size(); i++) {
                size = Integer.parseInt(postingFile.get(i).get(0).field2);
                if (searchWord.equals(postingFile.get(i).get(0).flag)) {
                    x = 1;
                    totalSize += size;
                    while (j <= totalSize) {
                        boolean fileExist = checkIfExist(fileName, postingFile.get(i).get(x).field1);
                        if (fileExist) break;
                        if(postingFile.get(i).get(x).flag.equals("1"))
                        {
                            j++;
                            x++;
                            continue;
                        }
                        fileName.add(postingFile.get(i).get(x).field1);

                        j++;
                        x++;
                    }
                }
            }
        return fileName;
    }
    public static void changeFlag(String searchFile) {
        int size=0, totalSize=0;
        int j = 1, x=1;
        Stack<String> fileName = new Stack<String>();
        for (int i = 0; i < postingFile.size(); i++) {
            size = Integer.parseInt(postingFile.get(i).get(0).field2);
            for(int k=1; k<=size ; k++) {
                if (searchFile.equals(postingFile.get(i).get(k).field1)) {
                    postingFile.get(i).get(k).flag="1";
                    }
                }
            }
        try {

            FileWriter newPostingFile= new FileWriter("index/postingFile.txt");
            for(int i=0; i<postingFile.size(); i++)
            {
                SearchEngine.postingFile.printFile(postingFile.get(i),newPostingFile);
            }

            newPostingFile.close();
        }
        catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        }

    public static void changeFlagShow(String searchFile) {
        int size=0, totalSize=0;
        int j = 1, x=1;
        Stack<String> fileName = new Stack<String>();
        for (int i = 0; i < postingFile.size(); i++) {
            size = Integer.parseInt(postingFile.get(i).get(0).field2);
            for(int k=1; k<=size ; k++) {
                if (searchFile.equals(postingFile.get(i).get(k).field1)) {
                    postingFile.get(i).get(k).flag="0";
                }
            }
        }
        try {

            FileWriter newPostingFile= new FileWriter("index/postingFile.txt");
            for(int i=0; i<postingFile.size(); i++)
            {
                SearchEngine.postingFile.printFile(postingFile.get(i),newPostingFile);
            }

            newPostingFile.close();
        }
        catch(IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    }

