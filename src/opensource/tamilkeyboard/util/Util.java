package opensource.tamilkeyboard.util;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

	public Point getBottomCenterLocation(GraphicsConfiguration graphicsConfiguration, int width2, int height2){
		//size of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//height of the task bar
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(graphicsConfiguration);

		int height=screenSize.height -scnMax.bottom -height2;
		int width=(screenSize.width-width2)/2;
		//available size of the screen 

		Point p=new Point(width,height);
		return p;
	}
	static boolean isMark(char ch)
	{
		int type = Character.getType(ch);
		return type == Character.NON_SPACING_MARK ||
				type == Character.ENCLOSING_MARK ||
				type == Character.COMBINING_SPACING_MARK;
	}
	static int getLength(String text){

		List<String> characters=new ArrayList<String>();
		Pattern pat = Pattern.compile("\u0B95\u0BCD\u0BB7\\p{M}?|\\p{L}\\p{M}?");
		Matcher matcher = pat.matcher(text);
		while (matcher.find()) {
			characters.add(matcher.group());            
		}
		return characters.size();

	}

}
