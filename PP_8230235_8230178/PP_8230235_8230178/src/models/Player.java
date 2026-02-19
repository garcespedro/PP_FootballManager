
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
import com.ppstudios.footballmanager.api.contracts.player.PreferredFoot;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Representa um jogador de futebol com informações pessoais, habilidades,
 * características físicas e preferências de jogo.
 */
public class Player implements IPlayer {

    /**
     * Nome do jogador.
     */
    private String name;

    /**
     * Data de nascimento do jogador.
     */
    private LocalDate birthDate;

    /**
     * Nacionalidade do jogador.
     */
    private String nationality;

    /**
     * Caminho ou URL da foto do jogador.
     */
    private String photo;

    /**
     * Número da camisa do jogador.
     */
    private int number;

    /**
     * Habilidade de finalização do jogador (0–100).
     */
    private int shooting;

    /**
     * Habilidade de passe do jogador (0–100).
     */
    private int passing;

    /**
     * Nível de resistência do jogador (0–100).
     */
    private int stamina;

    /**
     * Nível de velocidade do jogador (0–100).
     */
    private int speed;

    /**
     * Altura do jogador em metros.
     */
    private float height;

    /**
     * Peso do jogador em quilogramas.
     */
    private float weight;

    /**
     * Posição em campo do jogador.
     */
    private IPlayerPosition position;

    /**
     * Pé preferido do jogador.
     */
    private PreferredFoot preferredFoot;

    /**
     * Construtor que inicializa todos os atributos do jogador.
     *
     * @param name           Nome do jogador.
     * @param birthDate      Data de nascimento.
     * @param nationality    Nacionalidade.
     * @param photo          Caminho ou URL da foto.
     * @param number         Número da camisa.
     * @param shooting       Nível de finalização (0–100).
     * @param passing        Nível de passe (0–100).
     * @param stamina        Nível de resistência (0–100).
     * @param speed          Nível de velocidade (0–100).
     * @param height         Altura em metros.
     * @param weight         Peso em quilogramas.
     * @param position       Posição do jogador.
     * @param preferredFoot  Pé preferido (esquerdo ou direito).
     */
    public Player(String name, LocalDate birthDate, String nationality, String photo,
                  int number, int shooting, int passing, int stamina, int speed,
                  float height, float weight, IPlayerPosition position, PreferredFoot preferredFoot) {
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.photo = photo;
        this.number = number;
        this.shooting = shooting;
        this.passing = passing;
        this.stamina = stamina;
        this.speed = speed;
        this.height = height;
        this.weight = weight;
        setPosition(position);
        this.preferredFoot = preferredFoot;
    }

    /**
     * @return Nome do jogador.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return Data de nascimento do jogador.
     */
    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * @return Idade aproximada do jogador em anos.
     */
    @Override
    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    /**
     * @return Nacionalidade do jogador.
     */
    @Override
    public String getNationality() {
        return nationality;
    }

    /**
     * @return Caminho ou URL da foto do jogador.
     */
    @Override
    public String getPhoto() {
        return photo;
    }

    /**
     * @return Número da camisa do jogador.
     */
    @Override
    public int getNumber() {
        return number;
    }

    /**
     * @return Nível de finalização do jogador.
     */
    @Override
    public int getShooting() {
        return shooting;
    }

    /**
     * @return Nível de passe do jogador.
     */
    @Override
    public int getPassing() {
        return passing;
    }

    /**
     * @return Nível de resistência do jogador.
     */
    @Override
    public int getStamina() {
        return stamina;
    }

    /**
     * @return Nível de velocidade do jogador.
     */
    @Override
    public int getSpeed() {
        return speed;
    }

    /**
     * @return Altura do jogador em metros.
     */
    @Override
    public float getHeight() {
        return height;
    }

    /**
     * @return Peso do jogador em quilogramas.
     */
    @Override
    public float getWeight() {
        return weight;
    }

    /**
     * @return Posição em campo do jogador.
     */
    @Override
    public IPlayerPosition getPosition() {
        return position;
    }

    /**
     * @return Pé preferido do jogador (esquerdo ou direito).
     */
    @Override
    public PreferredFoot getPreferredFoot() {
        return preferredFoot;
    }

    /**
     * Define a posição em campo do jogador.
     *
     * @param position Posição a ser atribuída.
     * @throws IllegalArgumentException se a posição for nula.
     */
    @Override
    public void setPosition(IPlayerPosition position) {
        if (position == null) {
            throw new IllegalArgumentException("A posição não pode ser nula");
        }
        this.position = position;
    }

    /**
     * Exporta os dados básicos do jogador em formato JSON.
     *
     * @throws IOException caso ocorra erro na exportação.
     */
    @Override
    public void exportToJson() throws IOException {
        System.out.println("{ \"name\": \"" + name + "\", \"birthDate\": \"" + birthDate + "\" }");
    }
}
