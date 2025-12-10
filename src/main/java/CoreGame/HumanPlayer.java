package CoreGame;

import Interfaces.IDivision;
import Interfaces.IMap;
import Structures.Exceptions.ElementNotFoundException;
import Structures.Interfaces.UnorderedListADT;

public class HumanPlayer extends Player{

    public HumanPlayer(String name, IDivision initial) {
        super(name, initial);
    }

    @Override
    public void move(IMap maze) {
        IDivision currentPos = getDivision();
        System.out.println(currentPos.getName());
        try{
            UnorderedListADT<IDivision> adjacentPositions = maze.getAdjacentVertex(currentPos);
        } catch (ElementNotFoundException e) {
            System.out.println("Erro: A divisão do jogador não existe no mapa.");
        }

        System.out.println("Está na Divisão XPTO" );






    }
}
