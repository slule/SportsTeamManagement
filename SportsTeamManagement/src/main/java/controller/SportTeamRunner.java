/**  
* @Author Shalom Lule - slule@dmacc.edu  
* CIS175 <Spring 2024>
* Feb 7, 2024 
*/
package controller;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.SportsTeam;

import java.util.List;

public class SportTeamRunner {
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("SportsTeamManagement");

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		char choice;
		do {

			System.out.println("1. Add a new team");
			System.out.println("2. Delete a team");
			System.out.println("3. Edit team details");
			System.out.println("4. View all teams");
			System.out.println("5. Exit");

			System.out.println("Enter your choice: ");
			choice = scanner.next().charAt(0);
			scanner.nextLine();

			switch (choice) {

			case '1':
				addNewTeam(scanner);
				break;

			case '2':
				deleteTeam(scanner);
				break;

			case '3':
				editTeamDetails(scanner);
				break;

			case '4':
				viewAllTeams();
				break;
			case '5':
				System.out.println("Exiting. . .");
				break;
			default:
				System.out.println("Invalid choice!");

			}
		} while (choice != '4');

		scanner.close();
		factory.close();

	}

	private static void addNewTeam(Scanner scanner) {
		EntityManager entityManager = factory.createEntityManager();
		System.out.println("Enter team name:");
		String name = scanner.nextLine();

		System.out.println("Enter city name:");
		String city = scanner.nextLine();

		System.out.println("Enter number of players:");
		int numberOfPlayers = scanner.nextInt();

		entityManager.getTransaction().begin();

		SportsTeam team = new SportsTeam(name, numberOfPlayers);
		team.setCity(city);
		entityManager.persist(team);
		entityManager.getTransaction().commit();

		System.out.println("New team added!");

		entityManager.close();

	}

	private static void deleteTeam(Scanner scanner) {
		EntityManager entityManager = factory.createEntityManager();
		System.out.println("Enter team ID to delete:");
		long teamId = scanner.nextLong();
		scanner.nextLine();

		SportsTeam team = entityManager.find(SportsTeam.class, teamId);
		if (team != null) {
			entityManager.getTransaction().begin();
			entityManager.remove(team);
			entityManager.getTransaction().commit();
			System.out.println("Team deleted!");

		} else {
			System.out.println("Team" + teamId + "Not found! ");
		}

		entityManager.close();

	}

	private static void editTeamDetails(Scanner scanner) {
		EntityManager entityManager = factory.createEntityManager();
		System.out.print("Enter team ID to edit: ");
		long teamId = scanner.nextLong();
		scanner.nextLine();

		try {
			entityManager.getTransaction().begin();
			SportsTeam team = entityManager.find(SportsTeam.class, teamId);
			if (team != null) {
				System.out.print("Enter new city name (or leave blank to keep current): ");
				String newCity = scanner.nextLine();
				if (!newCity.isBlank()) {
					team.setCity(newCity);
				}

				System.out.print("Enter new team name (leave blank to keep current): ");
				String newName = scanner.nextLine();
				if (!newName.isBlank()) {
					team.setName(newName);
				}

				System.out.print("Enter new number of players (enter 0 to keep current): ");
				int newNumberOfPlayers = scanner.nextInt();
				scanner.nextLine(); // Consume newline character
				if (newNumberOfPlayers != 0) {
					team.setNumberOfPlayers(newNumberOfPlayers);
				}

				entityManager.merge(team);
				entityManager.getTransaction().commit();
				System.out.println("Team details updated!");
			} else {
				System.out.println("Team with ID " + teamId + " not found!");
			}
		} catch (Exception e) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			System.out.println("An error occurred: " + e.getMessage());
		} finally {
			entityManager.close();
		}
	}

	private static void viewAllTeams() {
		EntityManager entityManager = factory.createEntityManager();

		List<SportsTeam> teams = entityManager.createQuery("SELECT t FROM SportsTeam t", SportsTeam.class).getResultList();
		if (teams.isEmpty()) {

			System.out.println("No teams found!");
		} else {
			System.out.println("teams:");
			for (SportsTeam team : teams) {
				System.out.println("ID: " + team.getTeamId() + ", Name: " + team.getName() + ", City: " + team.getCity()
						+ ", Number of Players: " + team.getNumberOfPlayers());
			}

		}

		entityManager.close();

	}
}
