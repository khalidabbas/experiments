package com.football.selector;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "Players" )
public class Players {
		
	List<FootballPlayer> footBallPlayers;

	public List<FootballPlayer> getFootBallPlayers() {
		return footBallPlayers;
	}

	@XmlElement( name = "FootballPlayer" )
	public void setFootBallPlayers(List<FootballPlayer> footBallPlayers) {
		this.footBallPlayers = footBallPlayers;
	}

}
