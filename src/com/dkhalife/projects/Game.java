package com.dkhalife.projects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

public class Game extends BasicGame {
	private int playerX = 320;
	private int playerY = 120;
	private Animation player;
	private Polygon playerPoly;
	private BlockMap map;

	public Game() {
		super("Tiled Map Game");
	}

	public void init(GameContainer gc) throws SlickException {
		gc.setVSync(true);
		SpriteSheet sheet = new SpriteSheet("res/player.png", 33, 32);
		map = new BlockMap("res/level1.tmx");
		player = new Animation();
		player.setAutoUpdate(true);

		for (int frame = 0; frame < 3; ++frame) {
			player.addFrame(sheet.getSprite(frame, 0), 150);
		}

		playerPoly = new Polygon(new float[] { playerX, playerY, playerX + 32,
				playerY, playerX + 32, playerY + 32, playerX, playerY + 32 });
	}

	public void update(GameContainer gc, int deltaT) {
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_LEFT)) {
			playerPoly.setX(--playerX);
			if (entityCollidesWith()) {
				playerPoly.setX(++playerX);
			}
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {
			playerPoly.setX(++playerX);
			if (entityCollidesWith()) {
				playerPoly.setX(-playerX);
			}
		}

		if (input.isKeyDown(Input.KEY_UP)) {
			playerPoly.setY(--playerY);
			if (entityCollidesWith()) {
				playerPoly.setY(++playerY);
			}
		}

		if (input.isKeyDown(Input.KEY_DOWN)) {
			playerPoly.setY(++playerY);
			if (entityCollidesWith()) {
				playerPoly.setY(--playerY);
			}
		}
	}

	boolean entityCollidesWith() {
		for (int i = 0; i < BlockMap.entities.size(); ++i) {
			Block entity = (Block) BlockMap.entities.get(i);

			if (playerPoly.intersects(entity.poly)) {
				return true;
			}
		}

		return false;
	}

	public void render(GameContainer gc, Graphics g) {
		BlockMap.tmap.render(0, 0);
		g.drawAnimation(player, playerX, playerY);
		g.draw(playerPoly);
	}

	public static void main(String args[]) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game(), 640, 480, false);

		// Limit the update calls and fps so that CPU wont explode
		app.setMinimumLogicUpdateInterval(25);
		app.setTargetFrameRate(30);
		app.start();
	}
}
