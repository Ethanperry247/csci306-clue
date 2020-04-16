// Authors: Caleb Pan, Ethan Perry

package clueGame;

public class Solution {
	
	public String person;
	public String room;
	public String weapon;
	
	public Solution(String person, String room, String weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	@Override
	public boolean equals(Object o) {
		if (person.equals(((Solution) o).getPerson()) // If the person equals the passed in object's person, 
			&& room.equals(((Solution) o).getRoom())  // and room and weapon are also equal, then return true. 
			&& weapon.equals(((Solution) o).getWeapon())) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return person + " " + room + " " + weapon;
	}

	public String getPerson() {
		return person;
	}

	public String getRoom() {
		return room;
	}

	public String getWeapon() {
		return weapon;
	}
	
}
