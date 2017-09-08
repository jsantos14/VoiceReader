
public class PhonicCharacter
{
   private char _character;
   private boolean _accent;
   private boolean _vowel;
   private boolean _silent;
   
   private static final char[] VOWELS = {'a','e','i','o','u','y'};
   
   public PhonicCharacter(char character) throws IllegalArgumentException
   {
      _character = Character.toLowerCase(character);
      _accent = false;
      _vowel = false;
      _silent = false;
      
      // Temporarily at least have only letters allowed. 
      if (!Character.isAlphabetic(character)) throw new IllegalArgumentException();
      
      for (int i = 0; i < VOWELS.length && !_vowel; i++)
      {
         if (_character == VOWELS[i]) _vowel = true;
      }
   }

   /**
    * @return the _isAccent
    */
   public boolean isAccent()
   {
      return _accent;
   }

   
   /**
    * @param _isAccent the _isAccent to set
    */
   public void setAccent(boolean accent)
   {
      this._accent = accent;
   }

   public boolean isSilent()
   {
      return _silent;
   }
   
   public void setSilent(boolean silent)
   {
      _silent = silent;
   }
   
   /**
    * @return the _type
    */
   public boolean isVowel()
   {
      return _vowel;
   }

   public void setVowel(boolean vowel)
   {
      _vowel = vowel;
   }
   public char getChar()
   {
      return _character;
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString()
   {
      String ch = Character.toString(_character);
      if (_accent) ch = ch.toUpperCase();
      if (_silent) ch = "(" + ch + ")";
      return ch;
   }


   

}
