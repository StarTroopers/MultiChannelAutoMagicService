package com.fanniemae.starapp.providers.externals.trello.models;

public class Data {
	Board BoardObject;
	Card CardObject;
	private boolean voted;

	// Getter Methods

	public Board getBoard() {
		return BoardObject;
	}

	public Card getCard() {
		return CardObject;
	}

	public boolean getVoted() {
		return voted;
	}

	// Setter Methods

	public void setBoard(Board boardObject) {
		this.BoardObject = boardObject;
	}

	public void setCard(Card cardObject) {
		this.CardObject = cardObject;
	}

	public void setVoted(boolean voted) {
		this.voted = voted;
	}

	@Override
	public String toString() {
		return "Data [BoardObject=" + BoardObject.toString() + ", CardObject=" + CardObject.toString() + ", voted=" + voted + "]";
	}
}
