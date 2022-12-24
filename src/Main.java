import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class Main {
    static int index=0;
    static void WriteFile(String Word){
        try {
            FileWriter myWriter = new FileWriter("output.txt");
            myWriter.write(Word);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static ArrayList<String>readFile(String path){
        ArrayList<String>arr=new ArrayList<>();
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                arr.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return arr;
    }
    static boolean FirstMenu(int choice){
        return choice == 1; // Create a new fuzzy system or Quit
    }

    static boolean isNum(String text){
        return text.equals("1") || text.equals("2") ||
                text.equals("3") ||
                text.equals("4") ||
                text.equals("5");
    }
    static String skipUntilNum(ArrayList<String>arr){
        String text="";
        while (!isNum(arr.get(index))){
            text+=arr.get(index++);
        }
        return text;
    }
    static boolean valid(String text){
        if(text.equalsIgnoreCase("Close") ||
                text.equalsIgnoreCase("x") ){
            index++;
            return false;
        }return true;
    }
    static ArrayList<Variable>variableMenu(ArrayList<String>arr){
        ArrayList<Variable>all=new ArrayList<>();
        while (valid(arr.get(index))){
            Variable var=new Variable();
            boolean varValid=var.assignVariable(arr.get(index));
            if(!varValid){ skipUntilNum(arr); break;}
            index++;
            all.add(var);
        }
        return all;
    }
    static boolean fuzzySetMenu(ArrayList<String>arr,FuzzySystem fuzzySystem){
        ArrayList<FuzzySet>all=new ArrayList<>();
        String VarName="";
        VarName=arr.get(index++);
        while (valid(arr.get(index))){
            FuzzySet fuzzySet=new FuzzySet();
            boolean varValid=fuzzySet.assignFuzzySet(arr.get(index));
            if(!varValid){ skipUntilNum(arr); break;}
            index++;
            all.add(fuzzySet);
        }
        boolean FoundVariable=false;
        for (Variable var:fuzzySystem.variables){
            if(Objects.equals(var.getName(), VarName)){
                var.setFuzzySets(all);
                FoundVariable=true;
                break;
            }
        }
        return FoundVariable;
    }
    static void ruleMenu(ArrayList<String>arr,FuzzySystem fuzzySystem){
        for (String text:arr){
            Rule rule=new Rule();
            rule.assignRule(text,fuzzySystem);
        }
    }

    static void secondMenu(ArrayList<String>arr){// Create a new fuzzy system
        general.fuzzySystem.setName(arr.get(index++));
        general.fuzzySystem.setDescription(skipUntilNum(arr));
        ArrayList<String>allRule=new ArrayList<>();
        while (!arr.get(index).equalsIgnoreCase("CLOSE")){
            int choice=general.toInt(arr.get(index++));
            if(choice==1)general.fuzzySystem.setVariables(variableMenu(arr));
            else if(choice==2){
                fuzzySetMenu(arr,general.fuzzySystem);
            }else if(choice==3){
                while (valid(arr.get(index))){
                    allRule.add(arr.get(index++));
                }
            }else if(choice==4){//proj_funding: 50
                for (Variable var : general.fuzzySystem.variables){
                    if(var.getType()==VariableType.OUT)continue;
                    int num=general.toInt(arr.get(index++));
                    var.setCrisp_value(num);
                }
                general.fuzzySystem.performAlgorithm(false,new Variable());
                if(allRule.isEmpty()){
                    String word="Please Enter Fuzzy System Rules :)\n";
                    word+="Running the simulation…\n" +
                            "Fuzzification => done\n" +
                            "Inference => done\n" +
                            "Defuzzification => not done\n";
                    WriteFile(word);
                    skipUntilNum(arr);
                    continue;
                }else ruleMenu(allRule,general.fuzzySystem);
                for (Variable var: general.fuzzySystem.variables){
                    if(var.getType()==VariableType.OUT){
                        double best=var.getWeighted_average_Fuzzy();
                        general.fuzzySystem.performAlgorithm(true,var);
                        double min=0; FuzzySet ans=new FuzzySet();
                        for (FuzzySet fuz: var.FuzzySets){
                            if(fuz.getPercentage()>min){
                                min=fuz.getPercentage();
                                ans=fuz;
                            }
                        }
                        String word="Running the simulation…\n" +
                                "Fuzzification => done\n" +
                                "Inference => done\n" +
                                "Defuzzification => done\n";
                        word+="The predicted "+var.getName()+" is "+ ans.getName() +" ( "+best+" )\n";
                        WriteFile(word);
                    }
                }

            }
        }
    }


    public static void run() {
        index=0;
        ArrayList<String>arr=readFile(general.FileName);
        while (FirstMenu(general.toInt(arr.get(index++))) || index<arr.size()){
            general.fuzzySystem=new FuzzySystem();
            secondMenu(arr);
            break;
        }
    }
}

