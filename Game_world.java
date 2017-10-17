//Game_world.java
//Does most of the game functions performing player choices
//changing rooms and restarting the game

public class Game_world{
	
	//////////////////CLASSES///////////////////////////////
	/*	Room class
	*	Holds all the rooms in the game that are available in a binary tree structure
	*/
	private class Room{
		String tag;				//Room tag to switch between rooms
		String description;		//Room description
		SubRooms children;		//Holds all the options a room has available
		int letter;				//Tracks the letter choice for the options
		Room leftRoom;			//Tracks all the rooms made keeping them in a tree
		Room rightRoom;
		
		//Needed a constructor so that a few variables aren't left as null and broken
		public Room(){
			description = "";
			children    = new SubRooms();
		}
	}
	
	/*	Choice class
	*	Holds all player choices in a stack
	*/
	private class Choice{
		Room room;					//Saved rooms
		Choice prevChoice;			//Connects all the player choices together 
										//starting from the top to the bottom
		Choice recentChoice;		//Keeps track of the top of the stack or
										//what room the player was just in
		
		//Adds a room to the stack
		public void push(Room room){
			Choice temp = new Choice();
			temp.room = room;
			if(recentChoice == null){
				recentChoice = temp;
			}
			else{
				temp.prevChoice = recentChoice;
				recentChoice      = temp;
			}
		}
		
		//Returns the room at the top of the stack and returns it
		public Room pop(){
			if(recentChoice == null){
				return null;
			}
			else{
				Choice temp = recentChoice;
				recentChoice = temp.prevChoice;
				return temp.room;
			}
		}
	}
	
	//////////////////VARIABLES////////////////////////////
	
	private Room root;						//Starting room assuming the first room in the file is the start
	private Room current;					//Keeps track of room when making the game world
	//String characters is used to add the letter choice to each option allowing for a lot of options
	private String[] characters	= {"a","b","c","d","e","f","g","h","i","j","k","l","m",
									"n","o","p","s","t","u","v","w","x"};
	private Choice choice = new Choice();	//Keeps track of the player choices saving the most recent room they were in
	
	//////////////////FUNCTIONS////////////////////////////
	
	/*
	*	Starts the game putting the player in the starting room
	*/
	public void StartGame(){
		current = root;
	}
	
	//Restarts the game removing all choices inside the choice stack and resetting the current room to the start
	public void restartGame(){
		current = root;
		while(choice.pop() != null){}
		System.out.println("\nGame has been reset\n");
	}
	
	//Undos a room choice going back to the previous room
	public void undoChoice(){
		Room temp = choice.pop();
		if(temp!=null){
			current = temp;
		}
		else{
			System.out.println("There are no previous choices");
		}
	}
	
	//Print room description and options available
	public void printRoomInfo(){
		System.out.println(current.description+"\n");
		current.children.printOptions();
	}
	
	//Handles the player choice of what room to go to
	public void changeRoom(String input){
		String tag = current.children.searchOptions(input);	//Searches the options and gets the tag of the room the choice matches
		//If no match was found then an invalid input was given
		if(tag == null){
			System.out.println("Invalid option");
			return;
		}
		//Now find the room with the matching tag and add it to the choices stack and
		//set current to the new room
		Room parent;
		Room temp = current;
		current = root;
		while(true){
			parent = current;
			if(current.tag.compareToIgnoreCase(tag)>0){
				current = current.leftRoom;
			}
			else if(current.tag.compareToIgnoreCase(tag)<0){
				current = current.rightRoom;
			}
			else{
				choice.push(temp);
				break;
			}
		}
	}
	
	//Returns true if there are no rooms
	private boolean isEmpty(){
		return root == null;
	}
	
	/*
	*	Adds a room to the game world putting it at the end of the
	*	binary tree of rooms
	*/
	public void addRoom(String key){
		Room room = new Room();
		room.tag  = key;
		if(isEmpty()){
			root 	= room;
			current = room;
		}
		else{
			Room parent;
			current = root;
			while(true){
				parent = current;
				if(current.tag.compareToIgnoreCase(key)>0){
					//Left tree
					current = current.leftRoom;
					if(current == null){
						parent.leftRoom = room;
						current = room;
						break;
					}
				}
				else{
					//Right tree
					current = current.rightRoom;
					if(current == null){
						parent.rightRoom = room;
						current = room;
						break;
					}
				}
			}
		}
	}
	
	/*
	*	Adds a description to the most recently added room
	*/
	public void addDescription(String d){
		if(isEmpty()){
			System.out.println("No rooms generated");
			System.exit(1);
		}
		current.description = current.description + "\n" + d;	//Append string to description
	}
	
	/*
	*	Adds an option to the most recently added room
	*/
	public void addOption(String tag){
		current.children.addRoom(tag,characters[current.letter++]);
	}
	/*
	*	Adds a tag to the most recently added option of the current room being edited
	*	so that the option knows what room to go to when chosen
	*/
	public void addTag(String tag){
		current.children.addTag(tag);
	}
	
	/*
	*	Calls the print method to print out all the info for rooms
	*/
	public void printInfo(){
		print(root);
	}
	
	/*
	*	Method to recursively search the tree and print out the info in alphabetical order
	*/
	public void print(Room current){
		if(current != null){
			print(current.leftRoom);
			System.out.print("Room :"+ current.tag +" Destination tags: ");
			current.children.printSubRooms();								//Goes into the options class and prints out the room options the current room has
			System.out.println();
			print(current.rightRoom);
		}
	}
	
}