package ru.itis;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;

class Tests {
    List<Arena> testArenas = new ArrayList<>();
    List<Team> testTeams = new ArrayList<>();
    List<Player> testPlayers = new ArrayList<>();
    List<Game> games = new ArrayList<>();


    Tests() throws FileNotFoundException {
        testArenas = DataFillerUtil.getArenas(new File("C:\\Users\\user\\Downloads\\Telegram Desktop\\stream-game\\arena.csv"));
        testTeams = DataFillerUtil.getTeams(new File("C:\\Users\\user\\Downloads\\Telegram Desktop\\stream-game\\team.csv"));
        testPlayers = DataFillerUtil.getPlayers(new File("C:\\Users\\user\\Downloads\\Telegram Desktop\\stream-game\\player.csv"));
        games = DataFillerUtil.getGames(new File("C:\\Users\\user\\Downloads\\Telegram Desktop\\stream-game\\game.csv"));
    }

    @Test
    void testSolve1() {
        List<Arena> result = Main.solve1(testArenas);
        assertTrue(result.stream().allMatch(a -> a.getCapacity() > 9000));

    }

    @Test
    void testSolve2() {
        List<Player> result = Main.solve2(testPlayers);
        assertTrue(result.stream().allMatch(p -> p.getPosition().equals("защитник") || p.getPosition().equals("форвард")));
    }

    @Test
    void testSolve3() {
        List<Player> result = Main.solve3(testPlayers);
        assertTrue(result.stream().allMatch(p -> (p.getPosition().equals("защитник") || p.getPosition().equals("форвард"))
                && p.getSalary() >= 240000 && p.getSalary() <= 260000));
    }

    @Test
    void testSolve4() {
        List<Player> result = Main.solve4(testPlayers);
        assertTrue(result.stream().allMatch(p -> p.getHeight() >= 215 || p.getWeight() >= 120));
    }

    @Test
    void testSolve5() {
        List<Team> result = Main.solve5(testTeams);
        assertTrue(result.stream().allMatch(t -> (t.getCity().equals("Москва") || t.getCity().equals("Барселона"))
                && t.getCoachName().equals("Димитрис Итудис")));
    }

    @Test
    void testSolve6() {
        List<Player> result = Main.solve6(testPlayers);
        assertTrue(result.stream().allMatch(p -> p.getHeight() >= 188 && p.getHeight() <= 200
                && p.getSalary() >= 100000 && p.getSalary() <= 150000));
    }

    @Test
    void testSolve7() {
        List<String> result = Main.solve7(testTeams);
        assertEquals(5, result.size());
    }

    @Test
    void testSolve8() {
        List<Arena> result = Main.solve8(testArenas);
        assertEquals(3, result.size());
    }

    @Test
    void testSolve9() {
        List<String> result = Main.solve9(testArenas, testTeams);
        List<String> allNames = new ArrayList<>();
        testArenas.forEach(a -> allNames.add(a.getName()));
        testTeams.forEach(t -> allNames.add(t.getName()));
        allNames.sort((s1, s2) -> -s1.compareTo(s2));
        assertEquals(allNames.size(), result.size());
        assertEquals(allNames, result);
    }

    @Test
    void testSolve10() {
        List<StringInformation> result = Main.solve10(testArenas, testTeams);
        List<StringInformation> expected = new ArrayList<>();
        testArenas.forEach(a -> expected.add(new StringInformation(a.getName(), "стадион")));
        testTeams.forEach(t -> expected.add(new StringInformation(t.getName(), "команда")));
        expected.sort(Comparator.comparing(StringInformation::getType).reversed()
                .thenComparing(StringInformation::getName));

        assertEquals(expected.size(), result.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getName(), result.get(i).getName());
            assertEquals(expected.get(i).getType(), result.get(i).getType());
        }
    }

    @Test
    void testSolve11() {
        List<Player> result = Main.solve11(testPlayers);
        assertEquals(5, result.size());
    }
}