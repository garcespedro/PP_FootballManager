/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author joaof
 */
package com.ppstudios.footballmanager.api.gerador;

import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class clubHtmlGenerator {

    public static void generate(IClub club, String outputPath) throws IOException {
        if (club == null) {
            throw new IllegalArgumentException("Club não pode ser nulo.");
        }
        if (outputPath == null || outputPath.trim().isEmpty()) {
            throw new IllegalArgumentException("Output path inválido.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write("<!DOCTYPE html>");
            writer.write("<html lang='pt-br'>");
            writer.write("<head>");
            writer.write("<meta charset='UTF-8'>");
            writer.write("<title>" + club.getName() + "</title>");
            writer.write("<style>");
            writer.write("body { font-family: Arial, sans-serif; margin: 20px; }");
            writer.write(".logo { width: 150px; }");
            writer.write("table { width: 100%; border-collapse: collapse; }");
            writer.write("th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }");
            writer.write("</style>");
            writer.write("</head>");
            writer.write("<body>");

            writer.write("<h1>" + club.getName() + "</h1>");
            writer.write("<img src='" + club.getLogo() + "' alt='Logo do clube' class='logo'>");
            writer.write("<p><strong>País:</strong> " + club.getCountry() + "</p>");
            writer.write("<p><strong>Ano de Fundação:</strong> " + club.getFoundedYear() + "</p>");
            writer.write("<p><strong>Estádio:</strong> " + club.getStadiumName() + "</p>");
            writer.write("<p><strong>Código:</strong> " + club.getCode() + "</p>");

            writer.write("<h2>Jogadores</h2>");
            writer.write("<table>");
            writer.write("<tr><th>Nome</th><th>Posição</th></tr>");

            for (IPlayer player : club.getPlayers()) {
                if (player != null) {
                    writer.write("<tr>");
                    writer.write("<td>" + player.getName() + "</td>");
                    writer.write("<td>" + player.getPosition().getDescription() + "</td>");
                    writer.write("</tr>");
                }
            }

            writer.write("</table>");
            writer.write("</body>");
            writer.write("</html>");
        }

        System.out.println("Arquivo HTML gerado com sucesso: " + outputPath);
    }
}
