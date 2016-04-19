
package com.rmsi.mast.report.util;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageUtils {

	public static BufferedImage getResizedImage(BufferedImage sourceImage,
			int newWidth, int newHeight) {

		// return Scalr.resize(src, newWidth, newHeight);
		Image thumbnail = sourceImage.getScaledInstance(newWidth, -1,
				Image.SCALE_SMOOTH);
		BufferedImage bufferedThumbnail = new BufferedImage(
				thumbnail.getWidth(null), thumbnail.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
		return bufferedThumbnail;

	}
}
