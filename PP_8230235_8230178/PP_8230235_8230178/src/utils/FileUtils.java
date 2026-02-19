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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utilitário para operações relacionadas com ficheiros.
 */
public class FileUtils {

    /**
     * Lê o conteúdo completo de um ficheiro de texto e devolve-o como uma única String.
     * Todas as linhas são concatenadas removendo espaços em branco no início e fim de cada linha.
     * 
     * @param path Caminho do ficheiro a ser lido.
     * @return Conteúdo do ficheiro como uma String.
     * @throws IOException Caso ocorra algum problema ao ler o ficheiro.
     */
    public static String readFileToString(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line.trim());
        }

        reader.close();
        return sb.toString();
    }
}
