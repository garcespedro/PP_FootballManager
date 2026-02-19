/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import java.io.IOException;

/**
 *
 * @author joaof
 */
public class League implements ILeague {

    private String name;
    private ISeason[] seasons;
    private int seasonCount; 
    
    public League(String name, int maxSeasons) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("League name cannot be null or empty");
        }
        this.name = name;
        this.seasons = new ISeason[maxSeasons];
        this.seasonCount = 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ISeason[] getSeasons() {
        ISeason[] result = new ISeason[seasonCount];
        System.arraycopy(seasons, 0, result, 0, seasonCount);
        return result;
    }

    @Override
    public boolean createSeason(ISeason season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null");
        }
        int seasonYear = season.getYear();
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == seasonYear) {
                return false;
            }
        }
        if (seasonCount < seasons.length) {
            seasons[seasonCount++] = season;
            return true;
        } else {
            throw new IllegalStateException("League is full, cannot add more seasons");
        }
    }

    @Override
    public ISeason removeSeason(int year) {
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == year) {
                ISeason removedSeason = seasons[i];
                for (int j = i; j < seasonCount - 1; j++) {
                    seasons[j] = seasons[j + 1];
                }
                seasons[--seasonCount] = null;
                return removedSeason;
            }
        }
        throw new IllegalArgumentException("Season not found for the year: " + year);
    }

    @Override
    public ISeason getSeason(int year) {
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == year) {
                return seasons[i];
            }
        }
        throw new IllegalArgumentException("Season not found for the year: " + year);
    }

    @Override
    public void exportToJson() throws IOException {
        System.out.println("{");
        System.out.println("  \"name\": \"" + name + "\",");
        System.out.println("  \"seasons\": [");
        for (int i = 0; i < seasonCount; i++) {
            seasons[i].exportToJson();
            if (i < seasonCount - 1) {
                System.out.println(",");
            }
        }
        System.out.println("  ]");
        System.out.println("}");
    }
}

