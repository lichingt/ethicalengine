import java.util.ArrayList;
/**
 * Represents a character in a scenario
 * COMP90041, Sem2, 2021: Final Project - Ethical Engines
 * @author liching
 */
public class Character {

    /**
     * Role of character
     */
    public enum Role {
        
        /**
         * A part of the passengers group
         */
        PASSENGERS,

        /**
         * A part of the pedestrians group
         */
        PEDESTRIANS
    
    }

    /**
     * Gender of character
     */
    public enum Gender {
        
        /**
         * Female gender
         */
        FEMALE,
        
        /**
         * Male gender
         */
        MALE,
        
        /**
         * Unknown gender as a default option
         */
        UNKNOWN
        
    }

    /**
     * Body type of character
     */
    public enum BodyType {
        
        /**
         * Average body type
         */
        AVERAGE,
        
        /**
         * Athletic body type
         */
        ATHLETIC,
        
        /**
         * Overweight body type
         */
        OVERWEIGHT,
        
        /**
         * Unspecified body type as a default option
         */
        UNSPECIFIED
        
    }

    private Role role;
    private Gender gender;
    private int age;
    private BodyType bodyType;
    
    /**
     * Construct a character and assign default values for role, gender, age and body type.
     */
    public Character(){
        this.role = null;
        this.gender = Gender.UNKNOWN;
        this.age = 0;
        this.bodyType = BodyType.UNSPECIFIED;
    }

    /**
     * Set role of character
     * @param role role of character in scenario
     */
    public void setRole(Role role){
        this.role = role;
    }

    /**
     * Set gender of character
     * @param gender gender of character
     */
    public void setGender(Gender gender){
        this.gender = gender;
    }

    /**
     * Set age of character, if it is a positive value
     * @param age age of character
     */
    public void setAge(int age){
        if (age >= 0){
            this.age = age;
        }
    }

    /**
     * Set body type of character
     * @param bodyType body type of character
     */
    public void setBodyType(BodyType bodyType){
        this.bodyType = bodyType;
    }

    /**
     * Get role of character
     * @return role of character
     */
    public Role getRole(){
        return role;
    }

    /**
     * Get gender of character
     * @return gender of character
     */
    public Gender getGender(){
        return gender;
    }

    /**
     * Get age of character
     * @return age of character
     */
    public int getAge(){
        return age;
    }

    /**
     * Get body type of character
     * @return body type of character
     */
    public BodyType getBodyType(){
        return bodyType;
    }

    /**
     * Set attributes of character randomly
     */
    public void generateRandomCharacter(){

        if(Math.random() > 0.5) {
            setRole(Role.PASSENGERS);
        } else {
            setRole(Role.PEDESTRIANS);
        }

        if(Math.random() > 0.5) {
            setGender(Gender.FEMALE);
        } else {
            setGender(Gender.MALE);
        }

        int randomBodyType = (int)(Math.random() * 3);
        if(randomBodyType == 0){
            setBodyType(BodyType.AVERAGE);
        }
        else if(randomBodyType == 1){
            setBodyType(BodyType.ATHLETIC);
        }
        else if(randomBodyType == 2){
            setBodyType(BodyType.OVERWEIGHT);
        }

    }

    /**
     * Get list of character's attributes which are relevant to calculating statistics
     * @return attributes for description
     */
    public ArrayList<String> getAttr(){
        return null;
    }

}
