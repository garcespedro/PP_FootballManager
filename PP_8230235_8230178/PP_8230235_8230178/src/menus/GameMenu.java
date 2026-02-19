/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menus;

/*
* Nome: <João Fontes e Lima>
* Número: <8230178>
* Turma: <LEIT3>
*
* Nome: <Luis Pedro Ribeiro Gracês>
* Número: <8230235>
* Turma: <LEIT2>
*/

import java.util.Scanner;

/**
 * Classe responsável pelo menu principal do jogo Football Manager.
 * Controla a interação com o utilizador, exibindo opções e
 * delegando operações ao motor do jogo (Engine).
 */
public class GameMenu {

    /**
     * Scanner para leitura de entradas do utilizador via consola.
     */
    private Scanner scanner;

    /**
     * Motor do jogo que contém a lógica para manipulação da liga,
     * temporadas, equipas, jogos e estatísticas.
     */
    private Engine engine;

    /**
     * Construtor que inicializa o menu com uma instância do motor do jogo.
     * 
     * @param engine Instância do motor do jogo para executar comandos.
     */
    public GameMenu(Engine engine) {
        this.engine = engine;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Executa o ciclo principal do menu, mostrando opções e
     * processando as escolhas do utilizador até sair.
     */
    public void run() {
        int option;
        do {
            showMainMenu();
            option = readInt();

            switch (option) {
                case 1:
                    showTeamsMenu();
                    break;
                case 2:
                    engine.showCalendar();
                    break;
                case 3:
                    engine.showStandings();
                    break;
                case 4:
                    chooseFormation();
                    break;
                case 5:
                    engine.playNextRound();
                    break;
                case 6:
                    showStatsMenu();
                    break;
                case 7:
                    System.out.println("A sair...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (option != 7);
    }

    /**
     * Exibe o menu principal com as opções disponíveis.
     */
    private void showMainMenu() {
        System.out.println("\n=== Football Manager 2026 ===");
        System.out.println("1. Visualizar Equipas");
        System.out.println("2. Ver Calendário");
        System.out.println("3. Ver Classificação");
        System.out.println("4. Preparar Jornada (Escolher Formação)");
        System.out.println("5. Jogar Próxima Jornada");
        System.out.println("6. Estatísticas e Relatórios");
        System.out.println("7. Sair");
        System.out.print("Escolha uma opção: ");
    }

    /**
     * Exibe o menu para selecionar uma equipa e visualizar os seus jogadores.
     */
    private void showTeamsMenu() {
        engine.listClubs();
        System.out.print("Ver jogadores de qual equipa (número)? ");
        int index = readInt();
        engine.showClubPlayers(index);
    }

    /**
     * Menu para o utilizador escolher uma formação tática para a equipa.
     */
    private void chooseFormation() {
        System.out.println("Escolha a formação:");
        System.out.println("1. 4-3-3");
        System.out.println("2. 4-4-2");
        System.out.println("3. 3-5-2");
        System.out.print("Opção: ");
        int choice = readInt();
        engine.setFormationByChoice(choice);
    }

    /**
     * Exibe o submenu de estatísticas e relatórios, permitindo ao utilizador
     * escolher entre diferentes tipos de dados e relatórios.
     */
    private void showStatsMenu() {
        System.out.println("\n=== ESTATÍSTICAS E RELATÓRIOS ===");
        System.out.println("1. Estatísticas Individuais de Jogadores (golos, assistências)");
        System.out.println("2. Gráficos de posse de bola/remates por jogo");
        System.out.println("3. Histórico de confrontos entre equipas");
        System.out.println("4. Voltar");
        System.out.print("Escolha uma opção: ");
        int choice = readInt();
        switch (choice) {
            case 1:
                engine.showPlayerStats();
                break;
            case 2:
                engine.showMatchStats();
                break;
            case 3:
                engine.showHeadToHead();
                break;
            case 4:
                break; // Volta ao menu principal
            default:
                System.out.println("Opção inválida.");
        }
    }

    /**
     * Lê um inteiro válido da entrada do utilizador, repetindo até que um número
     * seja inserido corretamente.
     * 
     * @return o número inteiro inserido pelo utilizador
     */
    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Valor inválido. Introduza um número: ");
            scanner.next();
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // Limpar buffer
        return num;
    }
}
