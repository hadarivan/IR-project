package SearchEngine;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class userGUI {
    public static JFrame frame;
    public static userGUI window;
    /**
     * Launch the application.
     */
    public static void admin() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    window= new userGUI();
                    window.frame.setVisible(true);
                    //window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public userGUI() {
        initialize();
    }

    public static Stack<String> findFiles(String searchWord,Stack<String> wordsToSearch,Stack<String> filesList,Stack<String> stopList )
    {
        boolean flag=false;
        boolean found = searchWord.contains("or");
        boolean found2= searchWord.contains("and");
        boolean found3= searchWord.contains("and not");
        if(found) {
            Pattern pattern = Pattern.compile(" or ");
            Matcher matcher = pattern.matcher(searchWord);
            if (matcher.find()) {
                wordsToSearch.push(searchWord.substring(0, matcher.start()));
                wordsToSearch.push(searchWord.substring(matcher.end()));
            }
            wordsToSearch.set(0,wordsToSearch.get(0).replace(" ", ""));
            wordsToSearch.set(1,wordsToSearch.get(1).replace(" ", ""));


            for(int i=0; i<stopList.size(); i++)
            {
                for(int j=0 ; j<wordsToSearch.size(); j++)
                {
                    if((wordsToSearch.get(j).substring(0,1).equals("\""))) {
                        wordsToSearch.set(j,wordsToSearch.get(0).replace("\"", ""));
                        flag=true;
                        continue;
                    }
                    if(wordsToSearch.get(j).equals(stopList.get(i)) &&!flag)
                    {
                        wordsToSearch.remove(stopList.get(i));
                    }

                }
            }
            filesList=createIndex.SearchOr(wordsToSearch);
        }
        else if (found3)
        {
            Pattern pattern = Pattern.compile(" and not ");
            Matcher matcher = pattern.matcher(searchWord);
            if (matcher.find()) {
                wordsToSearch.push(searchWord.substring(0, matcher.start()));
                wordsToSearch.push(searchWord.substring(matcher.end()));
            }
            wordsToSearch.set(0,wordsToSearch.get(0).replace(" ", ""));
            wordsToSearch.set(1,wordsToSearch.get(1).replace(" ", ""));


            for(int i=0; i<stopList.size(); i++)
            {
                for(int j=0 ; j<wordsToSearch.size(); j++)
                {
                    if(!wordsToSearch.get(j).equals("") && wordsToSearch.get(j).substring(0,1).equals("\"")) {
                        wordsToSearch.set(j,wordsToSearch.get(0).replace("\"", ""));
                        flag=true;
                        continue;
                    }
                    if(wordsToSearch.get(j).equals(stopList.get(i)) &&!flag)
                    {
                        wordsToSearch.remove(stopList.get(i));
                    }

                }
            }
            filesList= createIndex.searchAndNot(wordsToSearch);
        }
        else if(found2)
        {
            Pattern pattern = Pattern.compile(" and ");
            Matcher matcher = pattern.matcher(searchWord);
            if (matcher.find()) {
                wordsToSearch.push(searchWord.substring(0, matcher.start()));
                wordsToSearch.push(searchWord.substring(matcher.end()));
            }
            wordsToSearch.set(0,wordsToSearch.get(0).replace(" ", ""));
            wordsToSearch.set(1,wordsToSearch.get(1).replace(" ", ""));


            for(int i=0; i<stopList.size(); i++)
            {
                for(int j=0 ; j<wordsToSearch.size(); j++)
                {
                    if((wordsToSearch.get(j).substring(0,1).equals("\""))) {
                        wordsToSearch.set(j,wordsToSearch.get(0).replace("\"", ""));
                        flag=true;
                        continue;
                    }
                    if(wordsToSearch.get(j).equals(stopList.get(i)) &&!flag)
                    {
                        wordsToSearch.remove(stopList.get(i));
                    }

                }
            }
            filesList=createIndex.searchAnd(wordsToSearch);
        }
        else
        {
            wordsToSearch.push(searchWord);
            for(int i=0; i<stopList.size(); i++)
            {
                for(int j=0 ; j<wordsToSearch.size(); j++)
                {
                    if((wordsToSearch.get(j).substring(0,1).equals("\""))) {
                        wordsToSearch.set(j,wordsToSearch.get(0).replaceAll("\"", ""));
                        flag=true; //"" exist
                        continue;
                    }
                    if(wordsToSearch.get(j).equals(stopList.get(i)) &&!flag)
                    {
                        wordsToSearch.remove(stopList.get(i));
                    }

                }
            }
            int length = searchWord.length();
            if(searchWord.contains("*"))
            {
                searchWord=searchWord.substring(0,length-1);
                filesList=createIndex.searchWild(searchWord, length-1);
            }
            else filesList=createIndex.SearchOr(wordsToSearch);

        }
        return filesList;
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(430, 75, 500, 500);
        frame.getContentPane().setLayout(null);
        JLabel title = new JLabel("Search");
        JTextField search = new JTextField();
        search.setBounds(100, 120, 167, 23);
        frame.getContentPane().add(search);
        title.setFont(new Font("Serif",Font.BOLD,20));
        title.setBounds(200,0,100,100);
        frame.getContentPane().add(title);

        JButton btnAdd = new JButton("search");
        frame.getRootPane().setDefaultButton(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Stack<String> wordsToSearch = new Stack<String>();
                Stack<String> filesList = new Stack<String>();
                Stack<String> finalsList = new Stack<String>();
                Stack<String> finalsList2 = new Stack<String>();
                Stack<String> finalList3= new Stack<String>(){};
                String[] words=new String[]{};
                String[] expr=new String[]{};
                boolean flag=false;
                boolean place=false;
                String searchWord = search.getText();
                searchWord=searchWord.toLowerCase();
                boolean found4= searchWord.contains("(");
                Stack<String> stopList = new Stack<String>();
                try {
                    File myObj = new File("index/stopList.txt");
                    Scanner myReader = new Scanner(myObj);
                    for (int i=0; myReader.hasNextLine(); i++) {
                        String data = myReader.nextLine();
                        stopList.push(data);
                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                if (found4)
                {
                    words = searchWord.split("[\\(||\\)]");
                    finalsList=findFiles(words[1], wordsToSearch, filesList, stopList);
                    if(words[0].equals(""))
                    {
                        place=true;
                        wordsToSearch.clear();
                        expr=words[2].split(" ");
                        words[2]= words[2].replace("[ or || and || and not ]", "");
                        finalsList2=findFiles(words[2], wordsToSearch, filesList, stopList);
                    }
                    else
                    {
                        wordsToSearch.clear();
                        expr=words[0].split(" ");
                        words[0]= words[0].replace("[ or || and || and not ]", "");
                        finalsList2=findFiles(words[0], wordsToSearch, filesList, stopList);
                    }

                    if(expr[1].equals("or"))
                    {
                        for(int i=0 ; i<finalsList2.size(); i++)
                        {
                         flag=createIndex.checkIfExist(finalsList,finalsList2.get(i));
                         if(flag) continue;
                         finalsList.push(finalsList2.get(i));
                        }
                        result.showResult(finalsList);
                    }
                    else if(expr[1].equals("and") && expr[2].equals("not"))
                    {
                        if(place) { //() first
                            for (int i = 0; i < finalsList2.size(); i++) {
                                for (int j = 0; j < finalsList.size(); j++) {
                                    finalsList.remove(finalsList2.get(i));
                                }
                            }
                            result.showResult(finalsList);
                        }
                        else //() second
                        {
                            for (int i = 0; i < finalsList.size(); i++) {
                                for (int j = 0; j < finalsList2.size(); j++) {
                                    finalsList2.remove(finalsList.get(i));
                                }
                            }
                            result.showResult(finalsList2);
                        }
                    }
                    else if(expr[1].equals("and"))
                    {
                        for(int i=0 ; i<finalsList2.size(); i++)
                        {
                            finalsList.push(finalsList2.get(i));
                        }
                        int finalSize=0;
                        for (int i = 0; i < finalsList.size(); i++) {
                            for (int index = i + 1; index < finalsList.size(); index++) {
                                if (finalsList.get(i).equals(finalsList.get(index))) {
                                    finalList3.push(finalsList.get(i));
                                }
                            }
                        }
                        result.showResult(finalList3);
                    }


                }
                else
                {
                    finalsList=findFiles(searchWord, wordsToSearch, filesList, stopList);
                    result.showResult(finalsList);
                }

                result.admin();
            }
        });
        btnAdd.setBounds(300, 120, 89, 23);
        frame.getContentPane().add(btnAdd);

        JButton btnSoundex = new JButton("search with soundex");
        btnSoundex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                Stack<String> finalList = new Stack<String>();
                Stack<String> stopList = new Stack<String>();
                try {
                    File myObj = new File("index/stopList.txt");
                    Scanner myReader = new Scanner(myObj);
                    for (int i=0; myReader.hasNextLine(); i++) {
                        String data = myReader.nextLine();
                        stopList.push(data);
                    }
                    myReader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
                String searchWord = search.getText();
                finalList=createIndex.SearchSoundex(searchWord);
                result.showResult(finalList);
                result.admin();
            }

        });
        btnSoundex.setBounds(100, 180, 170, 23);
        frame.getContentPane().add(btnSoundex);

    }

}

