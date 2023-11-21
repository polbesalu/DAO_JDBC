package DAO;

import java.*;
import java.sql.Date;
import java.util.ArrayList;

import Models.*;

public interface IDAOManager extends AutoCloseable{
	
	public boolean AddTeam(Team oneTeam);
	
	public void ImportTeams(String fileTeams);
	
	public Team GetTeam(String teamAbbreviation);
	
	public String GetTeamAbbreviation(String teamName);
	
	public boolean AddMatch(Matches oneMatch);
	
	public void ImportMatches(String fileMatches);
	
	public Matches GetMatch(Date matchDay, Team home, Team away);
	
	public int HomeGoals();
	
	public int AwayGoals();
	
	public ArrayList<Matches> MatchesOfTeam(Team oneTeam);
	
	public int RedCards(Team oneTeam);
	
	public ArrayList<Team> TopRedCards();
}
