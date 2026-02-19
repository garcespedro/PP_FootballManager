/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package simulator;

/*
* Nome: <João Fontes e Lima>
* Número: <8230178>
* Turma: <LEIT3>
*
* Nome: <Luis Pedro Ribeiro Gracês>
* Número: <8230235>
* Turma: <LEIT2>
*/
import menus.Engine;
import menus.GameMenu;
import models.Club;
import models.League;
import models.Season;
import utils.ClubImporter;
import utils.SquadImporter;

/**
 * Classe principal responsável por iniciar o simulador da liga.
 * <p>
 * O método main realiza a importação dos clubes e jogadores a partir de ficheiros JSON,
 * cria a liga e a época, gera o calendário de jogos, configura o simulador de partidas
 * e inicia o menu de interação com o utilizador.
 * </p>
 */
public class Main {

    /**
     * Ponto de entrada da aplicação.
     * <p>
     * Executa as seguintes operações:
     * <ul>
     *   <li>Importa os clubes de um ficheiro JSON.</li>
     *   <li>Para cada clube, importa o plantel de jogadores a partir de ficheiros JSON.</li>
     *   <li>Cria uma liga e uma época com os clubes importados.</li>
     *   <li>Gera o calendário de jogos da época.</li>
     *   <li>Define o simulador de partidas a ser utilizado.</li>
     *   <li>Inicializa o motor do jogo e o menu para interação com o utilizador.</li>
     * </ul>
     * Caso ocorra algum erro durante a inicialização, este é capturado e mostrado no console.
     *
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        try {
            String clubesPath = "C:\\Users\\garce\\OneDrive\\Documentos\\LEI\\1º ano\\2º semestre\\PP\\PP_8230235_8230178\\PP_8230235_8230178\\JSONfiles\\files\\clubs.json";
            String plantelBasePath = "C:\\Users\\garce\\OneDrive\\Documentos\\LEI\\1º ano\\2º semestre\\PP\\PP_8230235_8230178\\PP_8230235_8230178\\JSONfiles\\files\\players\\";
            
            Club[] clubes = ClubImporter.importClubs(clubesPath);
            for (int i = 0; i < clubes.length; i++) {
                Club club = clubes[i];
                String squadFilePath = plantelBasePath + club.getCode() + ".json";
                SquadImporter.importSquad(club, squadFilePath, club.getCode());
            }
            
            League liga = new League("Liga Portugal", 1);
            Season season = new Season(2026, "Época 2026", clubes.length, 38);
            
            for (int i = 0; i < clubes.length; i++) {
                season.addClub(clubes[i]);
            }
            
            season.generateSchedule();
            season.setMatchSimulator(new simulator.matchSimulatorStrategy());
            liga.createSeason(season);
            
            Engine engine = new Engine(liga);
            GameMenu menu = new GameMenu(engine);
            menu.run();
            
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o simulador: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
