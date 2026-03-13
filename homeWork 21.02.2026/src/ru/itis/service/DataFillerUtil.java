package ru.itis.service;

import ru.itis.models.Arena;
import ru.itis.models.Game;
import ru.itis.models.Player;
import ru.itis.models.Team;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class DataFillerUtil {

    public static List<Arena> getArenas(File file) throws FileNotFoundException {
        List<Arena> result = new ArrayList<>();
        Scanner sc = new Scanner(file);
        sc.nextLine();
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] lines = line.split(",");
            Arena arena = new Arena();
            arena.setId(Integer.parseInt(lines[0]));
            arena.setName(lines[1]);
            arena.setCapacity(Integer.parseInt(lines[2]));
            result.add(arena);
        }
        sc.close();
        return result;
    }

    public static List<Team> getTeams(File file) throws FileNotFoundException {
        List<Team> result = new ArrayList<>();
        Scanner sc = new Scanner(file);
        sc.nextLine();
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] lines = line.split(",");
            Team team = new Team();
            team.setId(Integer.parseInt(lines[0]));
            team.setCity(lines[1]);
            team.setName(lines[2]);
            team.setCoachName(lines[3]);
            team.setArenaId(Integer.parseInt(lines[4]));
            result.add(team);
        }
        sc.close();
        return result;
    }

    public static List<Player> getPlayers(File file) throws FileNotFoundException {
        List<Player> result = new ArrayList<>();
        Scanner sc = new Scanner(file);
        sc.nextLine();
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] lines = line.split(",");
            Player player = new Player();
            player.setId(Integer.parseInt(lines[0]));
            player.setName(lines[1]);
            player.setPosition(lines[2]);
            player.setHeight(Integer.parseInt(lines[3]));
            player.setWeight(Integer.parseInt(lines[4]));
            player.setSalary(Integer.parseInt(lines[5]));
            player.setTeamId(Integer.parseInt(lines[6]));
            result.add(player);
        }
        sc.close();
        return result;
    }

    public static List<Game> getGames(File file) throws FileNotFoundException {
        List<Game> result = new ArrayList<>();
        Scanner sc = new Scanner(file);
        sc.nextLine();
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] lines = line.split(",");
            Game game = new Game();
            game.setId(Integer.parseInt(lines[0]));
            game.setOwnerTeamId(Integer.parseInt(lines[1]));
            game.setGuestTeamId(Integer.parseInt(lines[2]));
            game.setGameDate(lines[3]);
            game.setWinnerTeamId(Integer.parseInt(lines[4]));
            game.setScore(lines[5]);
            game.setArenaId(Integer.parseInt(lines[6]));
            result.add(game);
        }
        sc.close();
        return result;
    }

}
