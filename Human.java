import java.util.ArrayList;
/**
 * Represents a human character in a scenario
 * COMP90041, Sem2, 2021: Final Project - Ethical Engines
 * @author liching
 */
public class Human extends Character {

    // Constants for maximum age value for age categories
    private static final int BABY_MAX_AGE = 4;
    private static final int CHILD_MAX_AGE = 16;
    private static final int ADULT_MAX_AGE = 68;

    /**
     * Age category of human
     */
    public enum AgeCategory {

        /**
         * Human with an age between 0 and 4
         */
        BABY,
        
        /**
         * Human with an age between 5 and 16
         */
        CHILD,
        
        /**
         * Human with an age between 17 and 68
         */
        ADULT,
        
        /**
         * Human with an age above 68
         */
        SENIOR
        
    }

    /**
     * Profession of human
     */
    public enum Profession {
        
        /**
         * Doctor profession
         */
        DOCTOR,
        
        /**
         * CEO profession
         */
        CEO,
        
        /**
         * Criminal profession
         */
        CRIMINAL,
        
        /**
         * Homeless profession
         */
        HOMELESS,
        
        /**
         * Unemployed profession
         */
        UNEMPLOYED,
        
        /**
         * Teacher profession
         */
        TEACHER,
        
        /**
         * Pilot profession
         */
        PILOT,
        
        /**
         * None as a default option
         */
        NONE
        
    }

    /**
     * Whether human is pregnant
     */
    public enum IsPregnant {
        
        /**
         * Human is pregnant
         */
        TRUE,
        
        /**
         * Human is not pregnant
         */
        FALSE

    }

    /**
     * Whether human represents you
     */
    public enum IsYou {

        /**
         * Human is you
         */
        TRUE,
        
        /**
         * Human is not you
         */
        FALSE
        
    }

    private AgeCategory ageCategory;
    private Profession profession;
    private IsPregnant isPregnant;
    private IsYou isYou;

    /**
     * Construct a human and assign default values of characters in addition to
     * age category, profession, if is pregnant and if is you.
     */
    public Human(){
        super();
        this.ageCategory = AgeCategory.BABY;
        this.profession = Profession.NONE;
        this.isPregnant = IsPregnant.FALSE;
        this.isYou = IsYou.FALSE;
    }

    /**
     * Set age category of human based on human age
     */
    public void setAgeCategory(){
    
        if (getAge() >= 0 && getAge() <= BABY_MAX_AGE){
            this.ageCategory = AgeCategory.BABY;
        }
        else if (getAge() > BABY_MAX_AGE && getAge() <= CHILD_MAX_AGE){
            this.ageCategory = AgeCategory.CHILD;
        }
        else if (getAge() > CHILD_MAX_AGE && getAge() <= ADULT_MAX_AGE){
            this.ageCategory = AgeCategory.ADULT;
        }
        else if (getAge() > ADULT_MAX_AGE){
            this.ageCategory = AgeCategory.SENIOR;
        }

    }

    /**
     * Set profession of human, if human is an adult.
     * @param profession profession of human
     */
    public void setProfession(Profession profession){
        if (this.ageCategory == AgeCategory.ADULT){
            this.profession = profession;
        }
    }

    /**
     * Set if human is pregnant, if human is an adult female
     * @param isPregnant TRUE if is pregnant, otherwise FALSE
     */
    public void setIsPregnant(IsPregnant isPregnant){
        if(getGender() == Gender.FEMALE && this.ageCategory == AgeCategory.ADULT){
            this.isPregnant = isPregnant;
        }
    }

    /**
     * Set if human is you
     * @param isYou TRUE if is you, otherwise FALSE
     */
    public void setIsYou(IsYou isYou){
        this.isYou = isYou;
    }

    /**
     * Get age category of human
     * @return age category of human
     */
    public AgeCategory getAgeCategory(){
        return ageCategory;
    }

    /**
     * Get if human is pregnant
     * @return if human is pregnant
     */
    public IsPregnant getIsPregnant(){
        return isPregnant;
    }

    /**
     * Set attributes of human randomly (except isYou)
     */
    public void generateRandomHuman(){

        // Set random attributes of character
        generateRandomCharacter();

        // Set random age between 0 and 100
        setAge((int)(Math.random() * 101));

        // Set age category based on age
        setAgeCategory();

        // Set random profession based on 8 options
        // however profession can only be updated if human is an adult
        int randomProfession = (int)(Math.random() * 8);
        if(randomProfession == 0){
            setProfession(Profession.DOCTOR);
        }
        else if(randomProfession == 1){
            setProfession(Profession.CEO);
        }
        else if(randomProfession == 2){
            setProfession(Profession.CRIMINAL);
        }
        else if(randomProfession == 3){
            setProfession(Profession.HOMELESS);
        }
        else if(randomProfession == 4){
            setProfession(Profession.UNEMPLOYED);
        }
        else if(randomProfession == 5){
            setProfession(Profession.TEACHER);
        }
        else if(randomProfession == 6){
            setProfession(Profession.PILOT);
        }
        else if(randomProfession == 7){
            setProfession(Profession.NONE);
        }

        // Set if human is pregnant 50/50 for all human
        // however IsPregnant.TRUE can only happen if human is an adult female
        if(Math.random() > 0.5) {
            setIsPregnant(IsPregnant.TRUE);
        } else {
            setIsPregnant(IsPregnant.FALSE);
        }

    }

    /**
     * Get list of human attributes which are relevant to calculating statistics
     * @return list of attributes
     */
    public ArrayList<String> getAttr(){

        ArrayList<String> attributes =  new ArrayList<>();

        attributes.add("HUMAN");
        if (getRole() != null){
            attributes.add(getRole().name());
        }
        if (getGender() != Character.Gender.UNKNOWN){
            attributes.add(getGender().name());
        }
        attributes.add(ageCategory.name());
        if (getBodyType() != Character.BodyType.UNSPECIFIED){
            attributes.add(getBodyType().name());
        }        
        if (profession != Profession.NONE){
            attributes.add(profession.name());
        }
        if (isPregnant == IsPregnant.TRUE){
            attributes.add("PREGNANT");
        }
        if (isYou == IsYou.TRUE){
            attributes.add("YOU");
        }

        return attributes;
        
    }

    /**
     * Description of human with applicable attributes
     * @return attributes for description
     */
    @Override
    public String toString(){

        StringBuilder description = new StringBuilder("-");
    
        if (isYou == IsYou.TRUE){
            description.append(" ");
            description.append("you");
        }
        description.append(" ");
        description.append(getBodyType().name());
        description.append(" ");
        description.append(ageCategory.name());
        if (profession != Profession.NONE){
            description.append(" ");
            description.append(profession.name());
        }
        description.append(" ");
        description.append(getGender().name());
        if (isPregnant == IsPregnant.TRUE){
            description.append(" ");
            description.append("pregnant");
        }

        return description.toString().toLowerCase();

    }


}
