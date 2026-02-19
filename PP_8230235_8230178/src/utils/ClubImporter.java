/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.io.IOException;
import models.Club;

public class ClubImporter {

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
