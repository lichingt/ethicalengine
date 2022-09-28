import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream; 
import java.io.PrintWriter;
/**
 * COMP90041, Sem2, 2021: Final Project - Ethical Engines
 * @author liching
 */
public class EthicalEngine {

    // Constants for default settings within the program
    private static final int CONFIG_COLUMN = 10;
    private static final int SCENARIO_DISPLAY_LOOP = 3;

    /**
     * Decision options to select which group to save for each scenario
     */
    public enum Decision {

        /**
         * Passengers group in scenario
         */
        PASSENGERS,
        
        /**
         * Pedestrians group in scenario
         */
        PEDESTRIANS
        
    }



    /**
     * Decision Algorithm which decides whether to save the passengers or the pedestrians
     * @param scenario the ethical dilemma
     * @return which group to save
     */
    public static Decision decide(Scenario scenario) {

        // Assign initial score values for pedestrians to 5 and passengers to 0.
        int pedScore = 5;   int pasScore = 0;

        // Add 5 scores to pedestrians if it is a legal crossing, otherwise add 5 scores to passengers.
        if(scenario.getLegality() == Scenario.Legality.GREEN){
            pedScore += 5;
        }
        else if(scenario.getLegality() == Scenario.Legality.RED){
            pasScore += 5;
        }

        // Count number of attributes for human in scenario and assign points.
        int humanPed = 0;       int ageNumPed = 0;      int pregNumPed = 0;
        for (Character c : scenario.getPedList()){
            if (c instanceof Human h){
                humanPed++;
                if (h.getAgeCategory() == Human.AgeCategory.BABY){
                    ageNumPed += 4;
                }
                else if (h.getAgeCategory() == Human.AgeCategory.CHILD){
                    ageNumPed += 3;
                }
                else if (h.getAgeCategory() == Human.AgeCategory.ADULT){
                    ageNumPed += 2;
                }
                else if (h.getAgeCategory() == Human.AgeCategory.SENIOR){
                    ageNumPed += 1;
                }
                if (h.getIsPregnant() == Human.IsPregnant.TRUE){
                    pregNumPed++;
                }
            }
        }

        int humanPas =0;        int ageNumPas = 0;     int pregNumPas = 0;
        for (Character c : scenario.getPasList()){
            if (c instanceof Human h){
                humanPas++;
                if (h.getAgeCategory() == Human.AgeCategory.BABY){
                    ageNumPas += 4;
                }
                else if (h.getAgeCategory() == Human.AgeCategory.CHILD){
                    ageNumPas += 3;
                }
                else if (h.getAgeCategory() == Human.AgeCategory.ADULT){
                    ageNumPas += 2;
                }
                else if (h.getAgeCategory() == Human.AgeCategory.SENIOR){
                    ageNumPas += 1;
                }
                if (h.getIsPregnant() == Human.IsPregnant.TRUE){
                    pregNumPas++;
                }
            }
        }

        // Proportionate number of points for pedestrians and passengers and assign additional scores.
        if (humanPed + humanPas != 0){
            pedScore += humanPed/(humanPed+humanPas) * 20;
            pasScore += humanPas/(humanPed+humanPas) * 20;
        }
        if (ageNumPed+ageNumPas != 0){
            pedScore += ageNumPed/(ageNumPed+ageNumPas) * 15;
            pasScore += ageNumPas/(ageNumPed+ageNumPas) * 15;
        }
        if (pregNumPed+pregNumPas != 0){
            pedScore += pregNumPed/(pregNumPed+pregNumPas) * 2;
            pasScore += pregNumPas/(pregNumPed+pregNumPas) * 2;
        }

        // Return decision based on which group scores higher.
        if(pedScore > pasScore) {
            return Decision.PEDESTRIANS;
        } else {
            return Decision.PASSENGERS;
        }
    }



    /**
     * 
     * PROJECT SETUP
     * Program Launch with Flags (Program entry)
     * @param args optional flags for program launch ("-h" for help)
     * 
     */
    public static void main(String[] args) {

        EthicalEngine ethicalEngine = new EthicalEngine();

        boolean startProgram = true;
        String configFilePath = null;
        String logFilePath = null;

        for (int i = 0; i < args.length; i++){

            if (args[i].equals("-h") || args[i].equals("--help")){
                ethicalEngine.displayHelpText();
                startProgram = false;
                break;
            }

            else{
                if (args[i].equals("-c") || args[i].equals("--config")){
                    if(i+1 == args.length){
                        ethicalEngine.displayHelpText();
                        startProgram = false;
                        break;
                    }
                    else if(args[i+1].equals("-l") || args[i+1].equals("--log")){
                        ethicalEngine.displayHelpText();
                        startProgram = false;
                        break;
                    }
                    else{
                        configFilePath = args[i+1];
                    }
                }

                if (args[i].equals("-l") || args[i].equals("--log")){
                    if(i+1 == args.length){
                        ethicalEngine.displayHelpText();
                        startProgram = false;
                        break;
                    }
                    else if(args[i+1].equals("-c") || args[i+1].equals("--config")){
                        ethicalEngine.displayHelpText(); 
                        startProgram = false;
                        break;       
                    }
                    else{
                        logFilePath = args[i+1];
                    }
                }
            }
        }

        if (startProgram){
            ethicalEngine.mainMenu(configFilePath, logFilePath);
        }

    }

    /**
     * Print Help
     */
    private void displayHelpText(){

        String helpText = """
                EthicalEngine - COMP90041 - Final Project

                Usage: java EthicalEngine [arguments]

                Arguments:
                    -c or --config        Optional: path to config file
                    -h or --help          Optional: print Help (this message) and exit
                    -l or --log           Optional: path to data log file""";

        System.out.println(helpText);    
        
    }



    /**
     * 
     * MAIN MENU
     * @param configFilePath path to config file
     * @param logFilePath path to data log file
     * 
     */
    private void mainMenu(String configFilePath, String logFilePath){

        ArrayList<Scenario> scenarioList = new ArrayList<>();

        // READING IN THE CONFIG FILE
        if (configFilePath != null){
            loadConfigFile(configFilePath, scenarioList);
        }

        // Main Menu Display
        mainMenuDisplay(configFilePath, scenarioList);

        boolean programRun = true;
        Scanner keyboard = new Scanner(System.in);
        String userConsent = null;

        while (programRun){

            // Process command
            String command = keyboard.nextLine();

            if (command.equalsIgnoreCase("judge") || command.equalsIgnoreCase("j")){

                // JUDGING SCENARIOS
                // Collect user consent which applies throughout the program run.
                if (userConsent == null){
                    userConsent = collectUserConsent(keyboard);
                }
                judgeScenarios(userConsent, configFilePath, logFilePath, scenarioList, keyboard);
                mainMenuDisplay(configFilePath, scenarioList);
            }
            else if (command.equalsIgnoreCase("run") || command.equalsIgnoreCase("r")){

                // RUN SIMULATION
                runSimulation(configFilePath, logFilePath, scenarioList, keyboard);
                mainMenuDisplay(configFilePath, scenarioList);
            }
            else if (command.equalsIgnoreCase("audit") || command.equalsIgnoreCase("a")){

                // AUDIT FROM HISTORY
                auditHistory(logFilePath);
                mainMenuDisplay(configFilePath, scenarioList);
            }
            else if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("q")){
                
                programRun = false;
            }    
        }
        keyboard.close();
    }

    /**
     * Main Menu Display
     * @param configFilePath path to config file
     * @param scenarioList list of scenarios
     */
    private void mainMenuDisplay(String configFilePath, ArrayList<Scenario> scenarioList){
        displayWelcomeText();
        displayScenarioImported(configFilePath, scenarioList);
        displayInitialMessage();
    }

    /**
     * Main Menu Display (Part 1): Display welcome text from "welcome.ascii" file provided
     */
    private void displayWelcomeText(){

        File welcomeTextFile = new File("welcome.ascii");
        try{
            Scanner read = new Scanner(welcomeTextFile);
            while (read.hasNextLine()){
                System.out.println(read.nextLine());
            }
            read.close();
            System.out.println();
        }
        catch(Exception e){
            System.out.print("ERROR: could not load welcome text file.");
            System.exit(1);
        }

    }

    /**
     * Main Menu Display (Part 2): Display humber of scenarios imported (if applicable)
     * @param configFilePath path to config file
     * @param scenarioList list of scenarios
     */
    private void displayScenarioImported(String configFilePath, ArrayList<Scenario> scenarioList){
        if (configFilePath != null){
            System.out.println(scenarioList.size() + " Scenarios imported.");
        }
    }

    /**
     * Main Menu Display (Part 3): Initial message followed by a command prompt
     */
    private void displayInitialMessage(){

        String initialMessage = """
            Please enter one of the following commands to continue:
            - judge scenarios: [judge] or [j]
            - run simulations with the in-built decision algorithm: [run] or [r]
            - show audit from history: [audit] or [a]
            - quit the program: [quit] or [q]""";

        System.out.println(initialMessage);
        System.out.print("> ");
        
    }



    /**
     * 
     * READING IN THE CONFIG FILE
     * @param configFilePath path to config file
     * @param scenarioList list of scenarios
     * 
     */
    private void loadConfigFile(String configFilePath, ArrayList<Scenario> scenarioList){
        
        try{
            Scanner read = new Scanner(new File(configFilePath));
            configFileLineProcessor(read, scenarioList);
            read.close();

            // If no scenario successfully imported, throw exception.
            if (scenarioList.size() == 0){
                throw new Exception();
            }
        }
        catch(FileNotFoundException e){
            System.out.println("ERROR: could not find config file.");
            System.exit(1);
        }
        catch(Exception e){
            System.out.println("ERROR: could not load config file.");
            System.exit(1);
        }
    }

    /**
     * Parsing the Configuration File (Step 1): Check every line in file
     * @param read instance of Scanner to read file
     * @param scenarioList list of scenarios
     */
    private void configFileLineProcessor(Scanner read, ArrayList<Scenario> scenarioList){
        
        int row = -1;

        while (read.hasNextLine()){

            // Add ",end" to make sure that "String[] columnData" later
            // in "configFileLineDataProcessor" method will have at least 10 elements
            // Otherwise, with "line.split(",")", there will not be enough elements in the
            // array, if towards the end of line is blank
            String line = read.nextLine() + ",end";
            row++;
            
            try{
                // Handling Invalid Data Rows: Invalid Data Format
                int dataFieldCount = 0;
                for(int i = 0; i < line.length(); i++){
                    if(line.charAt(i) == ','){
                        dataFieldCount++;
                    }
                }
                if (dataFieldCount != CONFIG_COLUMN){
                    throw new InvalidDataFormatException();
                }
                else{
                    configFileLineDataProcessor(line, row, scenarioList);
                }
            }
            catch(InvalidDataFormatException e){
                System.out.println(e.getMessage() + row);
            }
        }
    }

    /**
     * Parsing the Configuration File (Step 2): Process data in each line
     * @param line line data read in from file
     * @param row row number of line which is being processed
     * @param scenarioList list of scenarios
     */
    private void configFileLineDataProcessor(String line, int row, ArrayList<Scenario> scenarioList){
        
        String[] columnData = line.split(",");

        // Save legality value if line starts with "scenario:".
        if(columnData[0].toLowerCase().startsWith("scenario:")){

            String legality = columnData[0].substring("scenario:".length()).toUpperCase();

            try{
                if (legality.equals(Scenario.Legality.GREEN.name())
                        || legality.equals(Scenario.Legality.RED.name())){
                    Scenario newScenario = new Scenario(Scenario.Legality.valueOf(legality));
                    scenarioList.add(newScenario);
                }
                else{
                    // Handling Invalid Data Rows: Invalid Field Values
                    // Default legality set to null
                    Scenario newScenario = new Scenario();
                    scenarioList.add(newScenario);
                    if (!legality.isBlank()){
                        throw new InvalidCharacteristicException();
                    }
                }

            }

            catch (InvalidCharacteristicException e){
                System.out.println(e.getMessage() + row);
            }

        }

        // Save attribute values for characters if line starts with "human" or "animal".
        else if(columnData[0].equalsIgnoreCase("human") || columnData[0].equalsIgnoreCase("animal")){

            // Format and store attribute values from file to variables for convenience.
            int columnIndex = 0;
            String gender = columnData[++columnIndex].toUpperCase();            // Character
            Integer age = null;
            try{
                // Handling Invalid Data Rows: Invalid Number Format
                age = Integer.parseInt(columnData[++columnIndex]);              // Character
            }
            catch (NumberFormatException e){
                System.out.println("WARNING: invalid number format in config file in line " + row);
            }
            String bodyType = columnData[++columnIndex].toUpperCase();          // Character
            String profession = columnData[++columnIndex].toUpperCase();        // Human
            String isPregnant = columnData[++columnIndex].toUpperCase();        // Human
            String isYou = columnData[++columnIndex].toUpperCase();             // Human
            String species = columnData[++columnIndex].toUpperCase();           // Animal
            String isPet = columnData[++columnIndex].toUpperCase();             // Animal
            String role = columnData[++columnIndex].toUpperCase() + "S";        // Character

            // Save attribute values for Human.
            if(columnData[0].equalsIgnoreCase("human")){
                
                // Create a new Human.
                Human newHuman = new Human();
                
                // Gender, Age, BodyType, Role
                charAttrProcessor(gender, age, bodyType, role, newHuman, row, scenarioList);

                // AgeCategory
                if (age != null && age >=0){
                    newHuman.setAgeCategory();
                }

                // Profession
                try{
                    if (profession.equals(Human.Profession.DOCTOR.name())
                            || profession.equals(Human.Profession.CEO.name())
                            || profession.equals(Human.Profession.CRIMINAL.name())
                            || profession.equals(Human.Profession.HOMELESS.name())
                            || profession.equals(Human.Profession.UNEMPLOYED.name())
                            || profession.equals(Human.Profession.TEACHER.name())
                            || profession.equals(Human.Profession.PILOT.name())
                            || profession.equals(Human.Profession.NONE.name())){
                        newHuman.setProfession(Human.Profession.valueOf(profession));
                    }
                    else if (!profession.isBlank()){
                        throw new InvalidCharacteristicException();
                    }
                }
                catch (InvalidCharacteristicException e){
                    System.out.println(e.getMessage() + row);
                }

                // IsPregnant
                try{
                    if (isPregnant.equals(Human.IsPregnant.TRUE.name())
                            || isPregnant.equals(Human.IsPregnant.FALSE.name())){
                        newHuman.setIsPregnant(Human.IsPregnant.valueOf(isPregnant));
                    }
                    else if (!isPregnant.isBlank()){
                        throw new InvalidCharacteristicException();
                    }
                }
                catch (InvalidCharacteristicException e){
                    System.out.println(e.getMessage() + row);
                }

                // IsYou
                try{
                    if (Human.IsYou.valueOf(isYou) == Human.IsYou.TRUE
                            || Human.IsYou.valueOf(isYou) == Human.IsYou.FALSE){
                        // only allow one "YOU" to be added to the current scenario, ignore the subsequent "YOU"
                        if (!scenarioList.get(scenarioList.size()-1).getCharAttr().contains("YOU")){
                            newHuman.setIsYou(Human.IsYou.valueOf(isYou));
                        }
                    }
                    else if (!isYou.isBlank()){
                        throw new InvalidCharacteristicException();
                    }
                }
                catch (InvalidCharacteristicException e){
                    System.out.println(e.getMessage() + row);
                }

            }

            // Save attribute values for Animal.
            else if(columnData[0].equalsIgnoreCase("animal")){

                Animal newAnimal = new Animal();
                
                // Gender, Age, BodyType, Role
                charAttrProcessor(gender, age, bodyType, role, newAnimal, row, scenarioList);

                // Species (no characteristics restriction)
                if (!species.isBlank()){
                    newAnimal.setSpecies(species);
                }

                // IsPet
                try{
                    if (isPet.equals(Animal.IsPet.TRUE.name())
                            || isPet.equals(Animal.IsPet.FALSE.name())){
                        newAnimal.setIsPet(Animal.IsPet.valueOf(isPet));
                    }
                    else if (!isPet.isBlank()){
                        throw new InvalidCharacteristicException();
                    }

                }
                catch (InvalidCharacteristicException e){
                    System.out.println(e.getMessage() + row);
                }
            }
        }
    }

    /**
     * Save attribute values applicable to a Character (i.e. Human or Animal)
     * @param gender gender of character
     * @param age age of character
     * @param bodyType body type of character
     * @param role role of character in scenario
     * @param newChar character (human or animal)
     * @param row row number of line which is being processed
     * @param scenarioList list of scenarios
     */
    private void charAttrProcessor(String gender, Integer age, String bodyType,
                                    String role, Character newChar, int row,
                                    ArrayList<Scenario> scenarioList){

        // Gender
        try{
            if (gender.equals(Character.Gender.FEMALE.name())
                || gender.equals(Character.Gender.MALE.name())
                || gender.equals(Character.Gender.UNKNOWN.name())){
                newChar.setGender(Character.Gender.valueOf(gender));
            }
            else if (!gender.isBlank()){
                throw new InvalidCharacteristicException();
            }
        }
        catch (InvalidCharacteristicException e){
            System.out.println(e.getMessage() + row);
        }
            
        // Age
        try{
            if (age != null && age >=0){
                newChar.setAge(age);
            }
            else if (age != null) {
                throw new InvalidCharacteristicException();
            }
        }
        catch (InvalidCharacteristicException e){
            System.out.println(e.getMessage() + row);
        }

        // BodyType
        try{
            if (bodyType.equals(Character.BodyType.AVERAGE.name())
                    || bodyType.equals(Character.BodyType.ATHLETIC.name())
                    || bodyType.equals(Character.BodyType.OVERWEIGHT.name())
                    || bodyType.equals(Character.BodyType.UNSPECIFIED.name())){
                newChar.setBodyType(Character.BodyType.valueOf(bodyType));
            }
            else if (!bodyType.isBlank()){
                throw new InvalidCharacteristicException();
            }
        }
        catch (InvalidCharacteristicException e){
            System.out.println(e.getMessage() + row);
        }

        // Role
        try{
            if (role.equals(Character.Role.PASSENGERS.name())){
                newChar.setRole(Character.Role.valueOf(role));
                scenarioList.get(scenarioList.size()-1).addPas(newChar);
            }
            else if (role.equals(Character.Role.PEDESTRIANS.name())){
                newChar.setRole(Character.Role.valueOf(role));
                scenarioList.get(scenarioList.size()-1).addPed(newChar);
            }
            else if (!role.equals("S")){
                throw new InvalidCharacteristicException();
            }
        }
        catch (InvalidCharacteristicException e){
            System.out.println(e.getMessage() + row);
        }

    }



    /**
     * 
     * JUDGING SCENARIOS
     * @param userConsent whether user consented to data collection (yes or no)
     * @param configFilePath path to config file
     * @param logFilePath path to data log file
     * @param scenarioList list of scenarios
     * @param keyboard instance of Scanner to take input from user
     * 
     */
    private void judgeScenarios(String userConsent, String configFilePath, String logFilePath,
                                    ArrayList<Scenario> scenarioList, Scanner keyboard){

        Statistic stats = new Statistic();
        int scenarioCount = 0;

        boolean judging = true;
        while(judging){
            
            // Present scenarios
            scenarioCount = presentScenarios(scenarioCount, stats, configFilePath, scenarioList, keyboard);

            // Show statistic
            System.out.println(stats.toString("Statistic"));

            // Save judged scenarios
            if (userConsent.equals("yes")){
                saveJudgedScenarios("user", logFilePath, stats);
            }

            // Repeat or return
            judging = repeatOrReturn(scenarioCount, scenarioList, keyboard);

        }

    }

    /**
     * Collect user consent
     * @param keyboard instance of Scanner to take input from user
     * @return user consent (yes or no)
     */
    private String collectUserConsent(Scanner keyboard){
        System.out.println("Do you consent to have your decisions saved to a file? (yes/no)");
        
        String consent = null;
        boolean validAnswer = false;

        while(!validAnswer){
            try{
                consent = keyboard.nextLine();
                if (consent.equals("yes") || consent.equals("no")){
                    validAnswer = true;
                }
                else{
                    throw new InvalidInputException();
                }
            }
            catch (InvalidInputException e){
                System.out.println(e.getMessage());
            }
        }

        return consent;

    }

    /**
     * Present scenarios
     * @param scenarioCount number of scenarios judged
     * @param stats instance of Statistic to be updated
     * @param configFilePath path to config file
     * @param scenarioList list of scenarios
     * @param keyboard instance of Scanner to take input from user
     * @return number of scenarios judged
     */
    private int presentScenarios(int scenarioCount, Statistic stats, String configFilePath,
                                    ArrayList<Scenario> scenarioList, Scanner keyboard){

        boolean scenarioDisplay = true;
        while (scenarioDisplay){

            Scenario currentScenario; 
            
            // Random Scenario Generation if no config file provided
            if (configFilePath == null){
                currentScenario = new Scenario();
                currentScenario.generateRandomScenario();
            }
            else{
                currentScenario = scenarioList.get(scenarioCount);
            }

            // Print out scenario
            System.out.print(currentScenario.toString());

            boolean validAnswer = false;

            while(!validAnswer){
                String userDecision = keyboard.nextLine();

                if (userDecision.equals("passenger") || userDecision.equals("passengers") || userDecision.equals("1")){
                    stats.save(Decision.PASSENGERS, currentScenario);
                    validAnswer = true;
                }
                else if (userDecision.equals("pedestrian") || userDecision.equals("pedestrians") || userDecision.equals("2")){
                    stats.save(Decision.PEDESTRIANS, currentScenario);
                    validAnswer = true;
                }

            }

            scenarioCount++;

            // End loop every three scenario or when there is no more scenario from file.
            if ((scenarioCount % SCENARIO_DISPLAY_LOOP == 0) || (scenarioCount == scenarioList.size())){
                scenarioDisplay = false;
            }
        }

        return scenarioCount;

    }

    /**
     * Save judged scenarios (by user or algorithm) to log file
     * @param decisionMaker who made the decision (user or algorithm)
     * @param logFilePath path to data log file
     * @param stats instance of Statistic to be updated
     */
    private void saveJudgedScenarios(String decisionMaker, String logFilePath, Statistic stats){

        if (logFilePath == null){
            logFilePath = "ethicalengines.log";
        }
        
        try{
            PrintWriter write = new PrintWriter(new FileOutputStream(logFilePath, true));
            writeData(decisionMaker, write, stats);
            write.close();
        }

        catch(FileNotFoundException e){
            System.out.println("ERROR: could not print results. Target directory does not exist.");
            System.exit(1);
        }
        catch(Exception e){
            System.out.println("ERROR: could not print results. Unknown error occurred.");
            System.exit(1);
        }        


    }

    /**
     * Write data to log file
     * @param decisionMaker who made the decision (user or algorithm)
     * @param write instance of PrintWriter to write to file
     * @param stats instance of Statistic to be updated
     */
    private void writeData(String decisionMaker, PrintWriter write, Statistic stats){
        for (String data: stats.getFileData()){
            write.println(decisionMaker + " " + data.toLowerCase());
        }
        stats.clearFileData();
    }


    /**
     * Repeat or return
     * @param scenarioCount number of scenarios judged
     * @param scenarioList list of scenarios
     * @param keyboard instance of Scanner to take input from user
     * @return continue judging (true) or return to main menu (false)
     */
    private boolean repeatOrReturn(int scenarioCount, ArrayList<Scenario> scenarioList, Scanner keyboard){
        boolean contJudging = true;
        if (scenarioCount !=scenarioList.size()){
            System.out.println("Would you like to continue? (yes/no)");

            boolean validAnswer = false;
            while(!validAnswer){
                String cont = keyboard.nextLine();
                if (cont.equals("yes")){
                    validAnswer = true;
                }
                else if (cont.equals("no")){
                    contJudging = false;
                    validAnswer = true;
                }
            }
        }
        else{
            System.out.println("That's all. Press Enter to return to main menu.");
            pressEnterToCont();
            contJudging = false;
        }
        return contJudging;

    }



    /**
     * 
     * RUN SIMULATION
     * @param configFilePath path to config file
     * @param logFilePath path to data log file
     * @param scenarioList list of scenarios
     * @param keyboard instance of Scanner to take input from user
     * 
     */
    private void runSimulation(String configFilePath, String logFilePath, ArrayList<Scenario> scenarioList, Scanner keyboard){

        Statistic stats = new Statistic();

        Scenario currentScenario;
        int scenarioTotal = 0;

        // Determine total number of scenarios to run.
        if (configFilePath == null){
            System.out.println("How many scenarios should be run?");

            boolean validAnswer = false;
            while(!validAnswer){
                try{
                    scenarioTotal = Integer.parseInt(keyboard.nextLine());
                    validAnswer = true;
                }
                catch(Exception e){
                    System.out.println("Invalid input. How many scenarios should be run?");
                }
            }
        }
        else{
            scenarioTotal = scenarioList.size();
        }

        // Run the total number of scenarios.
        for (int scenarioCount = 0; scenarioCount < scenarioTotal; scenarioCount++){

            if (configFilePath == null){
                currentScenario = new Scenario();
                currentScenario.generateRandomScenario();
            }
            else{
                currentScenario = scenarioList.get(scenarioCount);
            }

            Decision algoDecision = decide(currentScenario);
            stats.save(algoDecision, currentScenario);

        }

        System.out.println(stats.toString("Statistic"));

        saveJudgedScenarios("algo", logFilePath, stats);

        System.out.println("That's all. Press Enter to return to main menu.");
        pressEnterToCont();

    }



    /**
     * 
     * AUDIT FROM HISTORY
     * @param logFilePath path to data log file
     * 
     */
    private void auditHistory(String logFilePath){

        if (logFilePath == null){
            logFilePath = "ethicalengines.log";
        }
        
        try{
            Scanner read = new Scanner(new File(logFilePath));
            Statistic algoStats = new Statistic();
            Statistic userStats = new Statistic();
            logFileDataProcessor(algoStats, userStats, read);
            read.close();

            System.out.println(algoStats.toString("Algorithm Audit") + "\n");
            System.out.println(userStats.toString("User Audit"));
            System.out.println("That's all. Press Enter to return to main menu.");
            pressEnterToCont();
        }

        catch(FileNotFoundException e){
            System.out.println("No history found. Press enter to return to main menu.");
            pressEnterToCont();
        }
        catch(Exception e){
            System.out.println("Unknown error occurred. Press enter to return to main menu.");
            pressEnterToCont();
        }

    }

    /**
     * Process log file data
     * @param algoStats instance of Statistic which has data of scenarios and decisions made by decision algorithm
     * @param userStats instance of Statistic which has data of scenarios and decisions made by user
     * @param read instance of Scanner to read file
     * @throws FileNotFoundException if no data in the file
     */
    private void logFileDataProcessor(Statistic algoStats, Statistic userStats, Scanner read) throws FileNotFoundException {

        if(!read.hasNext()){
            throw new FileNotFoundException();
        }

        while(read.hasNextLine()){
            String line = read.nextLine();
            String [] data = line.split(" ");
            int dataNum = 0;

            if(data[dataNum].equals("algo")){
                algoStats.load(data[++dataNum], data[++dataNum]);
            }
            else if (data[dataNum].equals("user")){
                userStats.load(data[++dataNum], data[++dataNum]);
            }
        }
    }



    /**
     * Allow user to continue after pressing the Enter key
     */
    private void pressEnterToCont(){
        try{System.in.read();}
        catch(Exception e){}
    }



}
