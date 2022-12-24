import java.util.ArrayList;

enum FuzzySetType{TRI,TRAP}
public class FuzzySet {
    private String name;
    private double centroid;
    private double percentage;
    private FuzzySetType type;
    ArrayList<Integer>values;

    public FuzzySet() {
        values=new ArrayList<>();
    }
    public void setValues(int item) {
        values.add(item);
    }
    public boolean assignFuzzySet(String text){//expert TRI 30 60 60
        String a=""; int cnt=0;
        double sum=0;
        for (char c:text.toCharArray()){
            if(c==' ' && cnt==0){
                this.name=a;
                cnt++;
                a="";
            }else if(c==' '&& cnt==1){
                this.type=a.equalsIgnoreCase("TRI")?FuzzySetType.TRI:FuzzySetType.TRAP;
                cnt++;
                a="";
            }else if(c==' ' && cnt==2){
                values.add(general.toInt(a));
                sum+=general.toDouble(a);
                a="";
            }
            if(c==' ')continue;
            a+=c;
        }
        if(!a.equals("")) {
            values.add(general.toInt(a)); sum+=general.toDouble(a);
        }
        centroid=sum /(int)values.size();

        return name != null && type != null && !values.isEmpty();
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }

    public String getName() {
        return name;
    }
    public double getCentroid() {
        return centroid;
    }

    public FuzzySetType getType() {
        return type;
    }
}
