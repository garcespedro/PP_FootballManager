/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menus;

/**
 *
 * @author joaof
 */

import java.util.Scanner;

public class GameMenu {

    private Scanner scanner;
    private Engine engine;

    public GameMenu(Engine engine) {
        this.engine = engine;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        int option;
        do {
            showMainMenu();
            option = readInt();

            switch (option) {
                case 1 : showTeamsMenu();
                case 2 : engine.showCalendar();
                case 3 : engine.showStandings();
                case 4 : chooseFormation();
                case 5 : engine.playNextRound();
                case 6 : System.out.println("A sair...");
                default : System.out.println("Opção inválida.");
            }

        } while (option != 6);
    }

    private void showMainMenu() {
        System.out.println("\n=== Football Manager 2026 ===");
        System.out.println("1. Visualizar Equipas");
        System.out.println("2. Ver Calendário");
        System.out.println("3. Ver Classificação");
        System.out.println("4. Preparar Jornada (Escolher Formação)");
        System.out.println("5. Jogar Próxima Jornada");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void showTeamsMenu() {
        engine.listClubs();
        System.out.print("Ver jogadores de qual equipa (número)? ");
        int index = readInt();
        engine.showClubPlayers(index);
    }

    private void chooseFormation() {
        System.out.println("Escolha a formação:");
        System.out.println("1. 4-3-3");
        System.out.println("2. 4-4-2");
        System.out.println("3. 3-5-2");
        System.out.print("Opção: ");
        int choice = readInt();
        engine.setFormationByChoice(choice);
    }

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