package Models;

public class Team {
	String clubName;
	String abv;
	String hexCode;
	String logoLink;

	public Team(String clubName, String abv, String hexCode, String logoLink) {
	    this.clubName = clubName;
	    this.abv = abv;
	    this.hexCode = hexCode;
	    this.logoLink = logoLink;
	}

	public String getClubName() {
	    return clubName;
	}
	
	public String getAbv() {
	    return abv;
	}
	
	public String getHexCode() {
	    return hexCode;
	}
	
	public String getLogoLink() {
	    return logoLink;
	}
}