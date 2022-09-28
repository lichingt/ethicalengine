import java.util.ArrayList;
/**
 * Represents an animal character in a scenario
 * COMP90041, Sem2, 2021: Final Project - Ethical Engines
 * @author liching
 */
public class Animal extends Character {

    /**
     * Species of animal when generating random animal attributes
     */
    public enum Species {
        
        /**
         * Cat species
         */
        CAT,
        
        /**
         * Dog species
         */
        DOG,
        
        /**
         * Bird species
         */
        BIRD
    
    }

    /**
     * Whether animal is pet
     */
    public enum IsPet {
        
        /**
         * Animal is pet
         */
        TRUE,

        /**
         * Animal is not pet
         */
        FALSE
        
    }

    private String species;
    private IsPet isPet;

    /**
     * Construct an animal and assign default values of characters in addition to
     * species and if is pet.
     */
    public Animal(){
        super();
        this.species = "UNKNOWN";
        this.isPet = IsPet.FALSE;
    }

    /**
     * Set species of animal
     * @param species species of animal
     */
    public void setSpecies(String species){
        this.species = species;
    }

    /**
     * Set if animal is pet
     * @param isPet TRUE if is you, otherwise FALSE
     */
    public void setIsPet(IsPet isPet){
        this.isPet = isPet;
    }

    /**
     * Set attributes of animal randomly
     */
    public void generateRandomAnimal(){
        
        // Set random attributes of character
        generateRandomCharacter();

        // Set random age between 0 and 20
        setAge((int)(Math.random() * 21));

        // Set random species based on 3 options
        int randomSpecies = (int)(Math.random() * 3);
        if(randomSpecies == 0){
            setSpecies(Species.CAT.name());
        }
        else if(randomSpecies == 1){
            setSpecies(Species.DOG.name());
        }
        else if(randomSpecies == 2){
            setSpecies(Species.BIRD.name());
        }

        // Set if animal is pet 50/50
        if(Math.random() > 0.5) {
            setIsPet(IsPet.TRUE);
        } else {
            setIsPet(IsPet.FALSE);
        }

    }

    /**
     * Get list of animal's attributes which are relevant to calculating statistics
     * @return list of attributes
     */
    public ArrayList<String> getAttr(){

        ArrayList<String> attributes =  new ArrayList<>();

        attributes.add("ANIMAL");
        if (getRole() != null){
            attributes.add(getRole().name());
        }
        if (!species.equals("UNKNOWN")){
            attributes.add(species);
        }
        if (isPet == IsPet.TRUE){
            attributes.add("PET");
        }        

        return attributes;
        
    }

    /**
     * Description of animal with applicable attributes
     * @return attributes for description
     */
    @Override
    public String toString(){

        StringBuilder description = new StringBuilder("- ");
        
        description.append(species);
        if (this.isPet == IsPet.TRUE){
            description.append(" is pet");
        }

        return description.toString().toLowerCase();

    }

}
