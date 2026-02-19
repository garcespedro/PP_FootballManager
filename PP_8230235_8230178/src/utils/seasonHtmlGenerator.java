/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author joaof
 */
package utils;

import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import java.io.FileWriter;
import java.io.IOException;

public class seasonHtmlGenerator {

    private final ISeason season;
    private final String outputPath;

    public seasonHtmlGenerator(ISeason season, String outputPath) {
        if (season == null) {
            throw new IllegalArgumentException("Season não pode ser nulo.");
        }
        if (outputPath == null || outputPath.trim().isEmpty()) {
            throw new IllegalArgumentException("OutputPath inválido.");
        }
        this.season = season;
        this.outputPath = outputPath;
    }

    public void exportToJson() throws IOException {
        generate(season, outputPath);
    }

    public static void generate(ISeason season, String outputPath) throws IOException {
        if (season == null) {
            throw new IllegalArgumentException("Season não pode ser nulo.");
        }
        if (outputPath == null || outputPath.trim().isEmpty()) {
            throw new IllegalArgumentException("OutputPath inválido.");
        }

        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n<html lang=\"pt-BR\">\n<head>\n<meta charset=\"UTF-8\">\n");
        html.append("<title>Temporada ").append(season.getYear()).append(" - ").append(season.getName()).append("</title>\n");
        html.append("</head>\n<body>\n");

        html.append("<h1>Temporada ").append(season.getYear()).append(" - ").append(season.getName()).append("</h1>\n");

        html.append("<table border='1' cellpadding='5' cellspacing='0'>\n<thead>\n<tr>\n");
        html.append("<th>Rodada</th><th>Time Casa</th><th>Time Fora</th><th>Status</th><th>Resultado</th>\n");
        html.append("</tr>\n</thead>\n<tbody>\n");

        IMatch[] matches = season.getMatches();

        for (IMatch match : matches) {
            html.append("<tr>\n");
            html.append("<td>").append(match.getRound()).append("</td>\n");
            html.append("<td>").append(match.getHomeClub().getName()).append("</td>\n");
            html.append("<td>").append(match.getAwayClub().getName()).append("</td>\n");

            String status = match.isPlayed() ? "Jogada" : "Agendada";
            html.append("<td>").append(status).append("</td>\n");

            String result;
            if (match.isPlayed()) {
                int homeGoals = match.getTotalByEvent(simulator.GoalEvent.class, match.getHomeClub());
                int awayGoals = match.getTotalByEvent(simulator.GoalEvent.class, match.getAwayClub());
                result = homeGoals + " x " + awayGoals;
            } else {
                result = "-";
            }
            html.append("<td>").append(result).append("</td>\n");

            html.append("</tr>\n");
        }

        html.append("</tbody>\n</table>\n</body>\n</html>");

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(html.toString());
        }
    }
}

