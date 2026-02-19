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

import java.io.IOException;
import models.Club;

/**
 * Classe utilitária para importar clubes a partir de um ficheiro JSON.
 */
public class ClubImporter {

    /**
     * Lê um ficheiro JSON contendo uma lista de clubes e converte-o num array de objetos {@link Club}.
     * 
     * O ficheiro JSON deve conter um array de objetos JSON, onde cada objeto representa um clube
     * com os campos: "name", "code", "country", "founded", "stadium" e "logo".
     * 
     * @param filePath Caminho completo para o ficheiro JSON contendo os clubes.
     * @return Array de objetos {@link Club} criados a partir dos dados do ficheiro.
     * @throws IOException Caso ocorra um erro ao ler o ficheiro.
     */
    public static Club[] importClubs(String filePath) throws IOException {
        String json = FileUtils.readFileToString(filePath);
        json = json.substring(1, json.length() - 1);

        String[] entries = json.split("\\},\\{");

        Club[] clubes = new Club[entries.length];

        for (int i = 0; i < entries.length; i++) {
            String entry = entries[i];
            entry = entry.replace("{", "").replace("}", "");

            String[] fields = entry.split(",");

            String name = "", code = "", country = "", stadium = "", logo = "";
            int founded = 0;

            for (int j = 0; j < fields.length; j++) {
                String[] pair = fields[j].split(":", 2);
                if (pair.length < 2) continue;

                String key = pair[0].replace("\"", "").trim();
                String value = pair[1].replace("\"", "").trim();

                switch (key) {
                    case "name": name = value; break;
                    case "code": code = value; break;
                    case "country": country = value; break;
                    case "founded": founded = Integer.parseInt(value); break;
                    case "stadium": stadium = value; break;
                    case "logo": logo = value; break;
                }
            }

            clubes[i] = new Club(name, code, country, founded, stadium, logo);
        }

        return clubes;
    }
}
