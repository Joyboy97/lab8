package pkgGame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pkgExceptions.DrawException;
import pkgExceptions.GameException;
import pkgExceptions.MoveException;
import pkgHelper.BoardUtil;
import pkgHelper.GameUtil;
import pkgHelper.Util;

public class GameLab8Test {

	
	@Test
	public void TestRemoveTile()
	{
		Util.PrintStart(new Throwable().getStackTrace()[0].getMethodName());
		Board b = new Board();
		ArrayList<Letter> letters = new ArrayList<Letter>();
		ArrayList<Letter> tileBag = null;
		letters.add(new Letter('A'));
		letters.add(new Letter('B'));
		
		tileBag = BoardUtil.getTileBag(b);
		assertEquals(92,BoardUtil.getTileBag(b).size());
		b = BoardUtil.RemoveTileFromBag(b, letters);
		assertEquals(90,BoardUtil.getTileBag(b).size());
		Util.PrintEnd(new Throwable().getStackTrace()[0].getMethodName());
	}
 	
	@Test
	public void GameTest1() {
		Util.PrintStart(new Throwable().getStackTrace()[0].getMethodName());
		Game g = new Game();
		Player p1 = new Player("Bert");
		Player p2 = new Player("Joe");
		g.AddPlayer(p1);
		g.AddPlayer(p2);
		
		Move m1 = new Move(g.getGameBoard(), p1);
		m1.AddTile(new Space(new Letter('T'),7,7));
		m1.AddTile(new Space(new Letter('E'),7,8));
		m1.AddTile(new Space(new Letter('S'),7,9));
		m1.AddTile(new Space(new Letter('T'),7,10));		
		//g.getGameBoard().MakeMove(m1);
		
		Assertions.assertThrows(MoveException.class, () -> {
			g.MakeMove(m1);
		});
		
 

		Move m2 = new Move(g.getGameBoard(), p2);
		m1.AddTile(new Space(new Letter('E'),8,7));
		m1.AddTile(new Space(new Letter('N'),9,7));
		m1.AddTile(new Space(new Letter('T'),10,7));		
		g.getGameBoard().MakeMove(m2);

		Util.PrintEnd(new Throwable().getStackTrace()[0].getMethodName());		
	}
	
	
	@Test
	public void GameTestThrowExeption1() {
		Util.PrintStart(new Throwable().getStackTrace()[0].getMethodName());
		Game g = new Game();
		Player p1 = new Player("Bert");
		Player p2 = new Player("Joe");
		g.AddPlayer(p1);
		g.AddPlayer(p2);
		try {
			g.StartGame();
		} catch (DrawException | GameException e1) {
			fail("Exception thrown");
			e1.printStackTrace();
		}
		
		Move m1 = new Move(g.getGameBoard(), p1);
		m1.AddTile(new Space(new Letter('T'),7,7));
		m1.AddTile(new Space(new Letter('E'),7,8));
		m1.AddTile(new Space(new Letter('S'),7,9));
		m1.AddTile(new Space(new Letter('T'),7,10));		


		Move m2 = new Move(g.getGameBoard(), p2);
		m2.AddTile(new Space(new Letter('E'),8,7));
		m2.AddTile(new Space(new Letter('N'),9,7));
		m2.AddTile(new Space(new Letter('T'),10,7));		

		
		try {
			g.MakeMove(m2);
		} catch (MoveException e) {
			fail("MoveExceptionThrown");
			e.printStackTrace();
		}

		Util.PrintEnd(new Throwable().getStackTrace()[0].getMethodName());		
	}	

	@Test
	public void GameTestPlayerTileBag()
	{
		Util.PrintStart(new Throwable().getStackTrace()[0].getMethodName());
		Game g = new Game();
		Player p1 = new Player("Jim");
		Player p2 = new Player("Jill");
		g.AddPlayer(p1);
		g.AddPlayer(p2);
		
		assertEquals(2,g.GetGamePlayerCount());
		
		try {
			g.StartGame();
		} catch (DrawException | GameException e) {
			fail("Threw exception: " + e);
		}
		
		assertEquals(p2,g.getCurrentPlayer());
		g.advancePlayer();
		assertEquals(p1,g.getCurrentPlayer());
		
		
		ArrayList<Letter> p1Letters = GameUtil.getPlayersTiles(g, p1);
		
		for (Letter l: p1Letters)
		{
			System.out.println(l.getChLetter());
		}
		
		assertEquals(7,p1Letters.size());

		Util.PrintEnd(new Throwable().getStackTrace()[0].getMethodName());	
		
	}
	
}
