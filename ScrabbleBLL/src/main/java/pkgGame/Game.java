package pkgGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import eNum.eMoveResult;
import eNumExceptions.eDrawExceptionType;
import eNumExceptions.eGameExceptionType;
import pkgExceptions.DrawException;
import pkgExceptions.GameException;
import pkgExceptions.MoveException;
import pkgUtil.CircularLinkedList;

public class Game {

	private UUID GameID;
	private Board GameBoard;
	private CircularLinkedList<Player> cllPlayers; // = new CircularLinkedList<Player>();
	private HashMap<UUID, ArrayList<Letter>> playerTileRack = new HashMap<UUID, ArrayList<Letter>>();

	public Game() {
		this.GameID = UUID.randomUUID();
		this.GameBoard = new Board();
		cllPlayers = new CircularLinkedList<Player>();
	}

	/**
	 * StartGame - Start the game. If there are no players in cllPlayers, throw
	 * exception Make sure to draw seven tiles and add them to the player's
	 * TileRack.
	 * 
	 * @author BRG
	 * @version Lab #8
	 * @since Lab #8
	 * @throws DrawException
	 * @throws GameException
	 */

	public void StartGame() throws DrawException, GameException {
		// TODO: Complete this method

		if (cllPlayers.isEmpty()) {
			throw new GameException(this, eGameExceptionType.NoPlayers);
		}
		for (Player player : cllPlayers) {
			playerTileRack.put(player.getPlayerID(), GameBoard.drawLetter(7));
		}


	}

	public Player MakeMove(Move m) throws MoveException {
		// Can the player move? Are they the current player?
		if (m.getPlayer().equals(cllPlayers.getCurrent())) {
			this.GameBoard.MakeMove(m);
		} else {
			throw new MoveException(m, cllPlayers.getCurrent(), m.getPlayer(), eMoveResult.NotCurrentPlayer);
		}

		for (Space s : m.getTiles()) {
			removePlayerTile(cllPlayers.getCurrent(), s.getLetter());
		}

		// Draw tiles for the player, add them to their tile rack
		for (int iDraw = getPlayersTiles(cllPlayers.getCurrent()).size(); iDraw < 7; iDraw++) {
			try {
				addPlayerTile(cllPlayers.getCurrent(), this.GameBoard.drawLetter());
			} catch (DrawException e) {
				if (e.getDrawExceptionType() == eDrawExceptionType.TileBagEmpty) {
					// Nothing to do, no more tiles to draw
				}
			}
		}

		// Advance the player
		return cllPlayers.getNext();
	}

	/**
	 * getPlayersTiles - Return the current Player's tiles.
	 * 
	 * @author BRG
	 * @version Lab #8
	 * @since Lab #8
	 * @param p
	 */

	private ArrayList<Letter> getPlayersTiles(Player p) {
		return playerTileRack.get(p.getPlayerID());

	}

	/**
	 * removePlayerTile - Remove a tile from the player's TileRack
	 * 
	 * @author BRG
	 * @version Lab #8
	 * @since Lab #8
	 * @param p
	 * @param l
	 */

	private void removePlayerTile(Player p, Letter l) {
		getPlayersTiles(p).remove(l);
	}

	/**
	 * addPlayerTile - Add a tile to a player's TileRack
	 * 
	 * @author BRG
	 * @version Lab #8
	 * @since Lab #8
	 * @param p
	 * @param l
	 */
	private void addPlayerTile(Player p, Letter l) {
		getPlayersTiles(p).add(l);

	}

	public UUID getGameID() {
		return GameID;
	}

	public Board getGameBoard() {
		return GameBoard;
	}
  void AddPlayer(Player p) {
		cllPlayers.addNode(p);
	}

	public Player getCurrentPlayer() {
		return cllPlayers.getCurrent();
	}

	public void advancePlayer() {
		cllPlayers.getNext();
	}

	public void RemovePlayer(Player p) {
		cllPlayers.delete(p);
	}

	public int GetGamePlayerCount() {
		return cllPlayers.size();
	}
}
