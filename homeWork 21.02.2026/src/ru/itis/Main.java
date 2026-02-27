package ru.itis;

import ru.itis.models.Arena;
import ru.itis.models.Game;
import ru.itis.models.Player;
import ru.itis.models.Team;
import ru.itis.service.DataFillerUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

class StringInformation {
    String name;
    String type;

    public StringInformation(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public String getName() { return name;}
    public String getType() {return type;}
    @Override
    public String toString() {
        return String.format("%s %s", name, type);
    }
}


public class Main {
    public static void main(String[] args) {
        List<Arena> arenas = new ArrayList<>();
        List<Team> teams = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        List<Game> games = new ArrayList<>();
        try {
            arenas = DataFillerUtil.getArenas(new File("C:\\Users\\user\\Downloads\\Telegram Desktop\\stream-game\\arena.csv"));
            teams = DataFillerUtil.getTeams(new File("C:\\Users\\user\\Downloads\\Telegram Desktop\\stream-game\\team.csv"));
            players = DataFillerUtil.getPlayers(new File("C:\\Users\\user\\Downloads\\Telegram Desktop\\stream-game\\player.csv"));
            games = DataFillerUtil.getGames(new File("C:\\Users\\user\\Downloads\\Telegram Desktop\\stream-game\\game.csv"));
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка чтения данных. Правильно ли размещён файл?");
        }
        /* System.out.println(arenas);
        System.out.println(teams);
        System.out.println(players);
        System.out.println(games); */

        System.out.println("----------");
        System.out.println(solve1(arenas));
        System.out.println(solve2(players));
        System.out.println("----------");

        System.out.println(solve3(players));
        System.out.println(solve4(players));
        System.out.println("----------");

        System.out.println(solve5(teams));
        System.out.println(solve6(players));
        System.out.println("----------");

        System.out.println(solve7(teams));
        System.out.println(solve8(arenas));
        System.out.println("----------");

        System.out.println(solve9(arenas, teams));

        System.out.println(solve10(arenas, teams));
        System.out.println(solve11(players));

    }

    public static List<Arena> solve1(List<Arena> arenas){
        return arenas.stream()
                .filter(a -> a.getCapacity() >900)
                .toList();
    }

    public static List<Player> solve2(List<Player> players){
        return players.stream()
                .filter(p -> p.getPosition().equals("защитник") || p.getPosition().equals("форвард"))
                .toList();
    }

    public static List<Player> solve3(List<Player> players){
        return players.stream()
                .filter(p -> (p.getPosition().equals("защитник") ||
                        p.getPosition().equals("форвард")) &&
                        p.getSalary() >= 240000 &&
                        p.getSalary() <= 260000)
                .toList();
    }

    public static List<Player> solve4(List<Player> players){
        return players.stream()
                .filter(p -> p.getHeight() > 215 || p.getWeight() >120)
                .toList();
    }

    public static List<Team> solve5(List<Team> teams){
        return  teams.stream()
                .filter(t -> (t.getCity().equals("Барселона") ||
                        t.getCity().equals("Москва") ) &&
                        t.getCoachName().equals("Димитрис Итудис"))
                .toList();
    }

    public static List<Player> solve6(List<Player> players){
        return players.stream()
                .filter(p -> p.getHeight()>=188 &&
                        p.getHeight() <= 200 &&
                        p.getSalary()>=100000 &&
                        p.getSalary() <= 150000)
                .sorted((p1,p2) -> -p1.getName().compareTo(p2.getName()))
                .toList();
    }

    public static List<String> solve7(List<Team> teams){
        return teams.stream()
                .map(p -> String.format("город: %s; команда %s; тренер: %s", p.getCity(),
                        p.getName(),
                        p.getCoachName()))
                .sorted()
                .toList();
    }

    public static List<Arena> solve8(List<Arena> arenas){
        return arenas.stream()
                .filter(a -> a.getId() ==10 || a.getId() ==30 || a.getId() ==50)
                .sorted(Comparator.comparing(Arena::getCapacity).thenComparing(Arena::getName))
                .toList();
    }

    public static List<String> solve9( List<Arena> arenas, List<Team> teams){
        return (Stream.concat(
                arenas.stream()
                    .map(a -> a.getName()),
                teams.stream()
                        .map(t -> t.getName()) ))
                .sorted((a,b) -> b.compareTo(a)).toList();
    }

    public static List<StringInformation> solve10(List<Arena> arenas, List<Team> teams){
        return Stream.concat(
                arenas.stream().map(name -> new StringInformation(name.getName(), "стадион")),
                teams.stream().map(name -> new StringInformation(name.getName(), "команда")))
                        .sorted(Comparator.comparing(StringInformation::getType).thenComparing(StringInformation ::getName))
                        .toList();
    }

    public static List<Player> solve11(List<Player> players){
        return players.stream().sorted((p1,p2) -> {
            if(p1.getSalary() == 475000 && p2.getSalary() != 475000) return -1;
            if(p1.getSalary() != 475000 && p2.getSalary() == 475000) return 1;
            return Integer.compare(p1.getSalary(), p2.getSalary());
        }).limit(5).toList();
    }


}