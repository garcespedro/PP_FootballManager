/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.*;

public class leagueHtmlGenerator {

    public static void generate(String jsonFilePath, String outputPath) throws IOException {
        String json = readFile(jsonFilePath);

        String league = extractValue(json, "\"league\"");
        String season = extractValue(json, "\"season\"");
        String matchesArray = extractArray(json, "\"matches\"");

        // Agora vamos extrair os objetos dentro do array, usando um array de Strings fixo (tamanho máximo 100 partidas)
        String[] matchesJson = new String[100];
        int matchCount = 0;

        int i = 0;
        while (i < matchesArray.length()) {
            if (matchesArray.charAt(i) == '{') {
                int count = 1;
                int start = i;
                i++;
                while (i < matchesArray.length() && count > 0) {
                    if (matchesArray.charAt(i) == '{') count++;
                    else if (matchesArray.charAt(i) == '}') count--;
                    i++;
                }
                matchesJson[matchCount] = matchesArray.substring(start, i);
                matchCount++;
            } else {
                i++;
            }
        }
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n<html lang=\"pt-BR\">\n<head>\n<meta charset=\"UTF-8\">\n");
        html.append("<title>League Matches</title>\n");
        html.append("<style>\n");
        html.append("table {border-collapse: collapse; width: 100%;}\n");
        html.append("th, td {border: 1px solid #000; padding: 8px; text-align: left;}\n");
        html.append("thead {background-color: #f2f2f2;}\n");
        html.append("</style>\n</head>\n<body>\n");

        html.append("<h1>").append(league).append(" - Temporada ").append(season).append("</h1>\n");

        html.append("<table>\n<thead>\n<tr>\n");
        html.append("<th>Home Team</th>\n<th>Away Team</th>\n<th>Home Formation</th>\n<th>Away Formation</th>\n<th>Status</th>\n");
        html.append("</tr>\n</thead>\n<tbody>\n");

        for (int j = 0; j < matchCount; j++) {
            String matchJson = matchesJson[j];
            String homeTeam = extractValue(matchJson, "\"homeTeam\"");
            String awayTeam = extractValue(matchJson, "\"awayTeam\"");
            String homeFormation = extractValue(matchJson, "\"homeFormation\"");
            String awayFormation = extractValue(matchJson, "\"awayFormation\"");
            String status = extractValue(matchJson, "\"status\"");

            html.append("<tr>\n");
            html.append("<td>").append(homeTeam).append("</td>\n");
            html.append("<td>").append(awayTeam).append("</td>\n");
            html.append("<td>").append(homeFormation).append("</td>\n");
            html.append("<td>").append(awayFormation).append("</td>\n");
            html.append("<td>").append(status).append("</td>\n");
            html.append("</tr>\n");
        }

        html.append("</tbody>\n</table>\n</body>\n</html>\n");

        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(html.toString());
        }
    }

    private static String readFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line.trim());
        }
        br.close();
        return sb.toString();
    }

    private static String extractValue(String json, String key) {
        int index = json.indexOf(key);
        if (index == -1) return "";
        int start = json.indexOf(':', index) + 1;
        while (start < json.length() && (json.charAt(start) == ' ' || json.charAt(start) == '\"')) start++;
        int end = start;
        boolean inString = false;
        while (end < json.length()) {
            char c = json.charAt(end);
            if (c == '\"' && inString) break;
            if (c == '\"' && !inString) inString = true;
            else if (!inString && (c == ',' || c == '}')) break;
            end++;
        }
        String value = json.substring(start, end);
        return value.replaceAll("\"", "").trim();
    }

    private static String extractArray(String json, String key) {
        int index = json.indexOf(key);
        if (index == -1) return "";
        int start = json.indexOf('[', index);
        if (start == -1) return "";
        int count = 1;
        int end = start + 1;
        while (end < json.length() && count > 0) {
            if (json.charAt(end) == '[') count++;
            else if (json.charAt(end) == ']') count--;
            end++;
        }
        return json.substring(start, end);
    }
}
