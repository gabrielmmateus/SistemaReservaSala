package estrutura;

// Classe que representa uma reserva
public class Reserva {

    // Atributos da reserva
    private String sala;
    private String data;
    private int inicio;
    private int fim;
    private String usuario;

    // Construtor da classe
    public Reserva(String sala, String data, int inicio, int fim, String usuario) {
        this.sala = sala;
        this.data = data;
        this.inicio = inicio;
        this.fim = fim;
        this.usuario = usuario.trim();
    }

    // Getters para acesso aos dados da reserva
    public String getSala() {
        return sala;
    }

    public String getData() {
        return data;
    }

    public int getInicio() {
        return inicio;
    }

    public int getFim() {
        return fim;
    }

    public String getUsuario() {
        return usuario;
    }

    // Verifica se existe conflito entre duas reservas
    // Regras:
    // 1) Não pode haver duas reservas na mesma sala no mesmo horário
    // 2) O mesmo usuário não pode reservar duas salas ao mesmo tempo
    public boolean conflita(Reserva outra) {

        // Se a data for diferente, não há conflito
        if (!this.data.equals(outra.data)) {
            return false;
        }

        // Verifica se os horários se sobrepõem
        boolean horarioConflita = !(this.fim <= outra.inicio || this.inicio >= outra.fim);

        // Mesmo espaço físico no mesmo horário
        boolean mesmaSala = this.sala.equalsIgnoreCase(outra.sala);

        // Mesmo usuário em dois lugares ao mesmo tempo
        boolean mesmoUsuario = this.usuario.equalsIgnoreCase(outra.usuario);

        return horarioConflita && (mesmaSala || mesmoUsuario);
    }

    // Retorna os dados já no formato ideal para a tabela
    public Object[] toTableRow() {
        return new Object[] {
            sala,
            data,
            inicio,
            fim,
            usuario
        };
    }

    // Representação textual da reserva
    @Override
    public String toString() {
        return sala + " | " + data + " | " + inicio + "-" + fim + " | " + usuario;
    }
}