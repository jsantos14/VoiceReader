/*
 * Class PhonicCharacter to represent a specific unique sound in the
 * English Language.
 * 
 * These characters have specific traits and methods to assist 
 * in the application of phonic rules.
 * 
 * Still to be added is a way to link a specific sound file to each character
 */
public class PhonicCharacter
{
   // Value to carry the underlying character *************** Neeeds to be changed to a String
   private String _character;
   private boolean _vowel;
   
   // Vowel Specific, without accent: "hat", with accent "hAte"
   private boolean _accent;

   // Vowel Specific, whether a vowel is pronounced or silent
   private boolean _silent;
   
   // An Array to determine if a character is a vowel
   private static final char[] VOWELS = {'a','e','i','o','u','y'};
   
   // An Array to determine if two characters are diagraphs
   private static final String[] DIAGRAPHS = {"ch","sh","th","hw","ng","nk"};
   
   // An Array to determine if two characters are dipthongs
   private static final String[] DIPTHONGS = {"oi","ow","oo"};
   
   // An Array to determine if two characters are rControlledVowels
   private static final String[] R_CONTROLLED_VOWELS = {"ur","ar","or"};
   /*
    * Constructor to make a PhonicCharacter off of a char.
    * A check is performed to determine if the char is a vowel or not.
    * Throws IllegalArgumentException if the given char isn't alphabetical
    */
   public PhonicCharacter(char character) throws IllegalArgumentException
   {
      // All characters are lower case to default
      _character = Character.toString(character).toLowerCase();
      _accent = false;
      _vowel = false;
      _silent = false;
      
      // Only letters allowed. 
      if (!Character.isAlphabetic(character)) throw new IllegalArgumentException();
      
      // Sets the PhonicCharacter to a vowel if it is one
      for (int i = 0; i < VOWELS.length && !_vowel; i++)
      {
         if (character == VOWELS[i]) _vowel = true;
      }
   }
   
   /**
    * Sets a character as the given diagraph, and returns whether or
    * not the letters were accepted diagraphs. 
    * @param diagraph
    * @return
    */
   public boolean setDiagraph(String diagraph)
   {
      boolean done = false;
      
      // Checks that it is a diagraph
      for (int i = 0; i < DIAGRAPHS.length && !done; i++)
      {
         if (diagraph.toLowerCase().equals(DIAGRAPHS[i])) done = true;
      }
      
      if (done)
      {
         _character = diagraph.toLowerCase();
      }
      return done;
   }
   
   /**
    * Sets the letters as an r controlled combo.
    * returns true if was an r controlled vowel. 
    * @param letters
    * @return
    */
   public boolean setRControlled(String letters)
   {
      boolean done = false;
      
      // Checks that it is an r controlled vowel
      for (int i = 0; i < R_CONTROLLED_VOWELS.length && !done; i++)
      {
         if (letters.toLowerCase().equals(R_CONTROLLED_VOWELS[i])) done = true;
      }
      
      if (done)
      {
         _character = letters.toLowerCase();
      }
      return done;
   }
   
   /**
    * Sets a character to a dipthong and returns
    * whether it worked or not. 
    * @param letters
    * @return
    */
   public boolean setDipthong(String letters)
   {
      boolean done = false;
      
      // Checks that it is an dipthong
      for (int i = 0; i < DIPTHONGS.length && !done; i++)
      {
         if (letters.toLowerCase().equals(DIPTHONGS[i])) done = true;
      }
      
      if (done)
      {
         _character = letters.toLowerCase();
      }
      return done;
   }

   /**
    * @return whether the PhonicCharacter is accented.
    * 
    * This is a parameter for vowels only.
    */
   public boolean isAccent()
   {
      return _accent;
   }

   
   /**
    * Sets the PhonicCharacter as accented.
    * @param Boolean value to set as accent
    */
   public void setAccent(boolean accent)
   {
      this._accent = accent;
   }

   /**
    * @return Whether the PhonicCharacter is Silent
    * 
    * This is a parameter for vowels only.
    */
   public boolean isSilent()
   {
      return _silent;
   }
   
   /**
    * Sets the PhonicCharacter as silent.
    * @param Boolelan value to set as accent
    */
   public void setSilent(boolean silent)
   {
      _silent = silent;
   }
   
   /**
    * @return Whether or not the PhonicCharacter is a vowel
    */
   public boolean isVowel()
   {
      return _vowel;
   }

   /**
    * Sets the PhonicCharacter as a vowel
    * @param Boolean value for if the PhonicCharacter is a vowel
    */
   public void setVowel(boolean vowel)
   {
      _vowel = vowel;
   }
   
   /**
    * Returns the underlying Character or characters 
    * @return String of char or chars that make the PhonicCharacter
    */
   public String getChar()
   {
      return _character;
   }
   
   /*
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    * 
    * Gives the underlying characters to the PhonicCharacter.
    * For Debugging's sake capitalizes the vowel if accented,
    * or surrounds in ()'s if silent.
    */
   @Override
   public String toString()
   {
      String ch = _character;
      if (_accent) ch = ch.toUpperCase();
      if (_silent) ch = "(" + ch + ")";
      return ch;
   }
   
   
   
   
}
