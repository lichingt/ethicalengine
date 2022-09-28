import java.util.ArrayList;
/**
 * Represents a scenario to decide on
 * COMP90041, Sem2, 2021: Final Project - Ethical Engines
 * @author liching
 */
public class Scenario {

    /**
     * Legality of crossing
     */
    public enum Legality {

        /**
         * Legality crossing: yes
         */
        GREEN,
        
        /**
         * Legality crossing: no
         */
        RED
        
    }

    private Legality legality;
    private ArrayList<Character> passengers;
    private ArrayList<Character> pedestrians;

    /**
     * Construct a scenario and assign default value of null for legality, create new list of passengers and pedestrians.
     */
    public Scenario(){
        this.legality = null;
        this.passengers = new ArrayList<>();
        this.pedestrians =  new ArrayList<>();
    }

    /**
     * Construct a scenario based on legality given, create new list of passengers and pedestrians.
     * @param legality legality of crossing in scenario
     */
    public Scenario(Legality legality){
        this.legality = legality;
        this.passengers = new ArrayList<>();
        this.pedestrians =  new ArrayList<>();
    }

    /**
     * Given a character, add character to passengers list
     * @param character character to be added to passengers list
     */
    public void addPas(Character character){
        this.passengers.add(character);
    }

    /**
     * Given a character, add character to pedestrians list
     * @param character character to be added to pedestrians list
     */
    public void addPed(Character character){
        this.pedestrians.add(character);
    }

    /**
     * Get legality of scenario
     * @return legality of scenario
     */
    public Legality getLegality(){
        return legality;
    }

    /**
     * Get number of passengers
     * @return number of passengers
     */
    public int getPasCount(){
        return passengers.size();
    }

    /**
     * Get number of pedestrians
     * @return number of pedestrians
     */
    public int getPedCount(){
        return pedestrians.size();
    }

    /**
     * Get number of passengers and pedestrians
     * @return number of passengers and pedestrians
     */
    public int getCharCount(){
        return passengers.size() + pedestrians.size();
    }

    /**
     * Get list of passengers
     * @return list of passengers
     */
    public ArrayList<Character> getPasList(){
        return passengers;
    }

    /**
     * Get list of pedestrians
     * @return list of pedestrians
     */
    public ArrayList<Character> getPedList(){
        return pedestrians;
    }

    /**
     * Get list of character's attributes in passengers list which are relevant to calculating statistics
     * @return list of attributes
     */
    public ArrayList<String> getPasAttr(){
        ArrayList<String> pasAttr = new ArrayList<>();
        for (Character c : this.passengers){
            pasAttr.addAll(c.getAttr());
        }
        return pasAttr;
    }

    /**
     * Get list of character's attributes in pedestrians list which are relevant to calculating statistics
     * @return list of attributes
     */
    public ArrayList<String> getPedAttr(){
        ArrayList<String> pedAttr = new ArrayList<>();
        for (Character c : this.pedestrians){
            pedAttr.addAll(c.getAttr());
        }
        return pedAttr;
    }

    /**
     * Get list of character's attributes in passengers and pedestrians list which are relevant to calculating statistics
     * @return list of attributes
     */
    public ArrayList<String> getCharAttr(){
        ArrayList<String> charAttr = new ArrayList<>();

        charAttr.addAll(getPedAttr());
        charAttr.addAll(getPasAttr());

        return charAttr;
    }

    /**
     * Set attributes of scenario randomly
     */
    public void generateRandomScenario(){

        // Legality 50/50
        if(Math.random() > 0.5) {
            legality = Legality.GREEN;
        } else {
            legality = Legality.RED;
        }

        this.passengers = new ArrayList<>();

        // Passengers number
        // 5% chance of number between 31 and 60
        // 10% chance of number between 11 and 30
        // 25% chance of number between 6 and 10
        // 60 % chance of number between 1 and 5
        int passengersNum;
        double spread = Math.random();
        if (spread < .05){
            passengersNum = (int)(Math.random() * 60) + 1;
        }
        else if (spread < 0.15){
            passengersNum = (int)(Math.random() * 30) + 1;
        }
        else if (spread < 0.4){
            passengersNum = (int)(Math.random() * 10) + 1;
        }
        else {
            passengersNum = (int)(Math.random() * 5) + 1;
        }

        // 80% chance of generating a human as a passenger
        // 20% chance of generating an animal as a passenger
        for (int i = 0; i < passengersNum ; i++){

            if(Math.random() < 0.8){
                Human randomHuman = new Human();
                randomHuman.generateRandomHuman();
                passengers.add(randomHuman);
            }
            else{
                Animal randomAnimal = new Animal();
                randomAnimal.generateRandomAnimal();
                passengers.add(randomAnimal);
            }
            
        }

        this.pedestrians =  new ArrayList<>();

        // Pedestrians number
        // 5% chance of number between 31 and 60
        // 10% chance of number between 11 and 30
        // 25% chance of number between 6 and 10
        // 60 % chance of number between 1 and 5
        int pedestriansNum;
        spread = Math.random();
        if (spread < .05){
            pedestriansNum = (int)(Math.random() * 60) + 1;
        }
        else if (spread < 0.15){
            pedestriansNum = (int)(Math.random() * 30) + 1;
        }
        else if (spread < 0.4){
            pedestriansNum = (int)(Math.random() * 10) + 1;
        }
        else {
            pedestriansNum = (int)(Math.random() * 5) + 1;
        }

        // 80% chance of generating a human as a pedestrian
        // 20% chance of generating an animal as a pedestrian
        for (int i = 0; i < pedestriansNum ; i++){

            if(Math.random() < 0.8){
                Human randomHuman = new Human();
                randomHuman.generateRandomHuman();
                pedestrians.add(randomHuman);
            }
            else{
                Animal randomAnimal = new Animal();
                randomAnimal.generateRandomAnimal();
                pedestrians.add(randomAnimal);
            }
            
        }

        // 25% probability of appearing in scenario,
        // which has 50/50 of being passenger or pedestrian
        // However, given 80% chance the character is human,
        // only 20% chance of You appearing in scenario
        if(Math.random() < 0.25){
            if(Math.random() > 0.5){
                int youNum  = (int)(Math.random() * getPasCount());
                if (passengers.get(youNum) instanceof Human you){
                    you.setIsYou(Human.IsYou.TRUE);
                }
            }
            else{
                int youNum  = (int)(Math.random() * getPedCount());
                if (pedestrians.get(youNum) instanceof Human you){
                    you.setIsYou(Human.IsYou.TRUE);
                }
            }
            
        }

    }

    /**
     * Description of scenario with applicable attributes
     * @return attributes for description
     */
    @Override
    public String toString(){

        String header = """
                    ======================================
                    # Scenario
                    ======================================
                    """;


        String subHeader;

        if (legality == Legality.GREEN){
            subHeader = "Legal Crossing: yes\n";
        }
        else if (legality == Legality.RED){
            subHeader = "Legal Crossing: no\n";
        }
        else{
            subHeader = "Legal Crossing: unknown\n";
        }

        String body = "Passengers (" + getPasCount() + ")\n";

        for (Character c : getPasList()){
            body += c.toString() + "\n";
        }

        body = body + "Pedestrians (" + getPedCount() + ")\n";
        for (Character c : getPedList()){
            body += c.toString() + "\n";
        }

        String footer = "Who should be saved? (passenger(s) [1] or pedestrian(s) [2])\n";
        
        return header + subHeader + body + footer;
    }

}
