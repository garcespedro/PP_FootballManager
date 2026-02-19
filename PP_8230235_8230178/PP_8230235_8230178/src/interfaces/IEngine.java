/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/*
* Nome: <João Fontes e Lima>
* Número: <8230178>
* Turma: <LEIT3>
*
* Nome: <Luis Pedro Ribeiro Gracês>
* Número: <8230235>
* Turma: <LEIT2>
*/
/**
 * Interface que define as operações principais para o motor do jogo de futebol,
 * incluindo listagem de clubes, exibição de jogadores, calendário, classificação,
 * definição de formação e avanço das rodadas.
 */
public interface IEngine {

    /**
     * Lista todos os clubes disponíveis no campeonato.
     */
    void listClubs();

    /**
     * Exibe os jogadores do clube especificado pelo índice.
     *
     * @param index o índice do clube na lista de clubes
     */
    void showClubPlayers(int index);

    /**
     * Exibe o calendário de jogos do campeonato.
     */
    void showCalendar();

    /**
     * Exibe a tabela de classificação atual do campeonato.
     */
    void showStandings();

    /**
     * Define a formação tática do time com base na opção escolhida.
     *
     * @param option a opção de formação selecionada
     */
    void setFormationByChoice(int option);

    /**
     * Avança para a próxima rodada do campeonato e executa os jogos correspondentes.
     */
    void playNextRound();
}
