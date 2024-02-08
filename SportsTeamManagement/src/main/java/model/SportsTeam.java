/**  
* @Author Shalom Lule - slule@dmacc.edu  
* CIS175 <Spring 2024>
* Feb 7, 2024 
*/
package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class SportsTeam {
	private static long nextTeamId = 1000;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long teamId;
	String city;
	String name;
	int numberOfPlayers;

	public SportsTeam() {
		this.teamId = nextTeamId++;

	}

	public SportsTeam(String name, int numberOfPlayers) {
		super();
		this.name = name;
		this.numberOfPlayers = numberOfPlayers;
		this.teamId = nextTeamId++;

	}

	public static long getNextTeamId() {
		return nextTeamId;
	}

	public Long getTeamId() {
		return teamId;
	}
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

}
