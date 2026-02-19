/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;
import java.io.IOException;

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
 * Implementação da interface ILeague que representa uma liga desportiva,
 * com um nome e um conjunto limitado de temporadas (seasons).
 */
public class League implements ILeague {

    private String name;
    private ISeason[] seasons;
    private int seasonCount; 
    
    /**
     * Cria uma nova liga com um nome e capacidade máxima de temporadas.
     * 
     * @param name Nome da liga (não pode ser nulo ou vazio)
     * @param maxSeasons Capacidade máxima de temporadas que a liga suporta
     * @throws IllegalArgumentException se o nome for inválido
     */
    public League(String name, int maxSeasons) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("League name cannot be null or empty");
        }
        this.name = name;
        this.seasons = new ISeason[maxSeasons];
        this.seasonCount = 0;
    }

    /** {@inheritDoc} */
    @Override
    public String getName() {
        return name;
    }

    /** 
     * Retorna uma cópia do array com as temporadas atuais da liga.
     * 
     * @return Array contendo as temporadas já criadas
     */
    @Override
    public ISeason[] getSeasons() {
        ISeason[] result = new ISeason[seasonCount];
        System.arraycopy(seasons, 0, result, 0, seasonCount);
        return result;
    }

    /**
     * Adiciona uma nova temporada à liga, caso ainda não exista uma temporada com
     * o mesmo ano e a capacidade da liga não esteja esgotada.
     * 
     * @param season Temporada a adicionar (não pode ser nula)
     * @return true se a temporada foi adicionada, false se já existe uma temporada com o mesmo ano
     * @throws IllegalArgumentException se a temporada for nula
     * @throws IllegalStateException se a liga já estiver cheia
     */
    @Override
    public boolean createSeason(ISeason season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null");
        }
        int seasonYear = season.getYear();
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == seasonYear) {
                return false; // Temporada já existe
            }
        }
        if (seasonCount < seasons.length) {
            seasons[seasonCount++] = season;
            return true;
        } else {
            throw new IllegalStateException("League is full, cannot add more seasons");
        }
    }

    /**
     * Remove uma temporada da liga pelo ano.
     * 
     * @param year Ano da temporada a remover
     * @return A temporada removida
     * @throws IllegalArgumentException se a temporada não for encontrada
     */
    @Override
    public ISeason removeSeason(int year) {
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == year) {
                ISeason removedSeason = seasons[i];
                // Move as temporadas seguintes uma posição para trás
                for (int j = i; j < seasonCount - 1; j++) {
                    seasons[j] = seasons[j + 1];
                }
                seasons[--seasonCount] = null;
                return removedSeason;
            }
        }
        throw new IllegalArgumentException("Season not found for the year: " + year);
    }

    /**
     * Obtém uma temporada da liga pelo ano.
     * 
     * @param year Ano da temporada desejada
     * @return A temporada correspondente ao ano
     * @throws IllegalArgumentException se a temporada não for encontrada
     */
    @Override
    public ISeason getSeason(int year) {
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == year) {
                return seasons[i];
            }
        }
        throw new IllegalArgumentException("Season not found for the year: " + year);
    }

    /**
     * Exporta os dados da liga em formato JSON para a saída padrão.
     * 
     * @throws IOException se ocorrer erro na exportação
     */
    @Override
    public void exportToJson() throws IOException {
        System.out.println("{");
        System.out.println("  \"name\": \"" + name + "\",");
        System.out.println("  \"seasons\": [");
        for (int i = 0; i < seasonCount; i++) {
            seasons[i].exportToJson();
            if (i < seasonCount - 1) {
                System.out.println(",");
            }
        }
        System.out.println("  ]");
        System.out.println("}");
    }
}