//cyoa.java
//Main program file that parses the file and loads the menu and stuff
//allowing for player choice
import java.io.*;
import java.util.*;

class cyoa{
	public static void main(String args[])throws Exception{
		//Create the game world
		Game_world world = new Game_world();
		//Check for only one argument added and read in that file
		if(args.length != 1){
			System.out.println("File Usage: java cyoa text_adventure_file");
			System.exit(1);
		}
		else{
			read(args[0],world);					//Creates the game world from the file being read
		}
		//GAME START
		world.StartGame();							//Load the first room
		Scanner scan = new Scanner(System.in);		//Player input
		
		while(true){
		//player commands:
			//a-1 Standard choices defined by the adventure
			//r   Restart the adventure in the first room lose save
			//q   Quit the game
			//y   Show info about the adventure. Prints one room 
				  //per line including tag of the room then tags for destinations 
				  //of all possible options from that room
			//z   Undo the previous choice can be done multiple times
			world.printRoomInfo();
			String input = scan.nextLine();
			if(input.matches("^\\s*$")) continue;
			//If the input is not one letter then you aren't doing it right
			if(input.length() > 1){
				System.out.println("Invalid Choice");
				continue;
			}
			switch(input){
				case "r":
					world.restartGame();
					break;
				case "q": 
					System.exit(0);
					break;
				case "y": 
					world.printInfo();
					break;
				case "z": 
					world.undoChoice();
					break;
				default: 
					world.changeRoom(input);
					break;
			}
		}
	}
	
	//Reading in adventure file format
			//r Add a new blank room with a tag given by the contents. The room has no
			//	options when created.
			//d   Add a line of description to the most recently added room. If no room has
			//	been added then this command generates an error.
			//o   Add a new option to the most recent room. The content is the text of the
			//	option that will be displayed to the player.
			//t   Update the destination tag of the most recently added option to the contents.
			//	The tag must match the tag of a room that appears somewhere in the input file
			// (that room may occur later in the file than this command).
	//Method to read in the adventure file and load everything into the game world
	public static void read(String file,Game_world world){
		try{
			FileReader fil = new FileReader(file);
			Scanner scan = new Scanner(fil);
			while(scan.hasNextLine()){
				String input = scan.nextLine();
				if(input.matches("^\\s*$")) continue;
				char command = input.charAt(0);
				//Get the command located as the first char then everything after is
				//loaded into the game world according to te command given
				switch(command){
					case 'r':
						world.addRoom(input.substring(2));
						break;
					case 'd':
						world.addDescription(input.substring(2));
						break;
					case 'o':
						world.addOption(input.substring(2));
						break;
					case 't':
						world.addTag(input.substring(2));
						break;
					default:
						System.out.println("Invalid Command");
						System.exit(1);
				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("File "+file+" not found");
			System.exit(1);
		}
	}
}