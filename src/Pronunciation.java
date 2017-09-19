import java.io.File;
import java.util.ArrayList;

/**
 * Class Pronunciation to simply translate a given text string into an
 * array of PhonicCharacters.
 * 
 * @author Jonathan
 *
 */
public class Pronunciation
{
   private final static String[] FIRST_SYLLABLE_EXCEPTIONS = {
                                                       "de",
                                                       "re",
                                                       "ex",
                                                       "in",
                                                       "po",
                                                       "pro",
                                                       "a"
                                                       };
  
   /**
    * Currently runs a test on a series of words to see the output 
    * PhonicCharacters.
    * @param args
    */
   public static void main(String[] args)
   {
      // The test words
      String[] testString = {
                             "happening",
                             "pound",
                             "hint",
                             "carpet",
                             "album",
                             "libel",
                             "fiction",
                             "possession",
                             "chosen",
                             "hate"
                             };
      
      // Array of resultant PhonicCharacters after translation
      ArrayList<PhonicCharacter> letters;
      
      // As a test simply prints the word desired and the resulting
      // PhonicCharacters on an indented second line.
      for (int i = 0; i < testString.length; i++)
      {
         System.out.println(testString[i]);
         System.out.print("  ");
         letters = toPhonic(testString[i]);
         /*
          * Completely for Debug, prints off the syllables with a slash in the middle of splits.
          * This is in this location instead of int the main because syllables aren't important for the end product. 
          */
         for (int j = 0; j < letters.size(); j++)
         {
             System.out.print(letters.get(j).toString() + " ");
         }
         System.out.println();
      }
   }
   
   public static ArrayList<File> wordToFile(String word)
   {
      // filepath to add to the desired file to get universal path
      String filePath = System.getProperty("user.home") + "/IdeaProjects/VoiceReader/src/Sounds/";

      ArrayList<PhonicCharacter> phonChars;
      ArrayList<File> files = new ArrayList<>(word.length());
  
      phonChars = toPhonic(word);
      for (PhonicCharacter phonChar : phonChars)
      {
         String fileName = filePath + phonChar.toString() + ".wav";
         files.add(new File(fileName));
      }

      return files;
   }
   
   /**
    * Takes an input String of alphabetical characters and after a brief
    * bit of witchcraft, produces an arrayList of PhonicCharacters.
    * 
    * @param input as the word to translate.
    * @return an ArrayList of the translated PhonicCharacters.
    */
   public static  ArrayList<PhonicCharacter> toPhonic (String input)
   {
      // The ArrayList of PhonicCharacters to store the converted characters.
      ArrayList<PhonicCharacter> letters = new ArrayList<PhonicCharacter>(input.length());
      
      // The ArrayList with nested syllable ArrayLists.
      ArrayList<ArrayList<PhonicCharacter>> syllables;
      
      // A quick variable to help set letters as silent.
      boolean wasVowel = false;
      
      // Loop which iterates through the letters and apply's rules for silent E's
      // and determines whether Y's should be considered a vowel or constenant.
      for (int i = 0; i < input.length(); i++)
      {
         // Actually adds the PhonicCharacters into the letters ArrayList. 
         letters.add(new PhonicCharacter(input.charAt(i)));

         // If Y is the first letter in a Syllable, it is a constenant. Otherwise it is a vowel
         // This only works right now if it is the first letter of the first syllable.
         if (i == 0 && letters.get(i).getChar() == "y") letters.get(i).setVowel(false);
         
         // Checks to set any vowels as silent so syllables can be formed.
         if (wasVowel && letters.get(i).isVowel())
         {
            if (!letters.get(i - 1).getChar().equals("i")) letters.get(i).setSilent(true);
            else letters.get(i).setAccent(true);
            
            // Sets the previous letter accented *************************************************Could be a bad interp for rules
            letters.get(i - 1).setAccent(true);
         }
         // Sets the previous character to the current for the next iteration.
         wasVowel = letters.get(i).isVowel();
      }
      // Checks if the last letter is an 'e' in which case it is silent (hopefully)
      boolean done = false;
      int index = letters.size() - 1;
      if (letters.get(index).getChar().equals("e"))
      {
         letters.get(index).setSilent(true);
         while (!done)
         {
            index--;
            if (index < 0) done = true;
            if (letters.get(index).isVowel())
            {
               letters.get(index).setAccent(true);
               done = true;
            }
         }
      }
      
      // Calls the toSyllables method to now apply rules which require syllables. 
      syllables = toSyllables(letters);
      
      syllables = accentRules(syllables);
      letters = condense(syllables);      
      return letters;
   }
   
   /**
    * 
    * @param letters
    * @return
    */
   private static ArrayList<PhonicCharacter> condense(ArrayList<ArrayList<PhonicCharacter>> letters)
   {
      // An ArrayList of the syllables. 
      ArrayList<PhonicCharacter> phonChars = new ArrayList<PhonicCharacter>();
      
      // variable to see whether prev char was constenant
      PhonicCharacter prevChar;
      
      for (int i = 0; i < letters.size(); i++)
      {
         for (int j = 0; j < letters.get(i).size(); j++)
         {
            if (!letters.get(i).get(j).isSilent()) phonChars.add(letters.get(i).get(j));
         }
      }
      prevChar = phonChars.get(0);
      for (int i = 1; i < phonChars.size(); i++)
      {
         if (!phonChars.get(i).isVowel() && !prevChar.isVowel())
         {
            // Activates if Diagraph detected
            if (phonChars.get(i-1).setDiagraph(phonChars.get(i-1).getChar() + phonChars.get(i).getChar()) || phonChars.get(i-1).getChar().equals(phonChars.get(i).getChar()))
            {
               phonChars.remove(i);
               // i--;?
            }
         }
         else if (prevChar.isVowel() && phonChars.get(i-1).setRControlled(phonChars.get(i-1).getChar() + phonChars.get(i).getChar()))
         {
            phonChars.remove(i);
            // i--;?
         }
         else if (prevChar.getChar().equals("o") && phonChars.get(i-1).setDipthong(phonChars.get(i-1).getChar() + phonChars.get(i).getChar()))
         {
            phonChars.remove(i);
            // i--;?
         }
      }
      
      return phonChars;
   }
   /**
    * Takes an ArrayList of unanalyzed (with only the determination between vowels
    * and not vowels set) and breaks this ArrayList into a new ArrayList with 
    * nested ArrayLists for each Syllable. In the process, vowels are also 
    * determined to be silent or not.
    * 
    * @param letters ArrayList of Phonic Characters to be split into syllables.
    * @return An ArrayList of nested syllables which are ArrayLists of PhonicCharacters.
    */
   private static ArrayList<ArrayList<PhonicCharacter>> toSyllables(ArrayList<PhonicCharacter> letters)
   {
      // Variable counting syllables.
      int syllableCount = 0;  
      
      // An ArrayList of the syllables. 
      ArrayList<ArrayList<PhonicCharacter>> syllables;
      
      // Used for finding Dipthongs.
      String prevChar = null;
      int dipthongCount = 0;
      
      // Determines the syllable count by iterating across the letters and 
      // following the formula: Syllable# = vowel# - silentVowel# - dipthong#
      for (int i = 0; i < letters.size(); i++)
      {
         // Sometimes "c" is read as "s"
         if (prevChar == "c" && (letters.get(i).getChar() == "e" 
                                 || letters.get(i).getChar() == "i" 
                                 || letters.get(i).getChar() == "y"))
         {
            letters.set(i - 1, new PhonicCharacter('s'));
         }
         
         
         // Simply doesn't count a vowel if silent, resulting in the 
         // - silentVowel# part of the equation.
         if (letters.get(i).isVowel() && !letters.get(i).isSilent())
         {
            syllableCount++; // Count shit
         }
         
         // Dipthongs!!!
         if (prevChar == "o" && ( (letters.get(i).getChar() == "i") 
                                 ||(letters.get(i).getChar() == "w") 
                                 || (letters.get(i).getChar() == "o")))
         {
            dipthongCount++;
         }
         prevChar = letters.get(i).getChar();
      }
      // The equation resulting in the final syllable count
      syllableCount = syllableCount - dipthongCount;
      
      syllables = new ArrayList<ArrayList<PhonicCharacter>>(syllableCount);
      
      // Takes syllable count and splits syllables based off of prediction and the 
      // location of constenants. splitPred is the place immediately after the 
      // predicted split. EX:
      //    The word HAPPENING is predicted to be splitted like so,
      //    with the 0 and 1 being values for splitPred
      //
      //        H A P/P E N/I N G
      //              0     1
      // 
      int[] splitPred = new int[syllableCount - 1];
      
      // For each syllable split, loads the predicted syllable position
      for (int i = 0; i < syllableCount - 1; i++)
      {
         splitPred[i] = (int) (letters.size()*(i + 1.0)/(syllableCount));
      }
      
      // Two ArrayLists to fill with the positions of 
      // 1. Double constentants (between which a syllable splits)
      // 2. Single constenants (before which a syllable splits)
      // Splitting prioritizes on the closest 1. Then 2 if 1 doesn't exist
      ArrayList<Integer> doubleConst = new ArrayList<Integer>();
      ArrayList<Integer> singleConst = new ArrayList<Integer>();
      
      // Starts at the second letter if the word is long enough, because
      // a syllable will never split on the first letter, and is dependant
      // upon two immediate characters.
      PhonicCharacter prevPhonChar = letters.get(0);
      
      // Iterates across the letters and finds constentant positions,
      // adding occurrences into the doubleConst and singleConst lists.
      for (int i = 1; i < letters.size(); i++)
      {
         if (!letters.get(i).isVowel())
         {
            if (!prevPhonChar.isVowel())
            {
               doubleConst.add(i);
            }
            else singleConst.add(i);
         }
         prevChar = letters.get(i).getChar();
      }
      
      // Where the split actually occurs, given as a shift
      // from the predicted location.
      // EX: if the prediciton was 4, and the split was found to 
      // actually be at 3, the value in split would be -1
      int[] split = new int[syllableCount - 1];
      
      // Iterates through the number of syllables and predicted splits
      // to find the closest constentant splits. Prioritizing on doubles.
      for (int i = 0; i < syllableCount - 1; i++)
      {  
         // If we have double constenants, check if this constentant occurance
         // is closest to the predicted location. Aftewards, this is removed 
         // from the set for the next iteration.
         if (!doubleConst.isEmpty())
         {
            // calculates the split initially from the first position.
            split[i] = splitPred[i] - doubleConst.get(0);
            
            // Iterates through found double constentants. 
            for (int j = 0; j < doubleConst.size(); j++)
            {
               // If the current constenant is closer to the target predicted,
               // that new offset becomes the value in split[]. 
               if (Math.abs(splitPred[i] - doubleConst.get(j)) < Math.abs(split[i]))
                  split[i] = splitPred[i] - doubleConst.get(j);
            }
            // The selected value is removed from the double value array for
            // the next itteration.
            doubleConst.remove((Integer) (splitPred[i] + split[i]));
         }
         // Performs exactly the same for single constenants. It may be the case
         // that the first double constenant is for the second syllable split, in which case
         // I would need to add a target swapping clause or something. Potential errors here ******************
         else if (!singleConst.isEmpty())
         {
            split[i] = splitPred[i] - singleConst.get(0);
            
            // Iterates through single constenants. 
            for (int j = 0; j < singleConst.size(); j++)
            {
               // If the current constenant is closer to the target predicted,
               // that new offset becomes the value in split[].
               if (Math.abs(splitPred[i] - singleConst.get(j)) < Math.abs(split[i]))
                  split[i] = splitPred[i] - singleConst.get(j);
            }
            singleConst.remove((Integer) (splitPred[i] + split[i]));
         }
      }

      // Adds the letters into the nested syllable ArrayLists using the 
      // value pos which walks along all letters. 
      int pos = 0;
      // Splits off the requisit letters before each syllable splits
      // and puts them into the ArrayLists for each syllable. 
      for (int i = 0; i < syllableCount - 1; i++)
      {
         syllables.add(new ArrayList<PhonicCharacter>(splitPred[i] - split[i]));
         while (pos < splitPred[i] - split[i])
         {
            syllables.get(i).add(letters.get(pos));
            pos++;
         }         
      }
      // Adds the final letters after the last split to the last syllable.
      // THis could probably be cleverly worked into the above for loop. Expand? *************************
      syllables.add(new ArrayList<PhonicCharacter>(letters.size() - pos));
      while (pos < letters.size())
      {
         syllables.get(syllables.size() - 1).add(letters.get(pos));
         pos++;
      }
      return syllables;
   }
   
   private static ArrayList<ArrayList<PhonicCharacter>> accentRules(ArrayList<ArrayList<PhonicCharacter>> syllables)
   {
      // First syllable of multiple usually accented. Unless first syllable of a specified list.
      if (syllables.size() > 1)
      {
         boolean done = false;
         StringBuilder firstSyllable = new StringBuilder(syllables.get(0).size());
         for (PhonicCharacter character : syllables.get(0))
         {
            firstSyllable.append(character.getChar());
         }
         for (int i = 0; i < syllables.get(0).size() && !done; i++)
         {
            if (firstSyllable.toString().equals(FIRST_SYLLABLE_EXCEPTIONS[i]))
            {
               done = true;
            }
         }
         for (int i = 0; i < syllables.get(0).size() && !done; i++)
         {
            if (syllables.get(0).get(i).isVowel())
            {
               syllables.get(0).get(i).setVowel(true);
               done = true;
            }
         }
      }
      
      return syllables;
   }

}
