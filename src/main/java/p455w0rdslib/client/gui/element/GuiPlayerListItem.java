package p455w0rdslib.client.gui.element;

import net.minecraft.item.ItemStack;
import p455w0rdslib.util.GuiUtils;
import p455w0rdslib.util.PlayerUtils;
import p455w0rdslib.util.RenderUtils;

/**
 * @author p455w0rd
 *
 */
public class GuiPlayerListItem extends GuiListItem {

	String playerName = "";

	public GuiPlayerListItem(String diaplayText, String playerName) {
		super(diaplayText, 12);
		this.playerName = playerName;
	}

	@Override
	public void draw(int x, int y, int backColor, int textColor) {
		GuiUtils.drawGradientRect((getParent() != null ? getParent().getGui() : RenderUtils.mc().currentScreen), x + 2, y - 3, x + (getParent() == null ? getWidth() : getParent().getWidth()), y + getHeight(), backColor, backColor);
		GuiUtils.drawItem(getIcon(), x + 2, y - 3);
		RenderUtils.getFontRenderer().drawStringWithShadow(getDisplayText(), x + 18, y + 1, textColor);
	}

	public ItemStack getIcon() {
		return PlayerUtils.getPlayerSkull(getPlayerName());
	}

	public String getPlayerName() {
		return playerName == "" ? "Notch" : playerName;
	}

}
