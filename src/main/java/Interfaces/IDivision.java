package Interfaces;

public interface IDivision {

    /** Retorna a nova divisao a se deslocar, apos deve-se obter o corredor associado as divisoes que contem o evento
     * Caso a nova divisao seja nul significa que o jogador falhou o desafio, logo ele perde o turno
     * A partir do @param maze ele obtem as salas adjecentes, mostrandos as opções ao jogador
     */
    IDivision getNewChosenDivision(IMap maze);
}
