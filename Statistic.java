import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Represents a statistic based on judged scenarios
 * COMP90041, Sem2, 2021: Final Project - Ethical Engines
 * @author liching
 */
public class Statistic {

    private int runNum;
    private final HashMap<String, Integer> totalNum;
    private final HashMap<String, Integer> survivors;
    private final ArrayList<Integer> ageList;
    private final ArrayList<String> fileData;

    /**
     * Construct a statistic by assigning 0 to runNum and
     * creating new hashmaps and lists to calculate and save statistics to file
     */
    public Statistic(){
        this.runNum = 0;
        this.totalNum = new HashMap<>();
        this.survivors = new HashMap<>();
        this.ageList = new ArrayList<>();
        this.fileData = new ArrayList<>();
    }



    /**
     * Update statistics data based on decision made in each scenario
     * @param decision which group saved
     * @param scenario the ethical dilemma
     */
    public void save(EthicalEngine.Decision decision, Scenario scenario){
        
        // Update run num for each judged scenario
        runNum++;
        fileData.add("runNum " + runNum);

        // Update value in totalNum hashmap based characters' attributes in scenario
        for (String attr : scenario.getCharAttr()){
            
            if (totalNum.containsKey(attr)){
                int updateTotal = totalNum.get(attr) + 1;
                this.totalNum.replace(attr, updateTotal);
            }
            else{
                this.totalNum.put(attr, 1);
            }
            fileData.add("totalNum " + attr);
        }

        // Update value in totalNum hashmap based legality of scenario
        if (scenario.getLegality() == Scenario.Legality.GREEN){
            if (totalNum.containsKey(Scenario.Legality.GREEN.name())){
                int updateTotal = totalNum.get(Scenario.Legality.GREEN.name()) + scenario.getCharCount();
                this.totalNum.replace(Scenario.Legality.GREEN.name(), updateTotal);
            }
            else{
                this.totalNum.put(Scenario.Legality.GREEN.name(), scenario.getCharCount());
            }
            fileData.add("totalNum " + Scenario.Legality.GREEN.name() + scenario.getCharCount());
        }
        else if (scenario.getLegality() == Scenario.Legality.RED){
            if (totalNum.containsKey(Scenario.Legality.RED.name())){
                int updateTotal = totalNum.get(Scenario.Legality.RED.name()) + scenario.getCharCount();
                this.totalNum.replace(Scenario.Legality.RED.name(), updateTotal);
            }
            else{
                this.totalNum.put(Scenario.Legality.RED.name(), scenario.getCharCount());
            }
            fileData.add("totalNum " + Scenario.Legality.RED.name() + scenario.getCharCount());
        }

        // Update value in survivors hashmap if passengers were saved
        if (decision == EthicalEngine.Decision.PASSENGERS){

            // Update value in survivors hashmap based characters' attributes in scenario
            for (String attr : scenario.getPasAttr()){
                if (survivors.containsKey(attr)){
                    int updateTotal = survivors.get(attr) + 1;
                    this.survivors.replace(attr, updateTotal);
                }
                else{
                    this.survivors.put(attr, 1);
                }
                fileData.add("survivors " + attr);
            }

            // Update value in survivors hashmap based legality of scenario
            if (scenario.getLegality() == Scenario.Legality.GREEN){
                if (survivors.containsKey(Scenario.Legality.GREEN.name())){
                    int updateTotal = survivors.get(Scenario.Legality.GREEN.name()) + scenario.getPasCount();
                    this.survivors.replace(Scenario.Legality.GREEN.name(), updateTotal);
                }
                else{
                    this.survivors.put(Scenario.Legality.GREEN.name(), scenario.getPasCount());
                }
                fileData.add("survivors " + Scenario.Legality.GREEN.name() + scenario.getPasCount());
            }
            else if (scenario.getLegality() == Scenario.Legality.RED){
                if (survivors.containsKey(Scenario.Legality.RED.name())){
                    int updateTotal = survivors.get(Scenario.Legality.RED.name()) + scenario.getPasCount();
                    this.survivors.replace(Scenario.Legality.RED.name(), updateTotal);
                }
                else{
                    this.survivors.put(Scenario.Legality.RED.name(), scenario.getPasCount());
                }
                fileData.add("survivors " + Scenario.Legality.RED.name() + scenario.getPasCount());
            }

            // Update age list of survivors
            for (Character c : scenario.getPasList()){
                if (c instanceof Human){
                    ageList.add(c.getAge());
                    fileData.add("survivors age" + c.getAge());
                }
            }
        }

        // Update value in survivors hashmap if pedestrians were saved
        else if (decision == EthicalEngine.Decision.PEDESTRIANS){

            // Update value in survivors hashmap based characters' attributes in scenario
            for (String attr : scenario.getPedAttr()){
                if (survivors.containsKey(attr)){
                    int updateTotal = survivors.get(attr) + 1;
                    this.survivors.replace(attr, updateTotal);
                }
                else{
                    this.survivors.put(attr, 1);
                }
                fileData.add("survivors " + attr);
            }

            // Update value in survivors hashmap based legality of scenario
            if (scenario.getLegality() == Scenario.Legality.GREEN){
                if (survivors.containsKey(Scenario.Legality.GREEN.name())){
                    int updateTotal = survivors.get(Scenario.Legality.GREEN.name()) + scenario.getPedCount();
                    this.survivors.replace(Scenario.Legality.GREEN.name(), updateTotal);
                }
                else{
                    this.survivors.put(Scenario.Legality.GREEN.name(), scenario.getPedCount());
                }
                fileData.add("survivors " + Scenario.Legality.GREEN.name() + scenario.getPedCount());
            }
            else if (scenario.getLegality() == Scenario.Legality.RED){
                if (survivors.containsKey(Scenario.Legality.RED.name())){
                    int updateTotal = survivors.get(Scenario.Legality.RED.name()) + scenario.getPedCount();
                    this.survivors.replace(Scenario.Legality.RED.name(), updateTotal);
                }
                else{
                    this.survivors.put(Scenario.Legality.RED.name(), scenario.getPedCount());
                }
                fileData.add("survivors " + Scenario.Legality.RED.name() + scenario.getPedCount());
            }

            // Update age list of survivors
            for (Character c : scenario.getPedList()){
                if (c instanceof Human){
                    ageList.add(c.getAge());
                    fileData.add("survivors age" + c.getAge());
                }
            }
        }
    }

    /**
     * Load/update statistics according to values extracted from log file
     * @param type type of data to be updated in statistic (totalnum, survivors, runnum)
     * @param attr attributes data to be updated in different data type
     */
    public void load(String type, String attr){
        switch (type) {
            case "runnum":
                runNum++;
                break;
            case "survivors":
                if (!( attr.startsWith("age") || attr.startsWith("green") || attr.startsWith("red") )) {
                    if (survivors.containsKey(attr)) {
                        int updateTotal = survivors.get(attr) + 1;
                        this.survivors.replace(attr, updateTotal);
                    } else {
                        this.survivors.put(attr, 1);
                    }
                }
                else if (attr.startsWith("age")){
                    int survivorsAge = Integer.parseInt(attr.substring("age".length()));
                    ageList.add(survivorsAge);
                }
                else if (attr.startsWith("green")){
                    if (survivors.containsKey("green")) {
                        int updateTotal = survivors.get("green") + Integer.parseInt(attr.substring("green".length()));
                        this.survivors.replace("green", updateTotal);
                    } else {
                        this.survivors.put("green", Integer.parseInt(attr.substring("green".length())));
                    }                    
                }
                else if (attr.startsWith("red")){
                    if (survivors.containsKey("red")) {
                        int updateTotal = survivors.get("red") + Integer.parseInt(attr.substring("red".length()));
                        this.survivors.replace("red", updateTotal);
                    } else {
                        this.survivors.put("red", Integer.parseInt(attr.substring("red".length())));
                    }
                }
                break;
            case "totalnum":
                if (!( attr.startsWith("green") || attr.startsWith("red") )){
                    if (totalNum.containsKey(attr)) {
                        int updateTotal = totalNum.get(attr) + 1;
                        this.totalNum.replace(attr, updateTotal);
                    }
                    else {
                        this.totalNum.put(attr, 1);
                    }
                }
                else if (attr.startsWith("green")){
                    if (totalNum.containsKey("green")) {
                        int updateTotal = totalNum.get("green") + Integer.parseInt(attr.substring("green".length()));
                        this.totalNum.replace("green", updateTotal);
                    } else {
                        this.totalNum.put("green", Integer.parseInt(attr.substring("green".length())));
                    }                    
                }
                else if (attr.startsWith("red")){
                    if (totalNum.containsKey("red")) {
                        int updateTotal = totalNum.get("red") + Integer.parseInt(attr.substring("red".length()));
                        this.totalNum.replace("red", updateTotal);
                    } else {
                        this.totalNum.put("red", Integer.parseInt(attr.substring("red".length())));
                    }
                }
                break;
        }
    }

    /**
     * Get fileData which contains list of attributes to be written to log file
     * @return list of attributes
     */
    public ArrayList<String> getFileData(){
        return fileData;
    }

    /**
     * Clear fileData to avoid duplicate data written to log file
     */
    public void clearFileData(){
        this.fileData.clear();
    }

    /**
     * Compute survival ratios for statistics and sort by ratios then attributes alphabetically
     * @return attributes with corresponding ratios sorted
     */
    private LinkedHashMap<String, Double> sortedRatio(){
        
        // Compute ratios and put into TreeMap so attributes are sorted alphabetically
        TreeMap<String, Double> ratio = new TreeMap<>();
        for (String attr : totalNum.keySet()){
            if (survivors.containsKey(attr)){
                double ratioNum = (double) survivors.get(attr) / (double) totalNum.get(attr);
                double roundRatioNum = Math.ceil(ratioNum * 100) / 100;
                ratio.put(attr, roundRatioNum);
            }
            else{
                ratio.put(attr, 0.0);
            }
        }

        // Sort ratios based on data from TreeMap
        // Solution idea from https://stackoverflow.com/questions/33945836/java-hashmap-sort-by-value-then-key
        ArrayList<Map.Entry<String, Double>> list = new ArrayList<>(ratio.entrySet());
        list.sort((a, b) -> -a.getValue().compareTo(b.getValue()));

        LinkedHashMap<String, Double> sortedRatio = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list){
            sortedRatio.put(entry.getKey(), entry.getValue());
        }

        return sortedRatio;

    }

    /**
     * Compute average age based on age data in age list
     * @return average human survivor age
     */
    private double getAverageAge(){

        double roundAverageAge = 0.0;
        if (ageList.size() != 0){
            int ageSum = 0;
            for (int age : ageList){
                ageSum += age;
            }
            double averageAge = (double) ageSum / (double) ageList.size();
            roundAverageAge = Math.ceil(averageAge * 100) / 100;
        }

        return roundAverageAge;

    }

    /**
     * Display statistics based on statistics data
     * @param headerName type of statistic
     * @return statistics
     */
    public String toString(String headerName){

        String header = 
                        "======================================\n" +
                        "# " + headerName + "\n" +
                        "======================================\n";

        String subHeader ="- % SAVED AFTER " + runNum + " RUNS\n";

        String body = "";
        for (String attr : sortedRatio().keySet()){
            body += attr + ": " + String.format("%.2f",sortedRatio().get(attr)) + "\n";
        }

        String footer = "--\naverage age: " + String.format("%.2f",getAverageAge());

        return header + subHeader + body.toLowerCase() + footer;

    }

}
