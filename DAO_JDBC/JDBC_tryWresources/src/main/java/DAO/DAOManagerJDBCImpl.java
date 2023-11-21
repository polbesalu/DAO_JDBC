package DAO;

import java.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import Models.*;

public class DAOManagerJDBCImpl implements IDAOManager{

	Connection conn = null;
	
	public DAOManagerJDBCImpl() {
		
		String connectionUrl = "jdbc:mysql://localhost:3306/premier2223?serverTimezone=UTC";
		
		try {
			conn = DriverManager.getConnection(connectionUrl, "root", "");
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}  
	}
	
	public boolean AddTeam(Team oneTeam) {
		boolean added = true;
		CallableStatement cS = null;
		
		try {
			String storedProcedureCall = "{call AddTeam(?,?,?,?)}";
			
			cS = conn.prepareCall(storedProcedureCall);
			cS.setString(1, oneTeam.getClubName());
			cS.setString(2, oneTeam.getAbv());
			cS.setString(3, oneTeam.getHexCode());
			cS.setString(4, oneTeam.getLogoLink());
			
			cS.executeUpdate();
			
		} 
		catch(Exception e) {
			System.out.println("No s'ha pogut afegir el --> " + e);
			added = false;
		} 
		finally {
			if(cS != null) {
				try {
					cS.close();
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return added;
	}
	
	public void ImportTeams(String fileTeams) {
		
		BufferedReader br = null;
		
		try {
			File file = new File("clubs.csv");
			br = new BufferedReader(new FileReader(file));
			
			br.readLine();
			
			String line;
				
			while((line = br.readLine()) != null) {
				String[] fields = line.split(",");
				String clubName =  fields[0];
				String abv = fields[1];
				String hexCode = fields[2];				
				String logoLink = fields[3];
					
				Team team = new Team(clubName, abv, hexCode, logoLink);
					
				AddTeam(team);
			}	
		} 
		catch(IOException e) {
			e.printStackTrace();
		} 
		finally {
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}	
	
	public Team GetTeam(String teamAbbreviation) {
	    CallableStatement cS = null;
	    Team team = null;

	    try {
	        String storedProcedureCall = "{call GetTeam(?)}";
	        
	        cS = conn.prepareCall(storedProcedureCall);
	        cS.setString(1, teamAbbreviation);

	        boolean hasResults = cS.execute();

	        if (hasResults) {
	            ResultSet resultSet = cS.getResultSet();

	            if (resultSet.next()) {
	                team = new Team(
	                    resultSet.getString("club_name"),
	                    resultSet.getString("abv"),
	                    resultSet.getString("hex_code"),
	                    resultSet.getString("logo_link")
	                );
	            }
	        }
	    } catch (SQLException ex) {
	        System.out.println("Error retrieving team: " + ex.getMessage());
	    } 
	    finally {
	        if (cS != null) {
	            try {
	                cS.close();
	            } 
	            catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    return team;
	}

	public String GetTeamAbbreviation(String teamName) {
		
		String abbreviation = null;
		
		try (CallableStatement cS = conn.prepareCall("{call GetTeamAbbreviationByTeamName(?, ?)}")) {
            
			cS.setString(1, teamName);
            cS.registerOutParameter(2, Types.VARCHAR);
            cS.execute();
            
            abbreviation = cS.getString(2);
            
        } 
		catch (SQLException ex) {
            System.out.println("Error retrieving abbreviation: " + ex.getMessage());
        }
		
		return abbreviation;
	}

	public boolean AddMatch(Matches oneMatch) {
	    boolean added = true;

	    try (CallableStatement cS = conn.prepareCall("{call AddMatch(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
	        java.sql.Date sqlDate = new java.sql.Date(oneMatch.getDate().getTime());
	        java.sql.Time sqlTime = new java.sql.Time(oneMatch.getTime().getTime());

	        cS.setDate(1, sqlDate);
	        cS.setTime(2, sqlTime);
	        cS.setString(3, oneMatch.getHomeTeam());
	        cS.setString(4, oneMatch.getAwayTeam());
	        cS.setInt(5, oneMatch.getFullTimeHomeGoals());
	        cS.setInt(6, oneMatch.getFullTimeAwayGoals());
	        cS.setString(7, oneMatch.getFullTimeResult());
	        cS.setInt(8, oneMatch.getHalfTimeHomeGoals());
	        cS.setInt(9, oneMatch.getHalfTimeAwayGoals());
	        cS.setString(10, oneMatch.getHalfTimeResult());
	        cS.setString(11, oneMatch.getReferee());
	        cS.setInt(12, oneMatch.getHomeTeamShots());
	        cS.setInt(13, oneMatch.getAwayTeamShots());
	        cS.setInt(14, oneMatch.getHomeShotsGoal());
	        cS.setInt(15, oneMatch.getAwayShotsGoal());
	        cS.setInt(16, oneMatch.getHomeTeamFouls());
	        cS.setInt(17, oneMatch.getAwayTeamFouls());
	        cS.setInt(18, oneMatch.getHomeTeamCorners());
	        cS.setInt(19, oneMatch.getAwayTeamCorners());
	        cS.setInt(20, oneMatch.getHomeTeamYellowCards());
	        cS.setInt(21, oneMatch.getAwayTeamYellowCards());
	        cS.setInt(22, oneMatch.getHomeTeamRedCards());
	        cS.setInt(23, oneMatch.getAwayTeamRedCards());

	        cS.executeUpdate();

	    } catch (SQLException e) {
	        System.out.println("Error adding match: " + e.getMessage());
	        added = false;
	    }

	    return added;
	}


	public void ImportMatches(String fileMatches){
		
		 try (BufferedReader br = new BufferedReader(new FileReader(fileMatches))) {
		        br.readLine();
		        String line;

		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		        while ((line = br.readLine()) != null) {
		            String[] fields = line.split(",");

		            try {
		                java.util.Date utilDate = dateFormat.parse(fields[1]);
		                java.sql.Date matchDate = new java.sql.Date(utilDate.getTime());

		                Time matchTime = new Time(timeFormat.parse(fields[2]).getTime());

		                Matches match = new Matches(
		                        matchDate,
		                        matchTime,
		                        fields[3],
		                        fields[4],
		                        Integer.parseInt(fields[5]),
		                        Integer.parseInt(fields[6]),
		                        fields[7],
		                        Integer.parseInt(fields[8]),
		                        Integer.parseInt(fields[9]),
		                        fields[10],
		                        fields[11],
		                        Integer.parseInt(fields[12]),
		                        Integer.parseInt(fields[13]),
		                        Integer.parseInt(fields[14]),
		                        Integer.parseInt(fields[15]),
		                        Integer.parseInt(fields[16]),
		                        Integer.parseInt(fields[17]),
		                        Integer.parseInt(fields[18]),
		                        Integer.parseInt(fields[19]),
		                        Integer.parseInt(fields[20]),
		                        Integer.parseInt(fields[21]),
		                        Integer.parseInt(fields[22]),
		                        Integer.parseInt(fields[23]));

		                AddMatch(match);
		            } catch (ParseException ex) {
		                System.out.println("Error parsing match data: " + ex.getMessage());
		            }
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	}

	public Matches GetMatch(Date matchDay, Team home, Team away) {
		Matches selectedMatch = null;

	    try (CallableStatement cS = conn.prepareCall("{call GetMatchByDateAndTeams(?, ?, ?)}")) {
	        java.sql.Date sqlDate = new java.sql.Date(matchDay.getTime());
	        cS.setDate(1, sqlDate);
	        cS.setString(2, home.getClubName());
	        cS.setString(3, away.getClubName());

	        try (ResultSet resultSet = cS.executeQuery()) {
	            if (resultSet.next()) {
	                Date date = new Date(resultSet.getDate("date_of_match").getTime());
	                Time time = new Time(resultSet.getTime("time_of_match").getTime());

	                selectedMatch = new Matches(
	                        date,
	                        time,
	                        resultSet.getString("home_team"),
	                        resultSet.getString("away_team"),
	                        resultSet.getInt("full_time_home_goals"),
	                        resultSet.getInt("full_time_away_goals"),
	                        resultSet.getString("full_time_result"),
	                        resultSet.getInt("halftime_home_goals"),
	                        resultSet.getInt("halftime_away_goals"),
	                        resultSet.getString("halftime_result"),
	                        resultSet.getString("referee"),
	                        resultSet.getInt("home_team_shots"),
	                        resultSet.getInt("away_team_shots"),
	                        resultSet.getInt("home_team_shots_on_goal"),
	                        resultSet.getInt("away_team_shots_on_goal"),
	                        resultSet.getInt("home_team_fouls"),
	                        resultSet.getInt("away_team_fouls"),
	                        resultSet.getInt("home_team_corners"),
	                        resultSet.getInt("away_team_corners"),
	                        resultSet.getInt("home_team_yellow_cards"),
	                        resultSet.getInt("away_team_yellow_cards"),
	                        resultSet.getInt("home_team_red_cards"),
	                        resultSet.getInt("away_team_red_cards")
	                );
	            }
	        }
	    } 
	    catch (SQLException e) {
	        System.out.println("Error retrieving match: " + e.getMessage());
	    }

	    return selectedMatch;	}


	public int HomeGoals() {
	    int totalHomeGoals = 0;
	    
	    try (CallableStatement cS = conn.prepareCall("{call HomeGoals(?)}")) {
	        cS.registerOutParameter(1, Types.INTEGER);
	        cS.execute();
	        totalHomeGoals = cS.getInt(1);
	    } 
	    catch (SQLException e) {
	        System.out.println("Error in HomeGoals(): " + e.getMessage());
	    }
	    return totalHomeGoals;
	}

	public int AwayGoals() {
		int awayHomeGoals = 0;

	    try (CallableStatement cS = conn.prepareCall("{call AwayGoals(?)}")) {
	        cS.registerOutParameter(1, Types.INTEGER);
	        cS.execute();
	        awayHomeGoals = cS.getInt(1);

	    } 
	    catch (SQLException e) {
	        System.out.println("Error in getAwayGoals(): " + e.getMessage());
	    }

	    return awayHomeGoals;
	}

	public ArrayList<Matches> MatchesOfTeam(Team oneTeam) {
		ArrayList<Matches> matches = new ArrayList<>();
	    
	    try (CallableStatement cS = conn.prepareCall("{call GetMatchesByTeamName(?)}")) {
	        String teamName = oneTeam.getClubName();
	        cS.setString(1, teamName);

	        boolean hasResults = cS.execute();
	        if (hasResults) {
	            ResultSet resultSet = cS.getResultSet();
	            while (resultSet.next()) {
	                Date date = new Date(resultSet.getDate("date_of_match").getTime());
	                Time time = new Time(resultSet.getTime("time_of_match").getTime());

	                matches.add(new Matches(
	                        date,
	                        time,
	                        resultSet.getString("home_team"),
	                        resultSet.getString("away_team"),
	                        resultSet.getInt("full_time_homeGoals"),
	                        resultSet.getInt("full_time_away_goals"),
	                        resultSet.getString("full_time_result"),
	                        resultSet.getInt("halftime_home_Goals"),
	                        resultSet.getInt("halftime_away_goals"),
	                        resultSet.getString("halftime_result"),
	                        resultSet.getString("referee_of_match"),
	                        resultSet.getInt("home_team_shots"),
	                        resultSet.getInt("away_team_shots"),
	                        resultSet.getInt("home_team_shots_on_goal"),
	                        resultSet.getInt("away_team_shots_on_goal"),
	                        resultSet.getInt("home_team_fouls"),
	                        resultSet.getInt("away_team_fouls"),
	                        resultSet.getInt("home_team_corners"),
	                        resultSet.getInt("away_team_corners"),
	                        resultSet.getInt("home_team_yellow_cards"),
	                        resultSet.getInt("away_team_yellow_cards"),
	                        resultSet.getInt("home_team_red_cards"),
	                        resultSet.getInt("away_team_red_cards")
	                ));
	            }
	        }
	    } 
	    catch (SQLException e) {
	        System.out.println("Error in GetMatchesByTeamName(): " + e.getMessage());
	    }

	    return matches;
	}

	public int RedCards(Team oneTeam) {
		int redCards = 0;
		try (CallableStatement cS = conn.prepareCall("{call GetTeamRedCards(?, ?)}")) {
	        
			cS.setString(1, oneTeam.getClubName());
	        cS.registerOutParameter(2, Types.INTEGER);
	        cS.execute();
	        redCards = cS.getInt(2);

	    } 
		catch (SQLException e) {
	        System.out.println("Error in RedCards(): " + e.getMessage());
	    }

	    return redCards;
	}

	public ArrayList<Team> TopRedCards() {
		ArrayList<Team> topRedCardsTeams = new ArrayList<>();

	    try (PreparedStatement pS = conn.prepareStatement("SELECT * FROM team")) {
	        try (ResultSet resultSet = pS.executeQuery()) {
	            while (resultSet.next()) {
	                Team team = new Team(
	                        resultSet.getString("club_name"),
	                        resultSet.getString("abv"),
	                        resultSet.getString("hex_code"),
	                        resultSet.getString("logo_link")
	                );

	                if (topRedCardsTeams.isEmpty() || RedCards(team) > RedCards(topRedCardsTeams.get(0))) {
	                    topRedCardsTeams.clear();
	                    topRedCardsTeams.add(team);
	                } else if (RedCards(team) == RedCards(topRedCardsTeams.get(0))) {
	                    topRedCardsTeams.add(team);
	                }
	            }
	        }
	    } 
	    catch (SQLException e) {
	        System.out.println("Error in TopRedCards(): " + e.getMessage());
	    }

	    return topRedCardsTeams;
	}

	public void close() throws Exception {
		try {
			if(conn != null) conn.close();
		} 
		catch (SQLException e) {
			System.out.println("Exception closing Connection: " + e);
		}
	}
}
