package graphic;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import core.entities.Archer;
import core.entities.BasicArrow;
import core.entities.Champion;
import core.entities.Piker;
import core.entities.Soldier;

public class TextureProvider {
	
	private static final int TEXTURE_TIME_SWITCH = 200;
	private BufferedImage walkTexture1;
	private BufferedImage walkTexture2;
	private static Map<Class<?>, TextureProvider> textureMap;
	
	public TextureProvider() {}
	
	private TextureProvider(String pathWalkTexture1, String pathWalkTexture2) {
		//walk1
		try {
			walkTexture1 = ImageIO.read(new File(getClass().getResource(pathWalkTexture1).toURI()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		//walk2
		try {
			walkTexture2 = ImageIO.read(new File(getClass().getResource(pathWalkTexture2).toURI()));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	static {
		textureMap = new HashMap<>();
		TextureProvider archer = new TextureProvider("/resources/textures/archer1.png", "/resources/textures/archer1.png");
		TextureProvider champion = new TextureProvider("/resources/textures/champion1.png", "/resources/textures/champion1.png");
		//le texture di piker sono nominate sbagliate!!!! PIKER --> PICKER
		TextureProvider piker = new TextureProvider("/resources/textures/picker1.png", "/resources/textures/picker1.png");
		TextureProvider soldier = new TextureProvider("/resources/textures/soldier1.png", "/resources/textures/soldier1.png");
		TextureProvider arrow = new TextureProvider("/resources/textures/arrow.png", "/resources/textures/arrow.png");
		
		textureMap.put(Archer.class, archer);
		textureMap.put(Champion.class, champion);
		textureMap.put(Piker.class, piker);
		textureMap.put(Soldier.class, soldier);
		textureMap.put(BasicArrow.class, arrow);
	}
	
	public BufferedImage getWalkTexture(long timeOffset) {
		long textureToReturn = timeOffset % TEXTURE_TIME_SWITCH;
		if(textureToReturn < 1) {
			return walkTexture1;
		}else{
			return walkTexture2;
		}
	}
	
	public BufferedImage flipTextureHorizzontaly(BufferedImage pImage) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-pImage.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(pImage, null);
	}
	
	public BufferedImage flipTextureVertically(BufferedImage pImage) {
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
		tx.translate(0, -pImage.getHeight(null));
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(pImage, null);
	}
	
	public BufferedImage getByClass(Class<?> EntityType, long timeOffset) {
		return textureMap.get(EntityType).getWalkTexture(timeOffset);
	}
}
