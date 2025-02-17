package game.level.entity;

import game.level.Animation;
import game.level.LevelMap;
import game.res.Preferences;
import game.res.ResourceManager;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class EntityPlayer extends EntityMob {

	protected boolean key_left;
	protected boolean key_right;
	protected boolean key_jump;

	public int health;

	protected Animation animationRun;
	protected Animation animationJump;

	public EntityPlayer(double x, double y, double width, double height) {
		super(x, y, width, height, null);
		health = 1;
		loadResources();
	}

	public EntityPlayer(double x, double y) {
		this(x, y, 14d, 31d);
	}

	public void loadResources() {
		animationRun = new Animation(14, 31);
		animationJump = new Animation(14, 31);
		animationRun.load("/model/player/Run-Animation.png", 2, 150);
		animationJump.load("/model/player/Jump-Animation.png", 2, 150);
		image = ResourceManager.getImage("/model/player/Player-Model.png");
	}

	public void update(LevelMap map, double timeFactor) {
		move(map);
		colision(map, timeFactor);
	}

	public void move(LevelMap map) {
		xMovement = 0;
		if (key_left) {
			xMovement = -2;
		}
		if (key_right) {
			xMovement = 2;
		}
		if (key_jump & onGround) {
			yMovement = -2.75;
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == Preferences.getKeyBinding("key_left") || e.getKeyCode() == Preferences.getKeyBinding("key_left2")) {
			key_left = true;
		} else if (e.getKeyCode() == Preferences.getKeyBinding("key_right") || e.getKeyCode() == Preferences.getKeyBinding("key_right2")) {
			key_right = true;
		} else if (e.getKeyCode() == Preferences.getKeyBinding("key_jump") || e.getKeyCode() == Preferences.getKeyBinding("key_jump2")
				|| e.getKeyCode() == Preferences.getKeyBinding("key_jump3")) {
			key_jump = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == Preferences.getKeyBinding("key_left") || e.getKeyCode() == Preferences.getKeyBinding("key_left2")) {
			key_left = false;
		} else if (e.getKeyCode() == Preferences.getKeyBinding("key_right") || e.getKeyCode() == Preferences.getKeyBinding("key_right2")) {
			key_right = false;
		} else if (e.getKeyCode() == Preferences.getKeyBinding("key_jump") || e.getKeyCode() == Preferences.getKeyBinding("key_jump2")
				|| e.getKeyCode() == Preferences.getKeyBinding("key_jump3")) {
			key_jump = false;
		}
	}

	public void resetKeys() {
		key_left = false;
		key_right = false;
		key_jump = false;
	}

	protected BufferedImage getImage(double timeFactor) {
		if (!onGround) {
			return animationJump.getImage(timeFactor);
		} else if (key_left || key_right) {
			return animationRun.getImage(timeFactor);
		}
		return image;
	}

	public EntityPlayerRecord createRecord(EntityPlayerRecord rec) {
		rec.key_left = key_left;
		rec.key_right = key_right;
		rec.key_jump = key_jump;
		return rec;
	}

	public int getHealth() {
		return health;
	}

	public void damage(int amount, LevelMap map) {
		health -= amount;
		if (health <= 0) {
			map.getStageLevel().lose();
		}
	}

}
