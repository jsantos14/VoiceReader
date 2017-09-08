import java.util.ArrayList;

public class Pronunciation
{
   
   
   public static void main(String[] args)
   {
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
                             };
      
      ArrayList<PhonicCharacter> letters;
      for (int i = 0; i < testString.length; i++)
      {
         System.out.println(testString[i]);
         System.out.print("  ");
         letters = toPhonic(testString[i]);
         System.out.println();
      }
      
      /*for (int i = 0; i < letters.size(); i++)
      {
         System.out.print(letters.get(i) + ".");
      }*/

   }
   
   public static  ArrayList<PhonicCharacter> toPhonic (String input)
   {
      ArrayList<PhonicCharacter> letters = new ArrayList<PhonicCharacter>(input.length());
      ArrayList<ArrayList<PhonicCharacter>> syllables;
      
      boolean wasVowel = false;
      
      for (int i = 0; i < input.length(); i++)
      {
         letters.add(new PhonicCharacter(input.charAt(i)));

         // If Y is the first letter in a Syllable, it is a constenant. Otherwise it is a vowel
         // This only works right now if it is the first letter of the first syllable.
         if (i == 0 && letters.get(i).getChar() == "y") letters.get(i).setVowel(false);
         // Checks to set any vowels as silent so syllables can be formed.
         if (wasVowel && letters.get(i).isVowel())
         {
            letters.get(i).setSilent(true);
         }
         wasVowel = letters.get(i).isVowel();
      }
      // Checks if the last letter is an 'e' in which case it is silent (hopefully)
      if (letters.get(letters.size() - 1).getChar() == "e") letters.get(letters.size() - 1).setSilent(true);
      
      syllables = toSyllables(letters);
      //System.out.println("Syllables: ");
      for (int i = 0; i < syllables.size(); i++)
      {
         for (int j = 0; j < syllables.get(i).size(); j++)
         {
            System.out.print(syllables.get(i).get(j).toString());
         }
         if (i + 1 < syllables.size()) System.out.print("/");
      }
      
      return letters;
   }
   
   private static ArrayList<ArrayList<PhonicCharacter>> toSyllables(ArrayList<PhonicCharacter> letters)
   {
      int syllableCount = 0;  
      ArrayList<ArrayList<PhonicCharacter>> syllables;
      // Checks for any Dipthongs to determine syllable count. 
      String prevChar = null;
      int dipthongCount = 0;
      
      // Accents need to be already determened before this point. ******************************Rule ORDER
      for (int i = 0; i < letters.size(); i++)
      {
         //System.out.println(letters.get(i).toString());
         if (letters.get(i).isVowel() && !letters.get(i).isSilent())
         {
            syllableCount++; // count shit
            //System.out.println("Vowel++");
         }
         if (prevChar == "o" && ( (letters.get(i).getChar() == "i") ||(letters.get(i).getChar() == "w") || (letters.get(i).getChar() == "o")))
         {
            dipthongCount++;
            System.out.println("Dipthong");
         }
         prevChar = letters.get(i).getChar();
      }
      syllableCount = syllableCount - dipthongCount;
      //System.out.println("Syllable Count: " + syllableCount);
      
      syllables = new ArrayList<ArrayList<PhonicCharacter>>(syllableCount);
      
      // Takes syllable count and splits syllables based off of prediction and the location of constenants.
      int[] splitPred = new int[syllableCount - 1];
      for (int i = 0; i < syllableCount - 1; i++)
      {
         splitPred[i] = (int) (letters.size()*(i + 1.0)/(syllableCount));
         //System.out.println(splitPred[i]);
         //System.out.println(letters.get(splitPred[i]).toString());
      }
      ArrayList<Integer> doubleConst = new ArrayList<Integer>();
      ArrayList<Integer> singleConst = new ArrayList<Integer>();
      
      // Can be 'a' because we only care if its a constenant
      PhonicCharacter prevPhonChar = letters.get(0);
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
      
      int[] split = new int[syllableCount - 1];
      
      for (int i = 0; i < syllableCount - 1; i++)
      {  
         if (!doubleConst.isEmpty())
         {
            split[i] = splitPred[i] - doubleConst.get(0);
            for (int j = 0; j < doubleConst.size(); j++)
            {
               if (Math.abs(splitPred[i] - doubleConst.get(j)) < Math.abs(split[i]))
                  split[i] = splitPred[i] - doubleConst.get(j);
            }
            doubleConst.remove((Integer) (splitPred[i] + split[i]));
         }
         else if (!singleConst.isEmpty())
         {
            split[i] = splitPred[i] - singleConst.get(0);
            for (int j = 0; j < singleConst.size(); j++)
            {
               if (Math.abs(splitPred[i] - singleConst.get(j)) < Math.abs(split[i]))
                  split[i] = splitPred[i] - singleConst.get(j);
            }
            singleConst.remove((Integer) (splitPred[i] + split[i]));
         }
      }
      /*for (int i = 0; i < syllableCount - 1; i++)
      {
         System.out.println("Split on: " + split[i]);
      }*/
      
      int pos = 0;
      for (int i = 0; i < syllableCount - 1; i++)
      {
         syllables.add(new ArrayList<PhonicCharacter>(splitPred[i] - split[i]));
         while (pos < splitPred[i] - split[i])
         {
            syllables.get(i).add(letters.get(pos));
            pos++;
         }         
      }
      syllables.add(new ArrayList<PhonicCharacter>(letters.size() - pos));
      while (pos < letters.size())
      {
         syllables.get(syllables.size() - 1).add(letters.get(pos));
         pos++;
      }
      return syllables;
   }
   

}
