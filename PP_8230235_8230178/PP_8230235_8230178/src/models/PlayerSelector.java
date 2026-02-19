
package models;

/*
* Nome: <João Fontes e Lima>
* Número: <8230178>
* Turma: <LEIT3>
*
* Nome: <Luis Pedro Ribeiro Gracês>
* Número: <8230235>
* Turma: <LEIT2>
*/
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IClub;
import com.ppstudios.footballmanager.api.contracts.team.IPlayerSelector;

/**
 * Implementação de {@link IPlayerSelector} responsável por selecionar um jogador de um clube
 * com base em uma posição específica.
 */
public class PlayerSelector implements IPlayerSelector {

    /**
     * Seleciona um jogador de um clube que joga na posição especificada.
     *
     * @param club     Clube do qual o jogador deve ser selecionado.
     * @param position Posição desejada do jogador.
     * @return Um jogador que ocupa a posição especificada.
     * @throws IllegalArgumentException se o clube ou a posição forem nulos.
     * @throws IllegalStateException    se o clube não tiver jogadores ou nenhum jogador com a posição especificada.
     */
    @Override
    public IPlayer selectPlayer(IClub club, IPlayerPosition position) {
        if (club == null || position == null) {
            throw new IllegalArgumentException("Clube e posição não podem ser nulos.");
        }

        IPlayer[] players = club.getPlayers();

        if (players == null || players.length == 0) {
            throw new IllegalStateException("The team is empty.");
        }

        for (int i = 0; i < players.length; i++) {
            IPlayer player = players[i];

            if (player != null && player.getPosition() != null) {
                String pos1 = player.getPosition().getDescription();
                String pos2 = position.getDescription();

                if (pos1 != null && pos1.equalsIgnoreCase(pos2)) {
                    return player;
                }
            }
        }

        throw new IllegalStateException("Não existe o jogador de acordo com a posição indicada : " + position.getDescription());
    }
}
