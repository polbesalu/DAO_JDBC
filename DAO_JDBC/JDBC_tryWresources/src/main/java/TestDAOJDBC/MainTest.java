package TestDAOJDBC;

import Models.Matches;
import Models.Team;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import DAO.DAOManagerJDBCImpl;

public class MainTest {

    public static void main(String[] args) {
        try {
            DAOManagerJDBCImpl daoManager = new DAOManagerJDBCImpl();

            // Import teams from a CSV file
            daoManager.ImportTeams("clubs.csv");

            // Import matches from a CSV file
            daoManager.ImportMatches("results.csv");

            // Add a team
            Team teamToAdd = new Team("TestTeam", "TT", "#FFFFFF", "logo.png");
            boolean teamAdded = daoManager.AddTeam(teamToAdd);
            System.out.println("Team added: " + teamAdded);


            // Get a team by abbreviation
            Team retrievedTeam = daoManager.GetTeam("TT");
            System.out.println("Retrieved Team: " + retrievedTeam.getClubName());
            
            Team retrievedTeam2 = daoManager.GetTeam("ARS");
            System.out.println("Retrieved Team: " + retrievedTeam2.getClubName());

            // Get team abbreviation by team name
            String teamAbbreviation = daoManager.GetTeamAbbreviation("TestTeam");
            System.out.println("Team Abbreviation: " + teamAbbreviation);

            // Add a match
            java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
            Time currentTime = new Time(new Date().getTime());

            Matches matchToAdd = new Matches(
                currentDate,
                currentTime,
                "HomeTeam",
                "AwayTeam",
                2, 1,
                "Result",
                1, 0,
                "HTResult",
                "Referee",
                5, 3,
                4, 2,
                10, 8,
                6, 4,
                2, 3,
                2, 1
            );

            boolean matchAdded = daoManager.AddMatch(matchToAdd);
            System.out.println("Match added: " + matchAdded);

            // Get a match
            Date matchDate = new Date();
            java.sql.Date sqlMatchDate = new java.sql.Date(matchDate.getTime());

            Team homeTeam = new Team("HomeTeam", "", "", "");
            Team awayTeam = new Team("AwayTeam", "", "", "");

            Matches retrievedMatch = daoManager.GetMatch(sqlMatchDate, homeTeam, awayTeam);
            System.out.println("Retrieved Match: " + retrievedMatch);

            // Get home goals
            int homeGoals = daoManager.HomeGoals();
            System.out.println("Home Goals: " + homeGoals);

            // Get away goals
            int awayGoals = daoManager.AwayGoals();
            System.out.println("Away Goals: " + awayGoals);

            // Get matches of a team
            Team teamForMatches = new Team("HomeTeam", "", "", "");
            ArrayList<Matches> matchesOfTeam = daoManager.MatchesOfTeam(teamForMatches);
            System.out.println("Matches of Team: " + matchesOfTeam);
            
            ArrayList<Matches> matchesOfTeamARS = daoManager.MatchesOfTeam(retrievedTeam2);
            //System.out.println("Matches of Team: " + matchesOfTeamARS);
            	
            matchesOfTeamARS.forEach((match) -> match.getMatchDetails());

            // Get red cards for a team
            Team teamForRedCards = new Team("HomeTeam", "", "", "");
            int redCards = daoManager.RedCards(teamForRedCards);
            System.out.println("Red Cards: " + redCards);
            
            int redCards2 = daoManager.RedCards(retrievedTeam2);
            System.out.println("Red Cards: " + redCards2);

            // Get top red cards teams
            ArrayList<Team> topRedCardsTeams = daoManager.TopRedCards();
            System.out.println("Top Red Cards Teams: " + topRedCardsTeams);
            
            topRedCardsTeams.forEach((redCard) -> System.out.println(redCard.getClubName()));

            daoManager.close();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

