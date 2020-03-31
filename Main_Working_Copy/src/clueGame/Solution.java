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
		if (person.equals(((Solution) o).getPerson())
			&& room.equals(((Solution) o).getRoom())
			&& weapon.equals(((Solution) o).getWeapon())) {
			return true;
		} else {
			return false;
		}
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
