//SubRooms.java
//Class to hold the options a Room can have in a linked list format
//Deals with the text that looks like this
//	a	-	Go to room1
//	b	-	Go to room2

public class SubRooms{
	//Class that holds all the room option nodes
	private class Children{
		String text;	//The text you see when you enter a room and are given a choice
		String tag;		//The tag of the room the option is connected
		Children next;	//Connection to the next room option
	}
	
	private Children front;		//Front of the linked list of options
	private Children end;		//End of the linked list of options
	
	//Returns true if the list is empty
	public boolean isEmpty(){
		return front == null;
	}
	
	//Adds a room option that the main room can go to adding the option description
	//of the sub room
	public void addRoom(String text,String letter){
		Children child = new Children();
		child.text = letter +"  -  "+ text;
		if(isEmpty()){
			front = child;
			end   = child;
		}
		else{
			Children temp = front;
			while(temp.next != null){
				temp = temp.next;
			}
			temp.next = child;
			end        = child;
		}
	}
	
	//Adds a tag reference to another room to the most recently added option
	public void addTag(String tag){
		end.tag = tag;
	}
	
	//Print the all of the options of the room
	//aka a	-	room1
	//	  b	-	room2
	public void printOptions(){
		Children child = front;
		while(child != null){
			System.out.println(child.text);
			child = child.next;
		}
	}
	
	//Searches the options for the matching letter for example
	//	a	-	room1
	//	b	-	room2
	//This method searches the first letter of each option looking for the
	//matching letter than returning the tag associated with that option
	//unless the option could not be found
	public String searchOptions(String input){
		Children child = front;
		String   tag   = null;
		while(child != null){
			if(input.equals(child.text.substring(0,1))){
				System.out.println("["+child.text.substring(6)+"]");
				tag = child.tag;
				return tag;
			}
			else{
				child = child.next;
			}
		}
		return tag;
	}
	
	//Prints room tags of where the room leads to
	public void printSubRooms(){
		Children child = front;
		while(child != null){
			System.out.print(child.tag+" ");
			child = child.next;
		}
	}
}