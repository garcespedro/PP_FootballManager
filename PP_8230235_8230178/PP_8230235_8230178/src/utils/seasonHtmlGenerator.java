/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/*
* Nome: <João Fontes e Lima>
* Número: <8230178>
* Turma: <LEIT3>
*
* Nome: <Luis Pedro Ribeiro Gracês>
* Número: <8230235>
* Turma: <LEIT2>
*/

import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe para gerar um arquivo HTML com os detalhes da temporada de uma liga de futebol.
 * <p>
 * Gera uma tabela listando todas as partidas da temporada, com informações como rodada, times, status da partida e resultado.
 * </p>
 */
public class seasonHtmlGenerator {

    private final ISeason season;
    private final String outputPath;

    /**
     * Construtor que inicializa a temporada e o caminho do arquivo de saída.
     * 
     * @param season Objeto que representa a temporada (não pode ser nulo).
     * @param outputPath Caminho do arquivo HTML onde os dados serão salvos (não pode ser nulo ou vazio).
     * @throws IllegalArgumentException Se algum parâmetro inválido for passado.
     */
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

    /**
     * Exporta a temporada para um arquivo HTML.
     * 
     * @throws IOException Caso haja erro na escrita do arquivo.
     */
    public void exportToJson() throws IOException {
        generate(season, outputPath);
    }

    /**
     * Gera o conteúdo HTML com os detalhes da temporada e grava no arquivo especificado.
     * 
     * @param season Objeto da temporada.
     * @param outputPath Caminho do arquivo HTML.
     * @throws IOException Caso haja erro na escrita do arquivo.
     * @throws IllegalArgumentException Se algum parâmetro inválido for passado.
     */
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
        html.append("<style>\n")
            .append("table {border-collapse: collapse; width: 100%;}\n")
            .append("th, td {border: 1px solid #000; padding: 8px; text-align: center;}\n")
            .append("thead {background-color: #f2f2f2;}\n")
            .append("</style>\n")
            .append("</head>\n<body>\n");

        html.append("<h1>Temporada ").append(season.getYear()).append(" - ").append(season.getName()).append("</h1>\n");

        html.append("<table>\n<thead>\n<tr>\n");
        html.append("<th>Rodada</th><th>Time Casa</th><th>Time Fora</th><th>Status</th><th>Resultado</th>\n");
        html.append("</tr>\n</thead>\n<tbody>\n");

        IMatch[] matches = season.getMatches();

        for (IMatch match : matches) {
            html.append("<tr>\n");
            html.append("<td>").append(match.getRound()).append("</td>\n");
            html.append("<td>").append(escapeHtml(match.getHomeClub().getName())).append("</td>\n");
            html.append("<td>").append(escapeHtml(match.getAwayClub().getName())).append("</td>\n");

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

    /**
     * Escapa caracteres especiais para HTML.
     * 
     * @param input texto original
     * @return texto escapado para evitar problemas de renderização
     */
    private static String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}
