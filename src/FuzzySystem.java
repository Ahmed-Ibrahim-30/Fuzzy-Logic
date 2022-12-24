import java.util.ArrayList;

public class FuzzySystem {
    String name;
    String description;
    ArrayList<Variable>variables;
    ArrayList<Rule>rules;

    public FuzzySystem() {
        variables=new ArrayList<>();
        rules=new ArrayList<>();
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setVariables(ArrayList<Variable> variables) {
        this.variables .addAll(variables);
    }

    public void setRule(Rule r) {
        this.rules.add(r);
    }
    public void performAlgorithm(boolean spec,Variable one){
        for (Variable var:variables){
            if(spec && var!=one)continue;
            double crisp=var.getCrisp_value();
            for (FuzzySet fuzzySet:var.FuzzySets){
                if(crisp>=fuzzySet.values.get(fuzzySet.values.size()-1) ||
                        crisp<=fuzzySet.values.get(0)){
                    fuzzySet.setPercentage(0);
                }else if(crisp<fuzzySet.values.get(fuzzySet.values.size()-1) &&
                        crisp>fuzzySet.values.get(fuzzySet.values.size()-2)){
                    int first=fuzzySet.values.get(fuzzySet.values.size()-1);//0
                    int sec=fuzzySet.values.get(fuzzySet.values.size()-2);//1
                    double m=(1.0)/(sec-first);
                    double c=0-(m*first);
                    fuzzySet.setPercentage((m*crisp)+c);
                }else if(crisp<fuzzySet.values.get(1) &&
                        crisp>fuzzySet.values.get(0)){
                    int first=fuzzySet.values.get(1);//1
                    int sec=fuzzySet.values.get(0);//0
                    double m=(1.0)/(first-sec);
                    double c=1-(m*first);
                    fuzzySet.setPercentage((m*crisp)+c);
                }
                else {
                    fuzzySet.setPercentage(1);
                }
            }
        }
    }


}
