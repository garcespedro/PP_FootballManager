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
import java.time.LocalDate;
import java.util.Random;
import models.Club;
import models.Player;
import models.PlayerPosition;
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Random;

/**
 * Classe utilitária para importar os dados do plantel (jogadores) de um clube a partir de um ficheiro JSON.
 */
public class SquadImporter {

    /**
     * Importa o plantel de jogadores para o clube a partir do ficheiro JSON especificado.
     * 
     * O método lê o ficheiro JSON, faz o parsing dos jogadores e adiciona-os ao clube.
     * Algumas características dos jogadores são geradas aleatoriamente (atributos físicos e técnicos).
     * 
     * @param club Clube onde os jogadores serão adicionados.
     * @param filePath Caminho do ficheiro JSON com os dados dos jogadores.
     * @param key Chave usada para identificar o plantel (não usada diretamente no método).
     * @throws IOException Se ocorrer erro ao ler o ficheiro.
     */
    public static void importSquad(Club club, String filePath, String key) throws IOException {
        String json = FileUtils.readFileToString(filePath);

        int start = json.indexOf("[");
        int end = json.lastIndexOf("]");
        if (start == -1 || end == -1) return;

        String block = json.substring(start + 1, end);
        String[] entries = block.split("\\},\\{");

        Random random = new Random();

        for (String entry : entries) {
            entry = entry.replace("{", "").replace("}", "");
            String[] fields = entry.split(",");

            String name = "", birthDate = "", nationality = "", basePosition = "", photo = "";
            int number = 0;

            for (String field : fields) {
                String[] pair = field.split(":", 2);
                if (pair.length < 2) continue;

                String k = pair[0].replace("\"", "").trim();
                String v = pair[1].replace("\"", "").trim();

                switch (k) {
                    case "name": name = v; break;
                    case "birthDate": birthDate = v; break;
                    case "nationality": nationality = v; break;
                    case "basePosition": basePosition = v; break;
                    case "photo": photo = v; break;
                    case "number": number = Integer.parseInt(v); break;
                }
            }

            int shooting = 40 + random.nextInt(61);
            int passing = 40 + random.nextInt(61);
            int stamina = 40 + random.nextInt(61);
            int speed = 40 + random.nextInt(61);
            float height = 1.70f + random.nextFloat() * 0.20f;
            float weight = 65f + random.nextFloat() * 20f;

            Player player = new Player(
                name,
                LocalDate.parse(birthDate),
                nationality,
                photo,
                number,
                shooting,
                passing,
                stamina,
                speed,
                height,
                weight,
                new PlayerPosition(basePosition),
                PreferredFoot.Right
            );

            club.addPlayer(player);
        }
    }
}
