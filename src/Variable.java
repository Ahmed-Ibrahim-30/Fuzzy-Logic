import java.util.ArrayList;

enum VariableType{IN,OUT}
public class Variable {
    private String name;
    private double crisp_value;
    private VariableType type;//In , out
    private int start;
    private int end;
    ArrayList<FuzzySet>FuzzySets;

    public Variable() {
        start=-1;
        end=-1;
        FuzzySets= new ArrayList<FuzzySet>();
    }
    public boolean assignVariable(String text){//proj_funding IN [0, 100]
        String a=""; int cnt=0;
        for (char c:text.toCharArray()){
            if(c==' ' && cnt==0){
                this.name=a;
                cnt++;
                a="";
            }else if(c==' '&& cnt==1){
                this.type=a.equalsIgnoreCase("IN")?VariableType.IN:VariableType.OUT;
                cnt++;
                a="";
            }else if(c==',' && cnt==2){
                this.start=general.toInt(a); cnt++; a="";
            }else if(c==']' && cnt==3){
                this.end=general.toInt(a); cnt++; a="";
            }
            if(c==' ' || c=='[' || c==']' || c==',')continue;
            a+=c;
        }
        return name != null && type != null && start != -1 && end != -1;
    }

    public void setFuzzySets(ArrayList<FuzzySet> fuzzySets) {
        FuzzySets .addAll(fuzzySets);
    }

    public void setCrisp_value(int crisp_value) {
        this.crisp_value = crisp_value;
    }

    public String getName() {
        return name;
    }



    public double getCrisp_value() {
        return crisp_value;
    }

    public VariableType getType() {
        return type;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public double getWeighted_average_Fuzzy(){
        double num1=0.0,num2=0.0;
        for (FuzzySet fuz:FuzzySets){
            num1+=fuz.getPercentage();
            num2+=fuz.getPercentage()*fuz.getCentroid();
        }
        crisp_value=num2/num1;
        return crisp_value;
    }
}
