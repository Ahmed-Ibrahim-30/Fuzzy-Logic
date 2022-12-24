import jdk.dynalink.Operation;

import java.util.*;

class pair{
    String name;
    String fuzzy;
    double value;

    public pair() {
        name="";
        fuzzy="";
    }
}
enum operation{and,and_not,or}
public class Rule {

    pair varOUT;

    public Rule() {

    }

    public void assignRule(String text,FuzzySystem fuzzySystem){//proj_funding high or exp_level expert => risk low
        int cnt=0; StringBuilder a= new StringBuilder();
        pair tmp=new pair();
        String op="";
        ArrayList<Double>arr=new ArrayList<>();
        for (char c : text.toCharArray()){
            if(c==' ' && cnt==0 && !a.isEmpty()){
                tmp.name= a.toString();
                cnt++; a = new StringBuilder();
            }
            else if (c == ' ' && cnt == 1) {
                tmp.fuzzy=a.toString();
                cnt++; a = new StringBuilder();
                for (Variable var : fuzzySystem.variables){
                    if(var.getName().equalsIgnoreCase(tmp.name)){
                        for (FuzzySet fuz:var.FuzzySets){
                            if(fuz.getName().equalsIgnoreCase(tmp.fuzzy)){
                                arr.add(fuz.getPercentage());
                                break;
                            }
                        }
                    }
                }
                if(!op.equals("") && !op.equalsIgnoreCase("OR")){
                    double ans;
                    if(op.equalsIgnoreCase("AND")){
                        ans = Math.min(arr.get(arr.size() - 1), arr.get(arr.size() - 2));
                    }else {
                        ans = Math.min(1 -arr.get(arr.size() - 1),  arr.get(arr.size() - 2));
                    }
                    arr.set(arr.size()-2,ans);
                    arr.remove(arr.size()-1);
                    op="";
                }
                tmp=new pair();
            }
            else if (c == ' ' && cnt == 2) {
                op=a.toString();
                cnt=0; a = new StringBuilder();
                tmp=new pair();
            }
            else if(c=='='){
                cnt=0; a = new StringBuilder();
                tmp=new pair();
            }
            if(c==' ' || c=='=' || c=='>')continue;
            a.append(c);
        }
        tmp.fuzzy=a.toString();
        tmp.value= Collections.max(arr);
        for (Variable var : fuzzySystem.variables){
            if(var.getName().equalsIgnoreCase(tmp.name)){
                for (FuzzySet fuz:var.FuzzySets){
                    if(fuz.getName().equalsIgnoreCase(tmp.fuzzy)){
                        fuz.setPercentage(Math.max(tmp.value, fuz.getPercentage()));
                        break;
                    }
                }
            }
        }
    }
}
