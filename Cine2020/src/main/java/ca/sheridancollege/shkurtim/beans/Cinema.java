package ca.sheridancollege.shkurtim.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cinema {

	private Long cinemaID;
	private String cinemaName;
	private String cinemaStage;
	private int availableSeats;
	private String movie;
	private String[] movies = {"WW1984","Batman","Venom 2","The Bee Movie"};
	
	
	private String properlySanitized, markedSeats, markedGround, doorSigns;
}
