package estrutura;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Classe responsável pela lógica do sistema
// Aqui ficam as operações de adicionar, editar, remover e listar reservas
public class SistemaReservas {

    // Lista interna que armazena todas as reservas
    private final ArrayList<Reserva> reservas;

    // Construtor
    public SistemaReservas() {
        reservas = new ArrayList<>();
    }

    // Adiciona uma nova reserva caso não exista conflito
    public boolean adicionarReserva(Reserva nova) {
        if (nova == null) {
            return false;
        }

        if (possuiConflito(nova, -1)) {
            return false;
        }

        reservas.add(nova);
        return true;
    }

    // Edita uma reserva existente
    // O índice identifica qual reserva está sendo alterada
    public boolean editarReserva(int indice, Reserva atualizada) {
        if (indice < 0 || indice >= reservas.size() || atualizada == null) {
            return false;
        }

        // Na edição, ignoramos a própria reserva ao verificar conflito
        if (possuiConflito(atualizada, indice)) {
            return false;
        }

        reservas.set(indice, atualizada);
        return true;
    }

    // Remove uma reserva pelo índice
    public boolean removerReserva(int indice) {
        if (indice < 0 || indice >= reservas.size()) {
            return false;
        }

        reservas.remove(indice);
        return true;
    }

    // Retorna uma lista apenas para leitura
    public List<Reserva> getReservas() {
        return Collections.unmodifiableList(reservas);
    }

    // Método auxiliar que verifica se uma reserva entra em conflito com outra
    // O parâmetro indiceIgnorado é usado na edição
    private boolean possuiConflito(Reserva nova, int indiceIgnorado) {
        for (int i = 0; i < reservas.size(); i++) {
            if (i == indiceIgnorado) {
                continue;
            }

            if (reservas.get(i).conflita(nova)) {
                return true;
            }
        }
        return false;
    }
}