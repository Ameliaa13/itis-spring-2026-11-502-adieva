package ru.itis.models;

public class Game {
    private int id;
    private int ownerTeamId;
    private int guestTeamId;
    private String gameDate;
    private int winnerTeamId;
    private String score;
    private int arenaId;

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", ownerTeamId=" + ownerTeamId +
                ", guestTeamId=" + guestTeamId +
                ", gameDate='" + gameDate + '\'' +
                ", winnerTeamId=" + winnerTeamId +
                ", score='" + score + '\'' +
                ", arenaId=" + arenaId +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerTeamId() {
        return ownerTeamId;
    }

    public void setOwnerTeamId(int ownerTeamId) {
        this.ownerTeamId = ownerTeamId;
    }

    public int getGuestTeamId() {
        return guestTeamId;
    }

    public void setGuestTeamId(int guestTeamId) {
        this.guestTeamId = guestTeamId;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public int getWinnerTeamId() {
        return winnerTeamId;
    }

    public void setWinnerTeamId(int winnerTeamId) {
        this.winnerTeamId = winnerTeamId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getArenaId() {
        return arenaId;
    }

    public void setArenaId(int arenaId) {
        this.arenaId = arenaId;
    }
}
