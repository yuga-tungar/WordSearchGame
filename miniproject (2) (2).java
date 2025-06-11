package SYDSA;
import java.util.*;
class Bogglesolver2 {
private static final Random random = new Random();
Dictionary dictionary = new Dictionary();
	int level;
	//global declarations
	Scanner in = new Scanner(System.in);
	char[][] grid;		//grid for generation
 	char[][] outputGrid;//output grid
    boolean flag;
    ArrayList<String> words = new ArrayList<>();
    HashSet<String> output = new HashSet<>(); //to avoid duplicates
    HashSet<String> check2 = new HashSet<>();
//select random words from dictionary to generate grid
    ArrayList<String> createArrayList() {
        dictionary.insert();
        words.clear();
        output.clear();
        check2.clear();
        int no_of_words = 0;
        switch (level) {
            case 1: no_of_words = 5; break;
            case 2: no_of_words = 7; break;
            case 3: no_of_words = 10; break;
        }
        for (int i = 0; i < no_of_words; i++) {
            int randomInt1 = random.nextInt(40);
            int a = dictionary.Hashtable[randomInt1].size();
            if (a > 0) {
                int randomInt2 = random.nextInt(a);
                words.add(dictionary.Hashtable[randomInt1].get(randomInt2));
            }
        }
        return words;
    }
//creating a grid to solve
    char[][] createBoggleGrid(int size, List<String> words) {
        char[][] grid = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = '-';
            }
        }
        for (String word : words) {
            placeWordInGrid(grid, word.toUpperCase());
        }
        fillRandomLetters(grid);
        return grid;
    }

    //this function places words at random in the grids by checking if they can be placed at that position
    void placeWordInGrid(char[][] grid, String word) {
        int size = grid.length;
        boolean placed = false;
        while (!placed) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            boolean horizontal = random.nextBoolean();
            if (canPlaceWord(grid, word, row, col, horizontal)) {  //function call to method which checks whether the word can be placed at random location
                for (int i = 0; i < word.length(); i++) {
                    if (horizontal) {
                        grid[row][col + i] = word.charAt(i);
                    } else {
                        grid[row + i][col] = word.charAt(i);
                    }
                }
                placed = true;
            }
        }
    }

    boolean canPlaceWord(char[][] grid, String word, int row, int col, boolean horizontal) {
        int size = grid.length;
        for (int i = 0; i < word.length(); i++) {
            if (horizontal) {
                if (col + i >= size || grid[row][col + i] != '-') {
                    return false;
                }
            } else {
                if (row + i >= size || grid[row + i][col] != '-') {
                    return false;
                }
            }
            // here we check whether the word can be placed it checks that the row index+ word length one by one is less than size similar fir columns
        }
        return true;
    }

    void fillRandomLetters(char[][] grid) {    // This method places a random character where ever it finds an empty space
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '-') {
                    grid[i][j] = (char) ('A' + random.nextInt(26));
                }
            }
        }
    }

    void printGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    void create(int size) {
        if (size == 10) {
            level = 1;
        } else if (size == 13) {
            level = 2;
        } else if (size == 15) {
            level = 3;
        } else {
            return;
        }
        createArrayList();
        grid = createBoggleGrid(size, words);
        printGrid(grid);
    }
// Checking if first alphabet of our words matches with the alphabet in the grid, so that it checks horizontal and vertical only if it matches
    
    boolean searchWord(String word) {
        int size = grid.length;
        String uppercaseWord = word.toUpperCase();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (grid[row][col] == uppercaseWord.charAt(0)) {
                    if (checkHorizontal(grid, uppercaseWord, row, col, outputGrid) || checkVertical(grid, uppercaseWord, row, col, outputGrid)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //check if the word fits horizontally by checking if the word length+column no. is equal to or smaller than size. If it doesn't fit, it returns false.
    //where the alphabet matches, it compares character by character horizontally in the grid, with the alphabets in our word.
    //if any character doesn't match, it returns false
    boolean checkHorizontal(char[][] grid, String word, int row, int col, char[][] outputGrid) {
        int size = grid.length;
        if (col + word.length() > size) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            if (grid[row][col + i] != word.charAt(i)) {
                return false;
            }
        }
        for (int i = 0; i < word.length(); i++) {
            outputGrid[row][col + i] = word.charAt(i);
        }
        output.add(word + " From (" + row + "," + col + ") to (" + row + "," + (col + word.length() - 1) + ")");
        return true;
      //If the word is found, we its characters in respective cells in output grid
    //We add the word found to output arraylist and display its start and end cell.
    }

  //check if the word fits vertically by checking if the word length+row no. is equal to or smaller than size. If it doesn't fit, it returns false
    //where the alphabet matches, it compares character by character vertically in the grid, with the alphabets in our word.
    //if any character doesn't match, it returns false
    boolean checkVertical(char[][] grid, String word, int row, int col, char[][] outputGrid) {
        int size = grid.length;
        if (row + word.length() > size) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            if (grid[row + i][col] != word.charAt(i)) {
                return false;
            }
        }
        for (int i = 0; i < word.length(); i++) {
            outputGrid[row + i][col] = word.charAt(i);
        }
        output.add(word + " From (" + row + "," + col + ") to (" + (row + word.length() - 1) + "," + col + ")");
        return true;
        //If the word is found, we its characters in respective cells in output grid
      //We add the word found to output arraylist and display its start and end cell.
    }

    void printOutputGrid() {
        for (char[] row : outputGrid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        for (String i : output) {
            System.out.println(i);
        }
    }
    //printing output grid

    void search(int size) {
        boolean check;
        outputGrid = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                outputGrid[i][j] = '-'; //initializing output grid with '-'
            }
        }
        //Incase a different word from dictionary is created from random letters generated 
        //We check grid using words from dictionary
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < dictionary.Hashtable[i].size(); j++) {
                String word = dictionary.Hashtable[i].get(j);
                check = searchWord(word);
                if (check) {
                    check2.add(word);
                }
            }
        }
    }
 //When user finds word he enters it and algorithm checks if the word is present
 //Calculates score with total correct words  
    void useranswers() {
        int choice = 1, score = 0, maxscore = 0;
        maxscore = output.size();
        String word = null;
        while (choice != 0) {
            flag = false;
            System.out.println("Enter \n1: Answers\n0: Show Solution");
            choice = in.nextInt();
            if (choice == 0) {
                break;
            }
            try {
            	System.out.println("Enter your answer: ");
                word = in.next();
                if(word.contains("0")) {
                throw new InputMismatchException();
                }
            }
            catch(InputMismatchException e){
            	e.getMessage();
            }
            if (check2.contains(word.toLowerCase())) {
                flag = true;
                score++;
            }
            if (flag) {
                System.out.println("Yeah!! Your answer is correct.");
            } else {
                System.out.println("OOPS!! Your answer is wrong.");
            }
        }
        System.out.println("\nFinal Answer Grid:");
        printOutputGrid();
        System.out.println("Your score: " + score + "/" + maxscore);
    }
}
public class miniproject {
public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	Bogglesolver b = new Bogglesolver();
	int option=0,size;
	do {
	// Levels 
	System.out.println("Select Level \n1: Easy(10X10)\n2: Medium(13X13)\n3: Hard(15X15)\n4: Exit game");	
	option = sc.nextInt();
	switch(option) {
	case 1:
	{
	size=10;
	b.create(size);
	b.search(size);
	b.useranswers();
	break;	
	}
	case 2:
	{
	size=13;
	b.create(size);
	b.search(size);
	b.useranswers();
	break;	
	}
	case 3:
	{
	size=15;
	b.create(size);
	b.search(size);
	b.useranswers();
	break;	
	}
	case 4:
	{
	System.out.println("Thanks for playing");	
	break;
	}
	default:{
	System.out.println("Sorry!! wrong input please recheck");	
	}
	}
	}while(option!=4);
	}		
}

/*
Select Level 
1: Easy(10X10)
2: Medium(13X13)
3: Hard(15X15)
4: Exit game
1
B J J X C P M N J G 
D R A X F M F V W Q 
D V U D F O B G E C 
F R O B C T R C I Z 
A I A R A I O A Q D 
O Y B X M O N C F Y 
B K H L E N Z G V I 
F I S H R A E A T P 
Z G O E A D A V T X 
D X S Y S T E M N Z 
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
system
Yeah!! Your answer is correct.
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
fish
Yeah!! Your answer is correct.
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
money
OOPS!! Your answer is wrong.
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
motion
Yeah!! Your answer is correct.
Enter 
1: Answers
0: Show Solution
0

Final Answer Grid:
- - - - - - - - - - 
- - - - - M - - - - 
- - - - - O B - - - 
- - - - C T R - - - 
- - - - A I O - - - 
- - - - M O N - - - 
- - - - E N Z - - - 
F I S H R - E - - - 
- - - - A - - - - - 
- - S Y S T E M - - 
SYSTEM From (9,2) to (9,7)
FISH From (7,0) to (7,3)
CAMERA From (3,4) to (8,4)
ERA From (6,4) to (8,4)
MOTION From (1,5) to (6,5)
BRONZE From (2,6) to (7,6)
Your score: 3/6
Select Level 
1: Easy(10X10)
2: Medium(13X13)
3: Hard(15X15)
4: Exit game
2
X U K Y H N P C S I L K V 
D U M E T A L E Z V J S E 
L Y E L A B S T R A C T M 
Z Y B A Q X M I M M K B A 
K U O C M M V M W I O W R 
P E O E B S D I N M Q U B 
G S K N E C Q V A U H E L 
R I B X W B D O T D I Z E 
P I I X A D S P A C E D T 
I N J V H S Y X X B K W Z 
G Y C A M B E O X J B S A 
N A D G B R V Z B N C I J 
W V E C I U B J F S A M O 
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
marble
Yeah!! Your answer is correct.
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
metal
Yeah!! Your answer is correct.
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
samosa
OOPS!! Your answer is wrong.
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
Abstract
Yeah!! Your answer is correct.
Enter 
1: Answers
0: Show Solution
0

Final Answer Grid:
- - - - - - - - S I L K - 
- - M E T A L - - - - - - 
- - - L A B S T R A C T M 
- - B A - - - - - - - - A 
- - O C - - - - - - - - R 
- - O E - - - - - - - - B 
- - K - - - - - - - - - L 
- - - - - - D O T - - - E 
- - - - - - S P A C E - - 
- - - - - - - - - - - - - 
- - - - - - - - - - - - - 
- - - - - - - - - - - - - 
- - - - - - - - - - - - - 
SILK From (0,8) to (0,11)
METAL From (1,2) to (1,6)
DOT From (7,6) to (7,8)
MARBLE From (2,12) to (7,12)
SPACE From (8,6) to (8,10)
BOOK From (3,2) to (6,2)
LACE From (2,3) to (5,3)
ABSTRACT From (2,4) to (2,11)
Your score: 3/8
Select Level 
1: Easy(10X10)
2: Medium(13X13)
3: Hard(15X15)
4: Exit game
3
L O K U F J H O O W T L X B S 
K B Q P R T X E I L O P U G B 
J B N Q I I V C F I B E R K W 
I B M Y N F S O I R I B B O N 
O S G G G M Y S U S E A L N W 
X C Q Y E K M Y V G E G B N K 
J X R P N L A S G F O R C E W 
K F T E M T C T W T U J I S P 
V T Q Y X J R E K E Y G F B X 
B U T T O N A M V B S T S G F 
S Q N P N X M P K D D L H B R 
Z R O P E D E I Z Q E D S N C 
L P O Z L P D V E B Q U G O D 
T V C D U N X C L E L D H N Y 
B H M Z O C E A N U C T W Z H 
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
button
Yeah!! Your answer is correct.
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
ocean
Yeah!! Your answer is correct.
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
hello
OOPS!! Your answer is wrong.
Enter 
1: Answers
0: Show Solution
1
Enter your answer: 
ribbon
Yeah!! Your answer is correct.
Enter 
1: Answers
0: Show Solution
0

Final Answer Grid:
- - - - F - - - - - - - - - - 
- - - - R - - E - - - - - - - 
- - - - I - - C F I B E R - - 
- - - - N - - O - R I B B O N 
- - - - G - - S - S E A L - - 
- - - - E - M Y - - - - - - - 
- - - - - - A S - F O R C E - 
- - - - - - C T - - - - - - - 
- - - - - - R E - - - - - - - 
B U T T O N A M - - - - - - - 
- - - - - - M - - - - - - - - 
- R O P E - E - - - - - - - - 
- - - - - - - - - - - - - - - 
- - - - - - - - - - - - - - - 
- - - - O C E A N - - - - - - 
MACRAME From (5,6) to (11,6)
FORCE From (6,9) to (6,13)
ECOSYSTEM From (1,7) to (9,7)
FIBER From (2,8) to (2,12)
BUTTON From (9,0) to (9,5)
SYSTEM From (4,7) to (9,7)
RING From (1,4) to (4,4)
OCEAN From (14,4) to (14,8)
ROPE From (11,1) to (11,4)
FRINGE From (0,4) to (5,4)
RIBBON From (3,9) to (3,14)
SEAL From (4,9) to (4,12)
Your score: 3/12
Select Level 
1: Easy(10X10)
2: Medium(13X13)
3: Hard(15X15)
4: Exit game
4
Thanks for playing
*/