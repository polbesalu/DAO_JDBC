package Models;

import java.sql.*;

public class Matches {
	private Date date;
	private Time time;
	private String homeTeam;
	private String awayTeam;
	private int fullTimeHomeGoals;
	private int fullTimeAwayGoals;
	private String fullTimeResult;
	private int halfTimeHomeGoals;
	private int halfTimeAwayGoals;
	private String halfTimeResult;
	private String referee;
	private int homeTeamShots;
	private int awayTeamShots;
	private int homeShotsGoal;
	private int awayShotsGoal;
	private int homeTeamFouls;
	private int awayTeamFouls;
	private int homeTeamCorners;
	private int awayTeamCorners;
	private int homeTeamYellowCards;
	private int awayTeamYellowCards;
	private int homeTeamRedCards;
	private int awayTeamRedCards;
	
	public Matches(Date date, Time time2, String homeTeam, String awayTeam,
            int fullTimeHomeGoals, int fullTimeAwayGoals, String string,
            int halfTimeHomeGoals, int halfTimeAwayGoals, String string2,
            String referee, int homeTeamShots, int awayTeamShots,
            int homeShotsGoal, int awayShotsGoal, int homeTeamFouls, int awayTeamFouls,
            int homeTeamCorners, int awayTeamCorners, int homeTeamYellowCards,
            int awayTeamYellowCards, int homeTeamRedCards, int awayTeamRedCards) {
		this.date = date;
        this.time = time2;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.fullTimeHomeGoals = fullTimeHomeGoals;
        this.fullTimeAwayGoals = fullTimeAwayGoals;
        this.fullTimeResult = string;
        this.halfTimeHomeGoals = halfTimeHomeGoals;
        this.halfTimeAwayGoals = halfTimeAwayGoals;
        this.halfTimeResult = string2;
        this.referee = referee;
        this.homeTeamShots = homeTeamShots;
        this.awayTeamShots = awayTeamShots;
        this.homeShotsGoal = homeShotsGoal;
        this.awayShotsGoal = awayShotsGoal;
        this.homeTeamFouls = homeTeamFouls;
        this.awayTeamFouls = awayTeamFouls;
        this.homeTeamCorners = homeTeamCorners;
        this.awayTeamCorners = awayTeamCorners;
        this.homeTeamYellowCards = homeTeamYellowCards;
        this.awayTeamYellowCards = awayTeamYellowCards;
        this.homeTeamRedCards = homeTeamRedCards;
        this.awayTeamRedCards = awayTeamRedCards;
	}
	
	public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getFullTimeHomeGoals() {
        return fullTimeHomeGoals;
    }

    public int getFullTimeAwayGoals() {
        return fullTimeAwayGoals;
    }

    public String getFullTimeResult() {
        return fullTimeResult;
    }

    public int getHalfTimeHomeGoals() {
        return halfTimeHomeGoals;
    }

    public int getHalfTimeAwayGoals() {
        return halfTimeAwayGoals;
    }

    public String getHalfTimeResult() {
        return halfTimeResult;
    }

    public String getReferee() {
        return referee;
    }

    public int getHomeTeamShots() {
        return homeTeamShots;
    }

    public int getAwayTeamShots() {
        return awayTeamShots;
    }

    public int getHomeShotsGoal() {
        return homeShotsGoal;
    }

    public int getAwayShotsGoal() {
        return awayShotsGoal;
    }

    public int getHomeTeamFouls() {
        return homeTeamFouls;
    }

    public int getAwayTeamFouls() {
        return awayTeamFouls;
    }

    public int getHomeTeamCorners() {
        return homeTeamCorners;
    }

    public int getAwayTeamCorners() {
        return awayTeamCorners;
    }

    public int getHomeTeamYellowCards() {
        return homeTeamYellowCards;
    }

    public int getAwayTeamYellowCards() {
        return awayTeamYellowCards;
    }

    public int getHomeTeamRedCards() {
        return homeTeamRedCards;
    }

    public int getAwayTeamRedCards() {
        return awayTeamRedCards;
    }
    public void getMatchDetails() {
        System.out.println("Match Date: " + getDate());
        System.out.println("Match Time: " + getTime());
        System.out.println("Home Team: " + getHomeTeam());
        System.out.println("Away Team: " + getAwayTeam());
        System.out.println("Full Time Home Goals: " + getFullTimeHomeGoals());
        System.out.println("Full Time Away Goals: " + getFullTimeAwayGoals());
        System.out.println("Full Time Result: " + getFullTimeResult());
        System.out.println("Half Time Home Goals: " + getHalfTimeHomeGoals());
        System.out.println("Half Time Away Goals: " + getHalfTimeAwayGoals());
        System.out.println("Half Time Result: " + getHalfTimeResult());
        System.out.println("Referee: " + getReferee());
        System.out.println("Home Team Shots: " + getHomeTeamShots());
        System.out.println("Away Team Shots: " + getAwayTeamShots());
        System.out.println("Home Shots on Goal: " + getHomeShotsGoal());
        System.out.println("Away Shots on Goal: " + getAwayShotsGoal());
        System.out.println("Home Team Fouls: " + getHomeTeamFouls());
        System.out.println("Away Team Fouls: " + getAwayTeamFouls());
        System.out.println("Home Team Corners: " + getHomeTeamCorners());
        System.out.println("Away Team Corners: " + getAwayTeamCorners());
        System.out.println("Home Team Yellow Cards: " + getHomeTeamYellowCards());
        System.out.println("Away Team Yellow Cards: " + getAwayTeamYellowCards());
        System.out.println("Home Team Red Cards: " + getHomeTeamRedCards());
        System.out.println("Away Team Red Cards: " + getAwayTeamRedCards());
    }
}
