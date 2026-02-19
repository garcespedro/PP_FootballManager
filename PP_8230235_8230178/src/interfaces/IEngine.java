/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 *
 * @author joaof
 */
public interface IEngine {
    void listClubs();
    void showClubPlayers(int index);
    void showCalendar();
    void showStandings();
    void setFormationByChoice(int option);
    void playNextRound();
}
