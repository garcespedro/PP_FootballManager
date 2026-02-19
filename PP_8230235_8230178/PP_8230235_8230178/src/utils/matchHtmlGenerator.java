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

import com.ppstudios.footballmanager.api.contracts.match.IMatch;
import java.io.FileWriter;
import java.io.IOException;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe utilitária para gerar um ficheiro HTML que exibe detalhes de uma partida de futebol.
 * 
 * <p>Gera uma página HTML contendo uma tabela simples com informações sobre as equipes em casa e fora,
 * suas formações, status do jogo e a rodada em que a partida ocorre.</p>
 */
public class matchHtmlGenerator {

    /**
     * Gera um ficheiro HTML contendo os detalhes da partida especificada.
     * 
     * @param match objeto que implementa a interface IMatch contendo os dados da partida
     * @param outputPath caminho onde o ficheiro HTML será salvo
     * @throws IOException caso ocorra erro na escrita do ficheiro
     * @throws IllegalArgumentException se {@code match} for nulo ou {@code outputPath} inválido
     */
    public static void generate(IMatch match, String outputPath) throws IOException {
        if (match == null) {
            throw new IllegalArgumentException("match cannot be null");
        }
        if (outputPath == null || outputPath.trim().isEmpty()) {
            throw new IllegalArgumentException("outputPath is invalid");
        }

        // Obtém nomes e formações das equipes, usando "N/A" caso dados ausentes
        String homeTeamName = match.getHomeTeam() != null ? match.getHomeTeam().getClub().getName() : "N/A";
        String awayTeamName = match.getAwayTeam() != null ? match.getAwayTeam().getClub().getName() : "N/A";

        String homeFormation = match.getHomeTeam() != null && match.getHomeTeam().getFormation() != null
                ? match.getHomeTeam().getFormation().toString()
                : "N/A";
        String awayFormation = match.getAwayTeam() != null && match.getAwayTeam().getFormation() != null
                ? match.getAwayTeam().getFormation().toString()
                : "N/A";

        // Estado do jogo: "Played" ou "Scheduled"
        String status = match.isPlayed() ? "Played" : "Scheduled";

        StringBuilder html = new StringBuilder();

        // Cabeçalho e estilos CSS básicos para a tabela
        html.append("<!DOCTYPE html>\n<html lang=\"pt-BR\">\n<head>\n<meta charset=\"UTF-8\">\n");
        html.append("<title>Match Details</title>\n");
        html.append("<style>\n");
        html.append("table {border-collapse: collapse; width: 100%;}\n");
        html.append("th, td {border: 1px solid #000; padding: 8px; text-align: left;}\n");
        html.append("thead {background-color: #f2f2f2;}\n");
        html.append("</style>\n</head>\n<body>\n");

        html.append("<h1>Detalhes da Partida - Rodada ").append(match.getRound()).append("</h1>\n");

        // Construção da tabela HTML
        html.append("<table>\n<thead>\n<tr>\n");
        html.append("<th>Home Team</th>\n<th>Away Team</th>\n<th>Home Formation</th>\n<th>Away Formation</th>\n<th>Status</th>\n");
        html.append("</tr>\n</thead>\n<tbody>\n");

        html.append("<tr>\n");
        html.append("<td>").append(escapeHtml(homeTeamName)).append("</td>\n");
        html.append("<td>").append(escapeHtml(awayTeamName)).append("</td>\n");
        html.append("<td>").append(escapeHtml(homeFormation)).append("</td>\n");
        html.append("<td>").append(escapeHtml(awayFormation)).append("</td>\n");
        html.append("<td>").append(escapeHtml(status)).append("</td>\n");
        html.append("</tr>\n");

        html.append("</tbody>\n</table>\n</body>\n</html>\n");

        // Escrita do HTML no ficheiro
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write(html.toString());
        }
    }

    /**
     * Escapa caracteres especiais para evitar problemas na renderização HTML.
     * 
     * @param input String que pode conter caracteres especiais HTML
     * @return String segura para inserção em HTML
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
