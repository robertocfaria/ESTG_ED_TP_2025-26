package Interfaces;

public interface ILeverDivision extends IDivision, AnswerCheckable {
    void setMiniumNumberOfLevers(int quantity);

    void applyChallenge();
}