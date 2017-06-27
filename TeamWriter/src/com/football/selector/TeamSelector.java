/**
 * 
 */
package com.football.selector;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author khalid
 * This is simple Class that gets the list of players in XML format and then prints them in html format with each player
 * highlighted with a colour depending on their position
 *
 */
public class TeamSelector {

	
	public Players parseXmlDocumet() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Players.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		return  (Players) jaxbUnmarshaller.unmarshal(new File("src/resource/Players.xml"));
		
	}
	
	/**
	 * A method that recieves the root XML element unmarshalled into a java object and proceeds to print the players list
	 * @param team
	 * @throws IOException
	 */
	
	public void printTeamSheet(Players team) throws IOException {
		StringBuilder html = new StringBuilder();
        html.append( "<!doctype html>\n" );
        html.append( "<html lang='en'>\n" );

        html.append( "<head>\n" );
        html.append( "<meta charset='utf-8'>\n" );
        html.append( "<title>Team Sheet</title>\n" );
        html.append( "</head>\n\n" );

        html.append( "<body>\n" );
        html.append( "<h1>List of Players</h1>\n" );
        // Make a list in HTML
        html.append( "<ul>\n" );
      
        for ( Player player : team.getFootBallPlayers() ) {
        	printPlayer(player, p -> p.isStriker(),html);
        	printPlayer(player, p -> p.isMidfielder(),html);
        	printPlayer(player, p -> p.isDefender(),html);
        }
        html.append( "</ul>\n" );
        html.append( "</body>\n\n" );

        html.append( "</html>" );

        Path file = Paths.get("src/resource/TeamSheet.html");
        BufferedWriter writer = Files.newBufferedWriter(file);
        writer.write(html.toString());
        writer.flush();
        writer.close();
	}
	
	/**
	 * prints each player with colour
	 * @param player
	 * @param checkPosition
	 * @param htmlDoc
	 */
	private void printPlayer(final Player player, Predicate<Player> checkPosition, StringBuilder htmlDoc) {
		
		    if(checkPosition.test(player)) {
		    	htmlDoc.append("<li style=color:" + player.getTsColour() + ">" + player.getName() + "</li>\n");	
		    }
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TeamSelector teamSL = new TeamSelector();
		Players allPlayers = null;
		try {
			allPlayers = teamSL.parseXmlDocumet();
			teamSL.printTeamSheet(allPlayers);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		 
		
	}

}
