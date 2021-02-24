package pl.xpawelek.database.interfaces;

public interface UserInterFace {

    // TODO: 23.02.2021

    //klasa ta zostanie zaimplementowana do naszego obiektu np: User

    void setUpdate(boolean update); // ta opcja pomoze nam do aktualizacja naszych danych do bazy danych
    boolean hasUpdate(); // sprawdzamy czy update do bazy danych zostal wykonany lub bedzie dopiero wykonany jesli wartosc zwroci "true" -oznacza to ze dopiero bedzie wprowadzony jesli "false" -oznacza ze zostala wykonany lub juz byl
    String update(); // dzieki tej opcji wprowadzimy nasze dane do bazy danych
}
