package cards;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Pack extends CardDeck{
    
    private int n;
    public Pack(int n){            

        super(0);
        
        this.n = n;
    }
    public void loadPack(String fileLocation) throws FileNotFoundException, InvalidPackException{
        // check if file exists
        //validate all inputs are non-negative integers
        //validate number of rows is 8n.
        
        File f = new File(fileLocation);
        try(Scanner scan= new Scanner(f)){
            try{
            
                while(scan.hasNext()){
                    int num = scan.nextInt();
                    
                    
                    if(scan.hasNextLine()){ //To handle if the pack ends in a new line character or not.
                        scan.nextLine();
                    }
                    if(num < 1){
                        throw new InvalidPackException(); //Create new more specific exception class - InvalidPackException??
                    }
                    
                    this.add(new Card(num));
                }
                if(this.getSize() != 8 * this.n){
                    throw new InvalidPackException();
                }
            } catch (Exception e) { //If there is an error reading the file
                throw new InvalidPackException();
            }

        }
    }
}
