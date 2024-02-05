import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import cards.InvalidPackException;
import cards.Pack;

public class PackTest {
    private Pack pack;
    @BeforeEach
    public void setUp() {
        pack = new Pack(4);
    }
    @Test
    @DisplayName("Ensure pack is loaded correctly when given a valid file.")
    public void testLoadPack_ValidFile() {
        try {
            pack.loadPack("validPack.txt");

        }catch (Exception e){
            fail("File should open fine with correct pack file");
        }

    }
    @Test
    @DisplayName("Ensure a pack containing negative integers throws correct exception.")
    public void testLoadPack_NegativeIntegers() {
        try {
            pack.loadPack("negativePack.txt");
            fail("File should not open an exception should be thrown");
        }catch (InvalidPackException e){

        }catch (Exception e){
            fail("Incorrect exception is thrown.");
        }
    }

    @Test
    @DisplayName("Ensure a pack without 8n rows throws correct exception.")
    public void testLoadPack_IncorrectNumberOfRows() {
        try {
            pack.loadPack("incorrectRowsPack.txt");
            fail("File should not open an exception should be thrown");
        }catch (InvalidPackException e){

        }catch (Exception e){
            fail("Incorrect exception is thrown");
        }
    }
    @Test
    @DisplayName("Ensure FileNotFoundException is thrown when the specified pack file doesn't exist.")
    public void testLoadPack_FileDoesNotExist() {
        try {
            pack.loadPack("thisPackDoesNotExist.txt");
            fail("Exception not thrown");
        }
        catch(FileNotFoundException e){

        } catch (InvalidPackException e) {
            fail("Incorrect exception thrown");
        }
    }

}
