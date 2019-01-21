package de.nordrheintvplay.discord.levelbot.json;

public class User {

    private int role, xp, coins;
    private long lastxp, boostertime, id;
    private boolean premium, ultra, booster;

    User(int role, int xp, int coins, long id, long lastxp, long boostertime, boolean premium, boolean ultra, boolean booster) {
        this.role = role;
        this.xp = xp;
        this.coins = coins;
        this.id = id;
        this.lastxp = lastxp;
        this.boostertime = boostertime;
        this.premium = premium;
        this.ultra = ultra;
        this.booster = booster;
    }

    public int getRole() {
        return role;
    }

    public User setRole(int role) {
        this.role = role;
        return this;
    }

    public int getXp() {
        return xp;
    }

    public User setXp(int xp) {
        this.xp = xp;
        return this;
    }

    public int getCoins() {
        return coins;
    }

    public User setCoins(int coins) {
        this.coins = coins;
        return this;
    }

    public long getLastXp() {
        return lastxp;
    }

    public User setLastxp(long lastxp) {
        this.lastxp = lastxp;
        return this;
    }

    public long getBoosterTime() {
        return boostertime;
    }

    public User setBoosterTime(long boostertime) {
        this.boostertime = boostertime;
        return this;
    }

    public boolean getPremium() {
        return premium;
    }

    public User setPremium(boolean premium) {
        this.premium = premium;
        return this;
    }

    public boolean getUltra() {
        return ultra;
    }

    public User setUltra(boolean ultra) {
        this.ultra = ultra;
        return this;
    }

    public boolean hasBooster() {
        return booster;
    }

    public User setBooster(boolean booster) {
        this.booster = booster;
        return this;
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public void save() {
        Users.updateUser(this);
    }

    public void remove() {
        Users.removeUser(this);
    }

}
