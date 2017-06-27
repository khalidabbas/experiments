package com.football.selector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType( propOrder = { "name", "position", "tsColour"})
@XmlRootElement( name = "FootballPlayer" )
public class FootballPlayer implements Player {

	
	private final static String FOOTBALLSTRIKER = "striker";
	private final static String FOOTBALLMIDFIELDER = "midfielder";
	private final static String FOOTBALLDEFENDER = "defender";
	
	private String position;
	private String name;
	private String tsColour;
	
	public String getPosition() {
		return position;
	}
	@XmlElement(name = "position")
	public void setPosition(String position) {
		this.position = position;
	}
	@Override
	public String getName() {
		return name;
	}
	@XmlElement(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getTsColour() {
		return tsColour;
	}
	@XmlElement(name = "tsColour")
	public void setTsColour(String teamSheetColor) {
		this.tsColour = teamSheetColor;
	}

	@Override
	public Boolean isStriker() {		
		return position.equalsIgnoreCase(FOOTBALLSTRIKER);
	}

	@Override
	public Boolean isMidfielder() {		
		return  position.equalsIgnoreCase(FOOTBALLMIDFIELDER);
	}

	@Override
	public Boolean isDefender() {		
		return position.equalsIgnoreCase(FOOTBALLDEFENDER);
	}

}
