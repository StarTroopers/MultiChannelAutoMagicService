package com.fanniemae.starapp.providers.externals.trello.models;

public class Data {
    Board BoardObject;
    Card CardObject;
    private boolean voted;
    private String text;
    private List list;
    private List listAfter;
    private List listBefore;


    // Getter Methods

    public Board getBoard() {
        return BoardObject;
    }

    public void setBoard(Board boardObject) {
        this.BoardObject = boardObject;
    }

    public Card getCard() {
        return CardObject;
    }

    public void setCard(Card cardObject) {
        this.CardObject = cardObject;
    }

    public String getText() { return text; }

    public List getList() { return list; }
    // Setter Methods

    public void setList(List list) { this.list = list; }

    public List getListAfter() { return listAfter; }

    public void setListAfter(List listAfter) { this.listAfter = listAfter; }

    public List getListBefore() {
        return listBefore;
    }

    public void setListBefore(List listBefore) {
        this.listBefore = listBefore;
    }

    public boolean getVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public void setText(String text) { this.text = text; }

}
