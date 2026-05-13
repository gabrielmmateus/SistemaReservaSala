# Sistema de Reserva de Salas

Projeto desenvolvido em **Java** com interface gráfica utilizando **JFrame/Swing**, com o objetivo de gerenciar reservas de salas de forma simples, organizada e funcional.

## Para executar

1. Importar o projeto no Eclipse.
2. Adicionar o arquivo jcalendar-1.4.jar ao Build Path.
3. Executar a classe Main.java.

## Objetivo

O sistema tem como objetivo facilitar o controle de reservas de salas, evitando conflitos de horários e permitindo que o usuário cadastre, visualize, edite e exclua reservas de maneira prática.

## Funcionalidades

- Cadastro de reservas
- Listagem de reservas em tabela
- Edição de reservas cadastradas
- Exclusão de reservas
- Seleção de sala por lista
- Seleção de data por calendário
- Validação de campos obrigatórios
- Validação de horário
- Verificação de conflitos de reserva

## Regras de Negócio

- Todos os campos devem ser preenchidos.
- O horário inicial deve ser menor que o horário final.
- As reservas devem ocorrer entre 08h e 22h.
- A duração da reserva deve ser entre 1 e 4 horas.
- Não é permitido reservar a mesma sala no mesmo horário.
- O mesmo usuário não pode reservar duas salas no mesmo horário.
- A data da reserva não pode ser anterior à data atual.

## Tecnologias Utilizadas

- Java
- Swing / JFrame
- JTable
- JComboBox
- JDateChooser
- Biblioteca JCalendar

## Estrutura do Projeto

```text
SistemaReservaSala/
├── src/
│   └── estrutura/
│       ├── Main.java
│       ├── Reserva.java
│       ├── SistemaReservas.java
│       └── TelaPrincipal.java
└── libs/
    └── jcalendar-1.4.jar
